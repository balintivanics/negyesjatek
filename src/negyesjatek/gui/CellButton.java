package negyesjatek.gui;

import javax.swing.JButton;
import negyesjatek.Coord;

/**
 *
 * @author IVBTAAI
 */
public class CellButton extends JButton {    //a játékmezők grafikus megvalósítása

    private Coord coord;                //a gomb helyzete a buttons mátrixban

    public CellButton(Coord coord) {     //konstuktor ami beállítja a gomb koordinátáit
        super();
        this.coord = coord;
    }

    public Coord getCoord() {            //visszaadja a gomb koordinátáit
        return coord;
    }
}
