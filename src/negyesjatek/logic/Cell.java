package negyesjatek.logic;

import java.io.Serializable;

/**
 *
 * @author IVBTAAI
 */
public class Cell implements Serializable {  //a játék egy négyzeténe logikája

    private int value;  //a játkmező értéke [0;4]-beli egész
    private int color;  //jelöli, hogy a mezőt ki állította 4-re
    //0, ha senki, 1 ha az első játékos (0-ás indexű) és 
    //2, ha a második játékos (2-es indexű)

    public Cell() {  //konstuktor a mező kezdeti állapotának beállítására
        value = 0;    //0-ról indulnak a mezők értékei a játék szabályai alapján
        color = 0;    //0, mert még senki nem állította 4-re
    }

    public Cell(Cell cell) { //copy konstuktor jellegű konstuktor, mentett állapot visszatöltéséhez
        this.value = cell.value;
        this.color = cell.color;
    }

    protected void cellPlus() {  //megnőveli 1-el a játékmező értékét
        value++;
    }

    protected void setColor(int color) { //beállítja a színt
        this.color = color;
    }

    protected void setValue(int value) { //beállítja az értéket
        this.value = value;               //TODO: használva van?
    }

    public int getValue() {  //visszaadja a mező értékét
        return value;
    }

    public int getColor() {  //visszaadja a mező színét
        return color;
    }
}
