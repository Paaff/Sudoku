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
                        if (XPair && GenericMethods.removeLockedCandidatesXAxis(Y,cPuzzle,cField[j][k],i)){

                            result = true;
                            System.out.println("Found pointing pair, for X-axis, for " + i + " at field:" + j +"," +k);
                        }

                        if(YPair && GenericMethods.removeLockedCandidatesYAxis(X,cPuzzle,cField[j][k],i)){
                            result = true;
                            System.out.println("Found pointing pair, for Y-axis, for " + i + " at field:" + j +"," +k);
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
                List<Tile> tiles1 = new ArrayList<>();
                List<Tile> tiles2 = new ArrayList<>();

                /*
                Here we check for both column and row at the same time.
                If the digit is found as a candidate on the column/row, add it to the correct list.
                 */
                for(int k = 0; k<cPuzzle.length;k++){
                    if(cPuzzle[j][k].getCandidates().contains(i)){
                        tiles1.add(cPuzzle[j][k]);
                    }
                    if(cPuzzle[k][j].getCandidates().contains(i)){
                        tiles2.add(cPuzzle[k][j]);
                    }
                }

                /*
                 If the column list has more than 1 tile, and fewer than the maximum (field's side length),
                 continue and check if they're in the same field.
                  */

                if(tiles1.size()>1 && tiles1.size()<=Math.sqrt(cPuzzle.length)){
                    Field f = tiles1.get(0).getField();
                    boolean claim = true;

                    for(Tile t: tiles1){
                        if(t.getField()!=f){
                            claim = false;
                        }
                    }

                    // if they were in same field, and something was updated, return true.
                    if(claim && GenericMethods.removeClaimedCandidates(tiles1,f,i)){

                        result = true;
                        int a =(int)(j/Math.sqrt(cPuzzle.length));
                        int b = (int) (tiles1.get(0).getY()/ Math.sqrt(cPuzzle.length));
                        System.out.println("Found claiming pair, digit: " + i+ " at field: "+ a + ","+ b);
                    }
                }

                /*
                 If the row list has more than 1 tile, and fewer than the maximum (field's side length),
                 continue and check if they're in the same field.
                  */
                if(tiles2.size()>1 && tiles2.size()<=Math.sqrt(cPuzzle.length)){
                    Field f = tiles2.get(0).getField();

                    boolean claim = true;

                    for(Tile t: tiles2){
                        if(t.getField()!=f){
                            claim = false;
                        }
                    }

                    // if they were in same field, and something was updated, return true.
                    if(claim && GenericMethods.removeClaimedCandidates(tiles2,f,i)){

                        result = true;
                        int a =(int)(tiles2.get(0).getX()/Math.sqrt(cPuzzle.length));
                        int b = (int)((int)j/Math.sqrt(cPuzzle.length));
                        System.out.println("Found claiming pair, digit: " + i+ " at field: "+ a+ "," +b );
                    }
                }

            }

        }


        return result;
    }
}
