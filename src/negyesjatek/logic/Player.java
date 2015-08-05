package negyesjatek.logic;

import java.io.Serializable;

/**
 *
 * @author IVBTAAI
 */
public class Player implements Serializable {    //a játékos osztálya
    //a játékszabályok szerint 2 
    //játékos játszik egymás ellen

    private String name;    //a játékos neve
    private int score;      //a játékos pontszáma, ha 4-re növeli egy mező értékét eggyel megnő

    public Player(String name) { //konstruktor, új játékost hoz létre 0 ponttal
        score = 0;
        this.name = name;
    }

    public Player(Player player) {   //copy konstuktor jellegű konstuktor, a mentett állapot beolvasáshoz
        this.name = player.name;
        this.score = player.score;
    }

    public String getName() {    //visszaadja a játékos nevét
        return name;
    }

    protected void scorePlus() { //növeli 1-el a játékos pontszámát
        ++score;
    }

    protected int getScore() {   //visszaadja a játékos pontszámát
        return score;
    }

    @Override
    public String toString() {
        return name;
    }
}
