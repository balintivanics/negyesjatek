package negyesjatek.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.DataFormatException;
import negyesjatek.Coord;

/**
 *
 * @author IVBTAAI
 */
public class Table implements Serializable { //a játéktábla osztálya, ez vezérli a játéklogikát

    private Cell[][] cells; //a mezők kétdimenziós tömbje
    private Player[] players;   //a játékosok listája, a játék szabályai szerint 2 játékos lehet
    private int actualPlayer;   //annak a játékosnak az indexe akinek éppen zajlik a köre
    private int n;  //az nxn-es játéktábla mérete
    private int winner;

    public Table(int n) {    //a játék létrehozása
        players = new Player[2];    //a játékosok létrehozása
        winner = 0;
        players[0] = new Player("player1");
        players[1] = new Player("player2");

        actualPlayer = 0;   //az első játékos (0. indexű) kezd új játék esetén

        this.n = n;   //a grafikus felületen a felhasználó választja ki milyen 
        //méretű táblán szeretne játszani

        cells = new Cell[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                cells[i][j] = new Cell(); //létrehozza az üres játékmezőket

            }
        }


    }

    public int getN() {
        return n;
    }

    public Cell getCell(Coord coord) {   //visszaadja a kapott koordinátán lévő játékmezőt
        System.out.println("length: " + cells.length);
        return cells[coord.getX()][coord.getY()];
    }

    public void plus(Coord coord) {   //megpróbálja növelni a kapott koordinátán lévő mező értékét

        int x = coord.getX();
        int y = coord.getY();
        if (x >= 0 && x < n && y >= 0 && y < n) {   //ellenőrzi, hogy a kapott koordináták a tömb egy elemére mutatnak-e
            //nem Exception, mert viszonylag gyakori eset

            if ((cells[x][y].getValue()) < 4) { //ha a játékmező értékét lehet még tovább növelni
                cells[x][y].cellPlus();     //meghívja a mező cellPlus függvényét, ami megnöveli a mező értékét
                if (cells[x][y].getValue() == 4) {  //ha ezzel a játékmező értéke 4 lett, akkor a játékos pontot szerzett
                    players[actualPlayer].scorePlus();  //megnöveli a játékos pontszámát eggyel
                    cells[x][y].setColor(actualPlayer + 1);   //jelöli, hogy a mezőt melyik játékos állította 4-re
                }
                isWon();    //megnézi, hogy a játék végetért-e

            }

        }
    }

    public Player nextPlayer() { //beállítja és visszaadja a következő játékost
        if (actualPlayer == 0) {
            actualPlayer = 1;
        } else {
            actualPlayer = 0;
        }
        return players[actualPlayer];
    }

    public int getWinner() {
        return winner;
    }

    protected boolean isWon() {  //leellenőrzi, hogy a játéknak vége van-e

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (cells[i][j].getValue() != 4) {  //ha van olyan cella, aminek még nem 4 az 
                    //értéke, akkor a játéknak nincs vége
                    return false;
                }
            }

        }        //ha a ciklusok lefutottak anélkül, hogy találtak
        //volna olyat, ami nem 4 akkor vége a játéknak



        if (players[0].getScore() > players[1].getScore()) {   //megnézi, hogy melyik játékos nyert...
            winner = 1;
        } else {
            winner = 2;
        }

        return true;

    }

    public int getActualPlayer() {   //visszaadja annak a játékosnak az indexét, aki következik
        //a kattintással
        return actualPlayer;
    }

    public Player getPlayer(int i) { //visszaadja az i-edik indexű játékost
        return players[i];
    }

    public void saveState(String path) throws FileNotFoundException, IOException {
        //a path elérési úttasl rendlekező fájlba menti a játék állását

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(this);      //a Table objektumok kiírja a fájlba, szerializálva
        oos.close();                //lezárja a fájlt

    }

    public void loadState(String path) throws FileNotFoundException, IOException, ClassNotFoundException {

        //betölti a játék állását a path elérési úttal rendelkező, Table objektumot tartalmazó fájblól

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        Table table = (Table) ois.readObject();
        //if(table.cells.length==n){
        this.actualPlayer = table.actualPlayer;   //az aktuális Table objektumot a fájlból kiolvasottra alakítja

        n = table.cells.length;
        System.out.println("n: " + n);
        cells = new Cell[n][n];
        players = new Player[2];
        players[0] = new Player(table.players[0]);
        players[1] = new Player(table.players[1]);


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println(table.cells[i][j]);
                cells[i][j] = new Cell(table.cells[i][j]);
                //System.out.println(cells[i][j].getValue());
            }
        }
    }
}
