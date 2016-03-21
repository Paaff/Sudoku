package solver;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by minit on 20-03-2016.
 */
public class GenericMethods {

    public static List findEmptyCells(Tile[][] cPuzzle){
        /*
        Deque[] coords = new Deque[2];
        coords[0] = new LinkedList<Integer>();
        coords[1] = new LinkedList<Integer>();
*/
        List<List<Integer>> coords = new ArrayList<>();
        coords.add(new ArrayList<>());
        coords.add(new ArrayList<>());

        for(int i =0 ; i < cPuzzle.length ; i++){
            for(int j = 0 ; j <  cPuzzle.length ; j++){
                if(cPuzzle[i][j].getDigit() == 0){
                    coords.get(0).add(i);
                    coords.get(1).add(j);
                }
            }
        }
        System.out.println(coords.get(0));
        System.out.println(coords.get(1));
        return coords;

    }

    public static void updateXAxis(int y, Tile[][] cPuzzle, List candidates, Tile t){
        for(int i = 0; i < cPuzzle.length ; i++){
            if(cPuzzle[i][y] != t)
                cPuzzle[i][y].getCandidates().removeAll(candidates);
        }

    }

    public static void updateYAxis(int x, Tile[][] cPuzzle, List candidates, Tile t){

        for(int i = 0 ; i < cPuzzle.length ; i++){
            if(cPuzzle[x][i] != t)
                cPuzzle[x][i].getCandidates().removeAll(candidates);
        }
    }

    public static void updateField(Field field, List candidates, Tile s){
        for(Tile t: field.getTiles()){
            if(s!=t)
                t.getCandidates().removeAll(candidates);
        }

    }
}
