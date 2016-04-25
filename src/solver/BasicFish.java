package solver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minit on 25-04-2016.
 */
public class BasicFish {

    public static boolean xWing(Tile[][] cPuzzle){
        boolean result = false;

        /*
        The techinques runs through all the possible digits. (candidates)
         */
        for (int digit = 1 ; digit <= cPuzzle.length ; digit++){
            /*
            Two boolean arrays, that quickly tells for the specific digit, if the different rows or columns has locked pairs.
             */
            boolean[] lockedPairRows = new boolean[cPuzzle.length];
            boolean[] lockedPairColumn = new boolean[cPuzzle.length];

            /*
            This for loop runs through each row and column and checks if the specific digit exists only 2 times, if
            they do the row/column's boolean is set true.
             */
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

            /*
            Now that we know which columns/rows has locked pairs, we'll run through these again to add the tiles that
            are a part of the pair to a list.
             */

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

                /*
                If the current column has a locked pair we'll get the y-coordinate (row) of the first tile.
                From this row we'll check to see if there are other tiles with the same digit as candidate.
                If there are, we'll first check if the new tile's column also has a locked pair, in this case
                the new tile must be a part of it.
                 */
                if(lockedPairColumn[i]){
                    int y1 = ColumnTiles.get(0).getY();

                    for(int x = 0 ; x < cPuzzle.length ; x++){

                        if(cPuzzle[x][y1] != cPuzzle[i][y1] && cPuzzle[x][y1].getCandidates().contains(digit) && lockedPairColumn[x]){

                            for(int y2 = 0; y2 < cPuzzle.length ;y2++){
                                /*
                                This if-statement checks to see if the new locked pair is fully aligned with the original one.
                                Meaning that the two second tiles has the same y-coordinate (row).
                                If they do, we have found an X-wing!
                                 */
                                if(cPuzzle[x][y2] != cPuzzle[x][y1] && cPuzzle[x][y2].getCandidates().contains(digit)
                                        && ColumnTiles.get(1).getY() == y2){
                                    // X - wing found!
                                    ColumnTiles.add(cPuzzle[x][y1]);
                                    ColumnTiles.add(cPuzzle[x][y2]);

                                    if(GenericMethods.removeXWingColumn(cPuzzle, ColumnTiles, digit )){
                                        result = true;

                                        System.out.println("Found x-wing! (column) From (" + ColumnTiles.get(0).getX() +  "," + ColumnTiles.get(0).getY()
                                                + ") and (" + ColumnTiles.get(3).getX() + "," + ColumnTiles.get(3).getY() + ")");
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

                /*
                If the current row has a locked pair we'll get the x-coordinate (column) of the first tile.
                From this column we'll check to see if there are other tiles with the same digit as candidate.
                If there are, we'll first check if the new tile's row also has a locked pair, in this case
                the new tile must be a part of it.
                 */
                if(lockedPairRows[i]){
                    int x1 = RowTiles.get(0).getX();

                    for(int y = 0 ; y < cPuzzle.length ; y++){

                        if(cPuzzle[x1][y] != cPuzzle[x1][i] && cPuzzle[x1][y].getCandidates().contains(digit) && lockedPairRows[y]){

                            for(int x2 = 0; x2 < cPuzzle.length ;x2++){

                                /*
                                This if-statement checks to see if the new locked pair is fully aligned with the original one.
                                Meaning that the two second tiles has the same x-coordinate (column).
                                If they do, we have found an X-wing!
                                 */
                                if(cPuzzle[x2][y] != cPuzzle[x1][y] && cPuzzle[x2][y].getCandidates().contains(digit)
                                        && RowTiles.get(1).getX() == x2){
                                    // X - wing found!
                                    RowTiles.add(cPuzzle[x1][y]);
                                    RowTiles.add(cPuzzle[x2][y]);

                                    if(GenericMethods.removeXWingRows(cPuzzle, RowTiles, digit )) {
                                        result = true;

                                        System.out.println("Found x-wing! (row) From (" + RowTiles.get(0).getX() + "," + RowTiles.get(0).getY()
                                                + ") and (" + RowTiles.get(3).getX() + "," + RowTiles.get(3).getY() + ")");
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


}
