package negyesjatek.gui;

import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author IVBTAAI
 */
public class NewGameButton extends JButton { //új játék indításának gombja

    private int n;                          //milyen méretű játékot szeretne a felhasználó indítani

    public NewGameButton(int n, ActionListener actl) {   //beállítja az n értékét és hozzárendel
        //a gombhoz egy ActuionListenert
        super();
        addActionListener(actl);
        this.n = n;
        setText("Új " + n + "x" + n + "-es játék");
    }

    public int getN() {      //visszaadja az n értékét
        return n;
    }
}
