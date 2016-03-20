package solver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by minit on 20-03-2016.
 */
public class CandidateFinder {
    /*
        Method that finds candidates for all of the empty cells.
     */
    public static void runFinder(Tile[][] cPuzzle){
        for(int i = 0; i <cPuzzle.length ;i++){
            for(int j = 0 ; j < cPuzzle.length ; j++){
                eliminateCandidates(cPuzzle,i,j);

            }
        }
    }

    public static void eliminateCandidates(Tile[][] cPuzzle, int x, int y){
        /*
            Initials temp. list of all 'non'-candidates for the cells. ie digits that cannot be written in the cell.
         */
        List<Integer> temp = new ArrayList<>();
        // This for loop checks both row and column at the same time.
        for(int i = 0; i < cPuzzle.length ; i++) {

            /*
                Doesnt check it self. Checks of the neighboring cells has a digit, that is in the current cell's candidates.
                If it is, add it to the remove list.
             */
            if (i != x && cPuzzle[x][y].getCandidates().contains(cPuzzle[i][y].getDigit())) {
                temp.add(cPuzzle[i][y].getDigit());
            }
            if(i!= y &&  cPuzzle[x][y].getCandidates().contains(cPuzzle[x][i].getDigit())){
                temp.add(cPuzzle[x][i].getDigit());
            }
        }
        /*
            Checks all neighboring tiles in the same field as our current cell. If their digit is on the cell's candidate list.
            Add it to the remove list.
         */
        for(Tile t : cPuzzle[x][y].getField().getTiles()){
            if(t!=cPuzzle[x][y] && cPuzzle[x][y].getCandidates().contains(t.getDigit())){
                temp.add(t.getDigit());
            }
        }
        /*
            Remove all digits on the remove list from the cell's candidate list.
         */
        cPuzzle[x][y].getCandidates().removeAll(temp);

    }
}
