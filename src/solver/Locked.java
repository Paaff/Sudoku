package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by minit on 13-04-2016.
 */
public class Locked {

    public static boolean lockedPointing(Tile[][] cPuzzle, Field[][] cField){
        boolean result  = false;

        // goes through the possible digits
        for(int i = 1 ; i <= cPuzzle.length ;i++){

            // goes through the fields
            for(int j = 0 ; j <cField.length ; j++){
                for(int k = 0; k < cField.length ;k++){
                    List<Tile> tiles = new ArrayList<>();

                    // if the candidates exists in fields' tile, add it to the list.
                    for(Tile t: cField[j][k].getTiles()){
                        if (t.getCandidates().contains(i)){
                            tiles.add(t);
                        }
                    }

                    /*
                     if the list is the correct length
                     Meaning it is longer than 1 and maximum the sidelength of the field.
                      */
                    if(tiles.size() > 1 && tiles.size() <= cField.length){

                        /*
                        recording the x and y axis of the first tile in the list.
                        Taken from the first, since it will be constant if pointing pair exists.
                         */
                        int X,Y;
                        boolean XPair,YPair;
                        X = tiles.get(0).getX();
                        Y = tiles.get(0).getY();
                        XPair = true; YPair = true;

                        /*
                         If constant coordinate was inconsistant, dismiss the list as pointing pair.
                         We know this will find pointing pair, since if none of the coordinates are constant,
                         the tiles will not be alligned and will not be considered a pointing pair.
                          */
                        for(Tile t : tiles){
                            if(t.getX() != X)
                                YPair = false;

                            if(t.getY() != Y)
                                XPair = false;

                        }

                        // If pointing pair was found and change was made, return true.
                        if (XPair && removePointingCandidate(Y,cPuzzle,cField[j][k],i,House.ROW)){

                            result = true;
                        //    System.out.println("Found pointing pair, for X-axis, for " + i + " at field:" + j +"," +k);
                        }

                        if(YPair && removePointingCandidate(X,cPuzzle,cField[j][k],i,House.COLUMN)){
                            result = true;
                          //  System.out.println("Found pointing pair, for Y-axis, for " + i + " at field:" + j +"," +k);
                        }
                    }


                }
            }
        }

        return result;
    }

    public static boolean lockedClaiming(Tile[][] cPuzzle){
        boolean result = false;

        // runs through all possible digits.
        for(int i =1; i <= cPuzzle.length ; i++){

            /*
            Runs through the 'locked' coordinate; meaning, for columns the x-coordinate is locked and
            for the rows, the y-coordinate if locked.
             */
            for(int j = 0; j<cPuzzle.length;j++){
                List<Tile> tilesColumn = new ArrayList<>();
                List<Tile> tilesRow = new ArrayList<>();

                /*
                Here we check for both column and row at the same time.
                If the digit is found as a candidate on the column/row, add it to the correct list.
                 */
                for(int k = 0; k<cPuzzle.length;k++){
                    if(cPuzzle[j][k].getCandidates().contains(i)){
                        tilesColumn.add(cPuzzle[j][k]);
                    }
                    if(cPuzzle[k][j].getCandidates().contains(i)){
                        tilesRow.add(cPuzzle[k][j]);
                    }
                }

                /*
                 If the column list has more than 1 tile, and fewer than the maximum (field's side length),
                 continue and check if they're in the same field.
                  */

                if(tilesColumn.size()>1 && tilesColumn.size()<=Math.sqrt(cPuzzle.length)){
                    result = checkForEquivalentField(tilesColumn,i);
                }

                /*
                 If the row list has more than 1 tile, and fewer than the maximum (field's side length),
                 continue and check if they're in the same field.
                  */
                if(tilesRow.size()>1 && tilesRow.size()<=Math.sqrt(cPuzzle.length)){
                    result = checkForEquivalentField(tilesRow,i);
                }

            }

        }
        return result;
    }

    private static boolean checkForEquivalentField(List<Tile> tileList, int digit){
        boolean result = false;
        Field f = tileList.get(0).getField();

        boolean claim = true;

        for(Tile t: tileList){
            if(t.getField()!=f){
                claim = false;
            }
        }

        // if they were in same field, and something was updated, return true.
        if(claim && removeClaimedCandidates(tileList,f,digit)){

            result = true;
                      /*  int a =(int)(tilesRow.get(0).getX()/Math.sqrt(cPuzzle.length));
                        int b = (int)((int)j/Math.sqrt(cPuzzle.length));
                        System.out.println("Found claiming pair, digit: " + i+ " at field: "+ a+ "," +b );*/
        }

        return result;
    }

    private static boolean removePointingCandidate(int pos, Tile[][] cPuzzle, Field f, int digit, House h){
        boolean result = false;

        if(h == House.ROW) {
            for (int i = 0; i < cPuzzle.length; i++) {
                if (cPuzzle[i][pos].getField() != f && cPuzzle[i][pos].getCandidates().contains(digit)) {
                    cPuzzle[i][pos].getCandidates().remove(new Integer(digit));
                    result = true;
                }
            }
        }else if(h == House.COLUMN){
            for(int i = 0; i<cPuzzle.length;i++){
                if(cPuzzle[pos][i].getField()!=f && cPuzzle[pos][i].getCandidates().contains(digit)){
                    cPuzzle[pos][i].getCandidates().remove(new Integer(digit));
                    result = true;
                }
            }
        }
        return result;
    }

    //removes candidates from field when dealing with claiming pairs, and acts as change-checker.
    private static boolean removeClaimedCandidates(List<Tile> tiles,Field f, int digit){
        boolean result = false;
        for(Tile t:f.getTiles()){
            if(!tiles.contains(t) && t.getCandidates().contains(digit)){
                t.getCandidates().remove(new Integer(digit));
                result = true;
            }
        }
        return  result;
    }
}
