package negyesjatek.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import negyesjatek.Coord;
import negyesjatek.logic.Table;

/**
 *
 * @author IVBTAAI
 */
public class GameFrame extends JFrame {  //az ablak ami megjeleníti a játékot

    private CellButton buttons[][];     //a kétdimenziós tömb, ami a gombokat tárolja
    private int n;                      //a játéktábla méretei
    private Table table;                //a játéktábla ami a játék logikájáért felelős
    private JLabel whosTurn;            //kiírja, hogy melyik játékos következik
    private JTextField saveTo;          //a mentéssel kapcsolatos input/output helye
    private JTextField loadFrom;        //a betöltéssel kapcsolatos input/output helye
    private JPanel panel;               //a játéktér

    public GameFrame(int n) {            //az ablak konstuktora

        super();                        //meghívja a JFrame konstruktorát
        this.n = n;                       //beállítja a tábla méretét
        buttons = new CellButton[n][n]; //létrehozza a gombokból álló mátrixot

        setLocation(50, 40);            //az balak pozíciója
        setVisible(true);               //legyen látható az ablak
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //az ablak jobbfelső sarkában lévő bezárás
        //gombra kattintva zárja be az alkalmazást
        //setExtendedState(MAXIMIZED_BOTH);
        setSize(800, 600);
        table = new Table(n);           //létrehozza az nxn-es játéktáblát

        panel = new JPanel(new GridLayout(n, n));    //a játéktér, nxn gombból áll
        
        whosTurn = new JLabel(table.getPlayer(table.getActualPlayer()).getName() + " következik");
        //kiírja ki következik

        



        updateButtons();    //létrehozza és felteszi a gombokat a panelre

        JPanel functions = new JPanel(new GridLayout(6, 2)); //a kezelőgomnok panele
        functions.add(new NewGameButton(10, newGameListener));//a 10x10-es új játék gombjának létrehozása
        //és feltevése
        functions.add(new NewGameButton(20, newGameListener));//a 20x20-as új játék gombjának létrehozása
        //és feltevése
        functions.add(new NewGameButton(30, newGameListener));//a 30x30-as új játék gombjának létrehozása
        //és feltevése

        saveTo = new JTextField("fájl");                    //a TextField ami várja, hogy hova mentsen a program
        //hibás input esetén ez jelez vissza
        functions.add(saveTo);

        JButton save = new JButton("mentés");
        save.addActionListener(saveListener);
        functions.add(save);

        loadFrom = new JTextField("fájl");  //a TextField ami várja, hogy hova mentsen a program
        //hibás input esetén ez jelez vissza

        JButton load = new JButton("betöltés");
        load.addActionListener(loadListener);

        functions.add(loadFrom);
        functions.add(load);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(functions, BorderLayout.EAST);  //a kezelőgombok paneljének felrakása
        getContentPane().add(panel, BorderLayout.WEST);      //a panel felrakása
        getContentPane().add(whosTurn, BorderLayout.NORTH);  //a következő játékost kiíró JLabel felrakása

        repaint();      //kirajzolás
        revalidate();
    }
    private final ActionListener loadListener = new ActionListener() {  //a betöltés gombhoz tartozó ActionListener
        @Override
        public void actionPerformed(ActionEvent e) {    //megpróbálja betölteni a fájlt
            try {
                table.loadState(loadFrom.getText());    //megpróbálja meghívni a loadFromban kapott elérési
                n = table.getN();                                        //úttal a tábla betöltési metódusát
            } catch (FileNotFoundException c) {       //a hibakezelés egy hibaüzeneta loadFrom-ra
                loadFrom.setText("Nem található a fájl");
            } catch (IOException d) {
                loadFrom.setText("Olvasási hiba");
            } catch (ClassNotFoundException f) {
                loadFrom.setText("Hibás fájlformátum");
            }


            updateButtons();                       //kirajzolja a gombokat a betöltött tábla alapján

        }
    };
    private final ActionListener saveListener = new ActionListener() {  //megpróbálja menteni fájlba a táblát
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                table.saveState(saveTo.getText());  //a saveTo által kapott eléri útra megpróbál menteni
            } catch (FileNotFoundException a) {       //a hibakezelés egy hibaüzenet a saveTo-ra
                saveTo.setText("Fájlhiba");
            } catch (IOException b) {
                saveTo.setText("Fájlhiba");

            }
        }
    };

    private void checkIfWon() {
        if (table.getWinner() != 0) {
            JOptionPane.showMessageDialog(getContentPane(), "A győztes " + table.getPlayer(table.getWinner() - 1));
        }



    }
    private final ActionListener newGameListener = new ActionListener() {   //az Új játék gomb ActionListenerje
        @Override
        public void actionPerformed(ActionEvent e) {
            NewGameButton ngb = (NewGameButton) e.getSource();     //a megnyomott gomb n adattagja alapján
            //indít egy új nxn-es játékot 
            int k = ngb.getN();
            dispose();
            GameFrame frame = new GameFrame(k);
        }
    };
    private final ActionListener cellButtonActionListener = new ActionListener() {   //a játékmezőre kattintás
        @Override
        public void actionPerformed(ActionEvent e) {
            CellButton cb = (CellButton) e.getSource();
            Coord coord = cb.getCoord();
            table.plus(coord);          //a kattintott gombnak megfelelő Cell értékét megpróbálja növelni
            table.plus(coord.left());   //a kattintott gomb szomszédait is megpróbálja növelni
            table.plus(coord.right());
            table.plus(coord.up());
            table.plus(coord.down());
            updateButtons();            //kirajzolja a frissített gombokat
            whosTurn.setText(table.nextPlayer().getName() + " következik");   //a következő játékos köre

        }
    };

    public final void updateButtons() {    //elkészíti és kirajzolja a gombokat
        //panel = new JPanel(new GridLayout(n,n));
        panel.removeAll();
        panel.setLayout(new GridLayout(n, n));
        buttons = new CellButton[n][n];
        checkIfWon();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                CellButton cb = new CellButton(new Coord(i, j));
                cb.addActionListener(cellButtonActionListener);
                //cb.setPreferredSize(new Dimension(90,50));
                buttons[i][j] = cb;
                panel.add(cb);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int color = table.getCell(new Coord(i, j)).getColor();
                if (color != 0) {
                    buttons[i][j].setEnabled(false);
                    switch (color) {

                        case 1:
                            buttons[i][j].setBackground(Color.BLUE);
                            break;
                        case 2:
                            buttons[i][j].setBackground(Color.RED);
                            break;



                    }
                } else {
                    buttons[i][j].setBackground(null);
                    buttons[i][j].setEnabled(true);
                }
                Integer value = (table.getCell(new Coord(i, j)).getValue());
                buttons[i][j].setText(value.toString());
                buttons[i][j].setName(value.toString());

            }
        }
        panel.repaint();
        panel.revalidate();
        repaint();
        revalidate();
    }
}
