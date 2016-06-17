package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by minit on 25-04-2016.
 */
public class BasicFish {

    public static boolean xWing(Tile[][] cPuzzle){
        boolean result = false;

        // running through all digits
        for (int digit = 1 ; digit <= cPuzzle.length ; digit++){
            // does the row/column have locked pair for specific digit.
            boolean[] lockedPairRows = new boolean[cPuzzle.length];
            boolean[] lockedPairColumn = new boolean[cPuzzle.length];

            // finds locked pair
            for(int i = 0 ; i <cPuzzle.length ; i++){
                int rows = 0;
                int columns = 0;

                for(int j = 0 ; j <cPuzzle.length; j++){
                   if(cPuzzle[i][j].getCandidates().contains(digit)){
                       columns++;
                   }
                   if(cPuzzle[j][i].getCandidates().contains(digit)){
                       rows++;
                   }

                    if(j == cPuzzle.length-1){
                        if(columns == 2){
                            lockedPairColumn[i] = true;
                        }
                        if(rows == 2){
                            lockedPairRows[i] = true;
                        }
                    }

                }
            }

            // Takes locked pair and see if it is a part of x-wing
            for(int i = 0 ; i < cPuzzle.length ; i++){
                // The lists
                List<Tile> ColumnTiles = new ArrayList<>();
                List<Tile> RowTiles = new ArrayList<>();


                // Columns
                for(int y = 0; y <cPuzzle.length && lockedPairColumn[i] ; y++ ){
                    if(cPuzzle[i][y].getCandidates().contains(digit)){
                        ColumnTiles.add(cPuzzle[i][y]);
                    }
                }

                // Find another locked pair and compare their perpendicular cordinates to determine x-wing
                if(lockedPairColumn[i]){
                    int y1 = ColumnTiles.get(0).getY();

                    for(int x = 0 ; x < cPuzzle.length ; x++){
                        // Does the first 2 tiles of the two locked pairs have same y-cordinate?
                        if(cPuzzle[x][y1] != cPuzzle[i][y1] && cPuzzle[x][y1].getCandidates().contains(digit) && lockedPairColumn[x]){

                            for(int y2 = 0; y2 < cPuzzle.length ;y2++){
                                // Does the two other tiles of the two locked pairs have same y-cordinates?
                                if(cPuzzle[x][y2] != cPuzzle[x][y1] && cPuzzle[x][y2].getCandidates().contains(digit)
                                        && ColumnTiles.get(1).getY() == y2){
                                    // X - wing found!
                                    ColumnTiles.add(cPuzzle[x][y1]);
                                    ColumnTiles.add(cPuzzle[x][y2]);

                                    if(removeXWing(cPuzzle, ColumnTiles, digit, House.COLUMN )){
                                        result = true;

                                    }


                                }

                            }

                        }

                    }

                }

                //Row
                for(int x = 0; x <cPuzzle.length && lockedPairRows[i] ; x++ ){
                    if(cPuzzle[x][i].getCandidates().contains(digit)){
                        RowTiles.add(cPuzzle[x][i]);
                    }
                }

                // Find another locked pair and compare their perpendicular cordinates to determine x-wing
                if(lockedPairRows[i]){
                    int x1 = RowTiles.get(0).getX();

                    for(int y = 0 ; y < cPuzzle.length ; y++){

                        // Does the first two tiles of locked pairs have same x-cordinate?
                        if(cPuzzle[x1][y] != cPuzzle[x1][i] && cPuzzle[x1][y].getCandidates().contains(digit) && lockedPairRows[y]){

                            for(int x2 = 0; x2 < cPuzzle.length ;x2++){

                                // Does the two second tiles of locked pair have same x-cordinate?
                                if(cPuzzle[x2][y] != cPuzzle[x1][y] && cPuzzle[x2][y].getCandidates().contains(digit)
                                        && RowTiles.get(1).getX() == x2){
                                    // X - wing found!
                                    RowTiles.add(cPuzzle[x1][y]);
                                    RowTiles.add(cPuzzle[x2][y]);

                                    if(removeXWing(cPuzzle, RowTiles, digit, House.ROW )) {
                                        result = true;
                                    }

                                }

                            }

                        }

                    }

                }




            }

        }

        return result;
    }
    // Removes digit from candidate lists through the x-wing's cover set.
    private static boolean removeXWing(Tile[][] cPuzzle, List<Tile> tiles, int digit, House h){
        boolean result = false;

        if(h == House.COLUMN){
            int y1 = tiles.get(0).getY();
            int y2 = tiles.get(1).getY();
            for(int x = 0 ; x < cPuzzle.length ; x++){
                if(!tiles.contains(cPuzzle[x][y1])){
                    if(cPuzzle[x][y1].getCandidates().remove(new Integer(digit))){
                        result = true;
                    }
                }

                if(!tiles.contains(cPuzzle[x][y2])){
                    if(cPuzzle[x][y2].getCandidates().remove(new Integer(digit))){
                        result = true;
                    }
                }

            }
        } else if(h == House.ROW){
            int x1 = tiles.get(0).getX();
            int x2 = tiles.get(1).getX();
            for(int y = 0 ; y <cPuzzle.length ; y++){
                if(!tiles.contains(cPuzzle[x1][y])){
                    result = cPuzzle[x1][y].getCandidates().remove(new Integer(digit));

                }

                if(!tiles.contains(cPuzzle[x2][y])){

                    result = cPuzzle[x2][y].getCandidates().remove(new Integer(digit));

                }
            }
        }

        return result;
    }

