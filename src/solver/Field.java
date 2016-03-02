package solver;

/**
 * Created by minit on 02-03-2016.
 */
public class Field {
    Tile[] tiles;
    int index = 0;

    public Field(int max){
        tiles = new Tile[max];
    }

    public void giveTile(Tile t){
        tiles[index] = t;
        t.giveField(this);
        index++;
    }

    public Tile[] getTiles(){
        return tiles;
    }
}
