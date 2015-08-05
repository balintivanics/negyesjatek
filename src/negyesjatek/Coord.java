package negyesjatek;

/**
 *
 * @author IVBTAAI
 */
public class Coord {    //koordináták osztálya

    private int x;      //x koordináta
    private int y;      //y koordináta

    public Coord(int x, int y) { //beállítja az x-et és az y-t
        this.x = x;
        this.y = y;
    }

    public int getX() {  //visszaadja x-et
        return x;
    }

    public int getY() {  //visszaajda y-t
        return y;
    }

    public Coord left() {    //visszaadja a koordináta példánytól 1-el balra lévő pont koordinátáit 
        return new Coord(x - 1, y);
    }

    public Coord right() {
        return new Coord(x + 1, y);//visszaadja a koordináta példánytól 1-el jobbra lévő pont koordinátáit
    }

    public Coord up() {
        return new Coord(x, y - 1);//visszaadja a koordináta példánytól 1-el feljebb lévő pont koordinátáit
    }

    public Coord down() {
        return new Coord(x, y + 1);//visszaadja a koordináta példánytól 1-el lejjebb lévő pont koordinátáit
    }
}
