package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by minit on 04-05-2016.
 */
public class HiddenSubsets {

    public static boolean hiddenPairs(Tile[][] cPuzzle, int size){
        boolean result = false;

        //Rows and columns and Fields
        for(int i = 0 ; i <cPuzzle.length ;i++){
            /*
            intializing 2 sets for each 'house' (ie. rows, columns, fields)
            the 'digitTiles' lists keeps track of which tiles contains a specific digit as candidate,
            while the 'indexList' keeps track of the address of the lists in the digitTiles list, with length shorter than
            'size' (For pairs size = 2, triplets size = 3 ...)
             */
            List<List<Tile>> digitTilesRows = new ArrayList<>();
            List<List<Tile>> digitTilesColumns = new ArrayList<>();

            List<Integer> indexListRows = new ArrayList<>();
            List<Integer> indexListColumns = new ArrayList<>();

            /*
            initialising the lists in the 'digitTiles' lists
             */
            for(int n = 0; n < cPuzzle.length; n++){
                digitTilesColumns.add(new ArrayList<>());
                digitTilesRows.add(new ArrayList<>());
            }

            for( int j = 0 ; j <cPuzzle.length ;j++){


                // Rows // Columns

                /*
                Going through a tile's candidates, if a specific digit occurs, the tile will be added to the corresponding
                address in the list. Lets say the digit is 1 then the address is index 0 (first address)
                 */
                for(int digit = 1 ; digit <= 9 ; digit++){


                    // Colums
                    if(cPuzzle[i][j].getCandidates().contains(digit)){
                        digitTilesColumns.get( digit-1 ).add(cPuzzle[i][j]);
                    }

                    // Rows
                    if(cPuzzle[j][i].getCandidates().contains(digit)){
                        digitTilesRows.get( digit-1 ).add(cPuzzle[j][i]);
                    }
                }

                // Fields
                //We know that if these conditions is met, we are in the top left corner of a field:
                if(i % Math.sqrt(cPuzzle.length) == 0
                        && j % Math.sqrt(cPuzzle.length) == 0){

                    //initializing the digitTiles list.
                    List<List<Tile>> digitTilesFields = new ArrayList<>();
                    for(int digit = 1 ; digit <= 9 ; digit++){
                        digitTilesFields.add(new ArrayList<>()); //initialization

                        for(Tile t: cPuzzle[i][j].getField().getTiles()){
                            if(t.getCandidates().contains(digit)){
                                digitTilesFields.get( digit-1 ).add(t); // adding to list, same as above.
                            }
                        }
                    }

                    List<Integer> indexListFields = new ArrayList<>();

                    /*
                    running through the lists of digitTiles, we will check to see if the size of the list
                    is below the specific size, and above one. if it is we add it to indexList, since it might be a
                    part of a hidden subset.
                     */
                    for(int n = 0 ; n < digitTilesFields.size() ; n++){
                        if (digitTilesFields.get(n).size() <= size && digitTilesFields.get(n).size() > 1){
                            indexListFields.add(n);

                        }
                    }
                    /*
                    if indexList has a size greater than 1, there is a chance that there might be a hidden subset.
                    First we will remove those digits that cannot be a part of the hidden subset.
                    We did this by looking at each potential digit's tiles, if they were the only potential
                    digit that used this tile, it could not be a part of the hidden subset, and we therefore remove it
                    from the list.
                     */
                    if(indexListFields.size() > 1){
                        result = clearHiddenSubset(digitTilesFields,indexListFields,size);

                    }

                }
            }

            // Rows
            // Same explanation as for fields.
            for(int n = 0 ; n < digitTilesRows.size() ; n++) {
                if(digitTilesRows.get(n).size() <= size && digitTilesRows.get(n).size() > 1) {
                    indexListRows.add( n );
                }
            }

            // same explanation as for fields.
            if (indexListRows.size() > 1){
                result = clearHiddenSubset(digitTilesRows,indexListRows,size);
            }

            // Columns
            // same explanation as for fields.
            for(int n = 0 ; n < digitTilesColumns.size() ; n++){

                if(digitTilesColumns.get(n).size() <= size && digitTilesColumns.get(n).size() > 1){

                    indexListColumns.add( n );
                }
            }

            // same explanation as for fields.
            if(indexListColumns.size() > 1){
                result = clearHiddenSubset(digitTilesColumns,indexListColumns,size);
            }
        }
        return result;
    }

    private static boolean clearHiddenSubset(List<List<Tile>> digitTiles, List<Integer> indexList, int size){
        boolean result = false;
        indexList = findHiddenSubsets(digitTiles, indexList);
        Set<Tile> tilesColumns = createTileSet(indexList, digitTiles); // made for easier access to the tiles of subset.

        /*
          If the number of tiles and digits in the subset is the correct size, we have found a hidden subset.
          We then try to remove every digit from these tile's candidates that is not a part of the subset.
        */
        if(indexList.size() == size && tilesColumns.size() == size)
            if (updateHiddenSubset(tilesColumns, indexList)) {
                result = true;
                // System.out.println("Found hidden subset (column), digits= " + indexListColumns + "(+1) At column: " + i);
            }

        return result;
    }

    private static Set createTileSet(List<Integer> indexList, List<List<Tile>> digitTiles){
        Set<Tile> tileSubset = new HashSet<>();
        for(int n = 0 ; n <indexList.size() ; n++){
            tileSubset.addAll(digitTiles.get( indexList.get(n) ));
        }

        return tileSubset;
    }

    private static List<Integer> findHiddenSubsets(List<List<Tile>> digitTiles, List<Integer> indexList){

        List<Integer> indexCopy = new ArrayList<>();
        indexCopy.addAll(indexList);
        List<Integer> removeList = new ArrayList<>();
        // see the explanation for fields.
        for(int i = 0 ; i < indexList.size(); i++){
            for(Tile t: digitTiles.get( indexList.get(i) )){
                int count = 0;
                for(int j = 0; j <indexList.size(); j++){

                    if(digitTiles.get( indexList.get(j) ).contains( t )){
                        count++;
                    }

                }

                if(count == 1){
                    removeList.add( indexList.get(i) );
                }
            }
        }

        indexList.removeAll(removeList);

        if(indexCopy.equals(indexList)){
            return indexList;
        }else{
            return findHiddenSubsets(digitTiles,indexList);
        }

    }

    private static boolean updateHiddenSubset(Set<Tile> subsetTiles, List<Integer> indexList){
        boolean result = false;

        List<Integer> newDigits = new ArrayList<>();
        for(int i = 0; i <indexList.size() ; i++){
            newDigits.add(indexList.get(i)+ 1);
        }

        for(Tile t: subsetTiles){
            if(t.getCandidates().retainAll(newDigits)){
                result = true;
            }
        }
        return result;
    }

}