    public static boolean swordFish(Tile[][] cPuzzle, int size){
        boolean result = false;

        //Running through all digits
        for(int digit = 1 ; digit <= cPuzzle.length; digit++){
            List<Integer> baseRow = new ArrayList<>();
            List<Integer> baseColumn = new ArrayList<>();
            List<List<Integer>> coverRow = new ArrayList<>();
            List<List<Integer>> coverColumn = new ArrayList<>();



            /*
            We know that row/column is a part of a base set, if there is a locked pair or locked triple.
             */

            for(int y = 0 ; y < cPuzzle.length ; y++){
                List<Integer> baseRowCoverColumn = new ArrayList<>();
                List<Integer> baseColumnCoverRow = new ArrayList<>();
                int countRow = 0;
                int countColumn = 0;
                /*
                running through both rows and columns to find locked pairs/triples, and candidates for base and cover sets.
                 */
                for(int x = 0 ; x <cPuzzle.length ; x++){
                    if (cPuzzle[x][y].getCandidates().contains(digit)){
                        countRow++;
                        baseRowCoverColumn.add(x);
                    }
                    if (cPuzzle[y][x].getCandidates().contains(digit)){
                        countColumn++;
                        baseColumnCoverRow.add(x);
                    }

                }
                // Is it a locked pair/triple ?
                if(countRow > 1 && countRow <= size){
                    baseRow.add(y);
                    coverRow.add(baseRowCoverColumn);
                }
                if(countColumn > 1 && countColumn <= size){
                    baseColumn.add(y);
                    coverColumn.add(baseColumnCoverRow);
                }
            }

            //Rows
            /*
            we start by cleaning up in our base and cover set by finding the candidates that does not belong, meaning that
            one of the columns in the base set does not align with the rest of the base set.
             */
            List<Integer> newBaseRow = cleanCandB(baseRow,coverRow);
            for(int i = 0 ; i < baseRow.size() ; i++){
                if( !newBaseRow.contains(baseRow.get(i)) ){
                    coverRow.remove(i);
                }
            }
            baseRow = newBaseRow;

            /*
            Here we check to see if the sizes of the base set and cover set is correct, if it is, we are sure that we've found
            a swordfish.
             */
            Set<Integer> coverSetRows = new HashSet<>();
            for(List l: coverColumn){
                coverSetRows.addAll(l);
            }

            if(baseRow.size() == size && coverSetRows.size() == size){ // found swordfish
                if(removeSwordfish(cPuzzle, baseRow, coverSetRows, digit, House.ROW)){ // try to update the puzzle using the found swordfish.
                    result = true;
                }

            }

            //Columns

            List<Integer> newBaseColumn = cleanCandB(baseColumn,coverColumn);
            for(int i = 0 ; i < baseColumn.size() ; i++){
                if( !newBaseColumn.contains(baseColumn.get(i)) ){
                    coverColumn.remove(i);
                }
            }
            baseColumn = newBaseColumn;
            /*
             The cover sets defines the 'houses' that needs to be changed.
             so they need to be changed to Hashsets
             */
            Set<Integer> coverSetColumn = new HashSet<>();
            for(List l: coverColumn){
                coverSetColumn.addAll(l);
            }

            if(baseColumn.size() == size && coverSetColumn.size() == size){ // found swordfish
                if(removeSwordfish(cPuzzle, baseColumn, coverSetColumn, digit, House.COLUMN)){
                    result = true;
                }

            }


        }

        return result;
    }
    private static List<Integer> cleanCandB(List<Integer> base, List<List<Integer>> cover){
        List<Integer> removeBase = new ArrayList<>();
        List<List<Integer>> removeCover = new ArrayList<>();

        List<List<Integer>> coverCopy = new ArrayList<>();
        coverCopy.addAll(cover);

        /*
        We go through each digit of each list in the cover set.
        For each digit we try to count how often it appears in the cover set over all.
        If its only found one place (where we originally found it), we have to remove
        the cover set, and thereby the base set from the lists.
         */
        for(List l : cover){
            for(int i = 0; i < l.size() ; i++){
                int count = 0;

                for(List l2 : cover){
                    if(l2.contains( l.get(i))){
                        count++;
                    }
                }

                if(count == 1){
                    int index = cover.indexOf(l);
                    removeBase.add( base.get(index) );
                    removeCover.add( l );
                }

            }

        }
        base.removeAll( removeBase );
        cover.removeAll( removeCover );

        // Has the set been updates? If so try to see if there is more to be changed, if not return the result.
        if(coverCopy.equals(cover)){
            return base;
        }else{
            return cleanCandB(base,cover);
        }
    }

    private static boolean removeSwordfish(Tile[][] cPuzzle, List<Integer> baseSet, Set<Integer> coverSet, int digit, House h) {
        boolean result = false;

        for(int i = 0; i < cPuzzle.length ; i++){
            if(coverSet.contains(i)){
                for(int j = 0 ; j <cPuzzle.length ; j++){

                    if(h == House.ROW){
                        if(!baseSet.contains(j) && cPuzzle[i][j].getCandidates().remove(new Integer(digit))){
                            result = true;
                        }
                    }else if(h == House.COLUMN){
                        if (!baseSet.contains(j) && cPuzzle[j][i].getCandidates().remove(new Integer(digit))) {
                            result = true;
                        }
                    }
                }
            }
        }


        return result;
    }



}
