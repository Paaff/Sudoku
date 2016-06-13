package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by minit on 20-04-2016.
 */
public class NakedSubsets {

    public static boolean nakedPairs(Tile[][]cPuzzle){
        boolean result = false;

        // Runs through all tiles.
        for(int i = 0 ; i <cPuzzle.length ; i++){
            for(int j = 0 ; j <cPuzzle.length ;j++){
                // Check to see if the tile has 2 candidates, since its pairs we are looking for.
                if(cPuzzle[i][j].getCandidates().size() == 2){

                    // Row:
                    for(int k = i ; k < cPuzzle.length ; k++){
                        /*
                        Check to see if we find another empty tile with the exact same candidate pair.
                        If so  remove the candidates from the rest of he row.
                         */
                        if(cPuzzle[i][j].getCandidates().equals(cPuzzle[k][j].getCandidates()) &&
                                cPuzzle[i][j] != cPuzzle[k][j]){
                            Tile[] pair = new Tile[2];
                            pair[0] = cPuzzle[i][j]; // one half of the pair
                            pair[1] = cPuzzle[k][j]; // the other half of the path

                            if(removedNP(cPuzzle,pair, House.ROW)){

                                result = true;
                              //  System.out.println("Found naked pair at row " + cPuzzle[i][j].getY());
                            }
                        }
                    }

                    // Column:
                    for(int k = j ; k <cPuzzle.length ; k++){
                        /*
                        Check to see if we find another empty tile with the exact same candidate pair.
                        If so  remove the candidates from the rest of he column.
                         */
                        if(cPuzzle[i][j].getCandidates().equals(cPuzzle[i][k].getCandidates()) &&
                                cPuzzle[i][j] != cPuzzle[i][k]){
                            Tile[] pair = new Tile[2];
                            pair[0] = cPuzzle[i][j];
                            pair[1] = cPuzzle[i][k];

                            if(removedNP(cPuzzle,pair,House.COLUMN)){
                                result = true;
                             //   System.out.println("Found naked pair at column " + cPuzzle[i][j].getX());
                            }

                        }

                    }

                    // Field:
                    for(Tile t:cPuzzle[i][j].getField().getTiles()){
                        /*
                        Check to see if we find another empty tile with the exact same candidate pair.
                        If so  remove the candidates from the rest of the field.
                         */
                        if(cPuzzle[i][j].getCandidates().equals(t.getCandidates()) &&
                                cPuzzle[i][j] != t){
                            Tile[] pair = new Tile[2];
                            pair[0] = cPuzzle[i][j];
                            pair[1] = t;

                            if(removedNP(cPuzzle,pair,House.FIELD)){
                                result = true;
                             //   System.out.println("Found naked pair in field " + t.getX()/3 + ", " + t.getY()/3);
                            }
                        }

                    }

                }

            }
        }


        return result;
    }

    private static boolean removedNP(Tile[][] cPuzzle, Tile[] pair, House h){
        boolean result = false;

        if(h == House.FIELD) {
            for (Tile t : pair[0].getField().getTiles()) {
                if (pair[0] != t && pair[1] != t && t.getCandidates().removeAll(pair[0].getCandidates())) {
                    result = true;
                }
            }
        } else if( h == House.COLUMN){
            int x = pair[0].getX();

            for(int y = 0; y <cPuzzle.length ; y++){
                if(pair[0] != cPuzzle[x][y] && pair[1] != cPuzzle[x][y]
                        && cPuzzle[x][y].getCandidates().removeAll(pair[0].getCandidates())){
                    result = true;
                }
            }
        } else {
            int y = pair[0].getY();

            for(int x = 0; x < cPuzzle.length ; x++){
                if(pair[0] != cPuzzle[x][y] && pair[1] != cPuzzle[x][y] &&
                        cPuzzle[x][y].getCandidates().removeAll(pair[0].getCandidates())){
                    result = true;
                }
            }
        }

        return result;
    }

    /*
    Subsets, for instance triples or quadruple, are when a set of candidates exists across an equal amount of tiles,
    on the same row, column or field.

    We use the parameter *size* for the size of the subset. 3 for triples and so on.
     */

    public static boolean nakedLargeSubsets(Tile[][] cPuzzle, int size) {
        boolean result = false;
        // running through all tiles
        for(int i = 0 ; i < cPuzzle.length ; i++){
            for(int j = 0 ; j <cPuzzle.length ; j++){

                /*
                Check to see if the empty tile has *size* or less candidates, but not 0.
                 */
                if(cPuzzle[i][j].getCandidates().size() <= size && cPuzzle[i][j].getDigit() == 0){

                    /*
                    Two lists are added. One is a list of all possible subsets candidates.
                    This means that looking at tile with coordinate (i,j), we will make a list of all possible subsets sets


                    The list candidates consists of all possible candidate matches for the tile with coordinate (i,j).
                    The list tiles consists of the different tiles being a part of this subset.
                    Pairing lists has the same indexing in the different lists.
                     */
                    List<List<Integer>> candidatesRow = new ArrayList<>();
                    List<List<Tile>> tilesRow = new ArrayList<>();
                    List<List<Integer>> candidatesColumn = new ArrayList<>();
                    List<List<Tile>> tilesColumn = new ArrayList<>();
                    List<List<Integer>> candidatesField = new ArrayList<>();
                    List<List<Tile>> tilesField = new ArrayList<>();

                    // Row
                    // Checking for subsets on the row where the tile(i,j) is located.
                    for(int x = i ; x < cPuzzle.length ; x++){

                        /*
                         If another tile (x,j) also has *size* or less candidates, we'll check if its part of a possible subset.
                         We do this by checking if the candidates are already in the list above.
                          */
                        if(cPuzzle[x][j].getCandidates().size() <= size && cPuzzle[x][j] != cPuzzle[i][j] && cPuzzle[x][j].getDigit() == 0){
                            boolean newCand = true;

                            // runs through all of the candidate lists, and checks for two different cases:
                            for(List l : candidatesRow){
                                /*
                                 First case is that every candidate of the tile (x,j) is already in a candidate list,
                                 if this is the case it means that the tile (x,j) can be added the tile list,
                                 since it is a possible candidate for the subset.
                                  */
                                if(l.containsAll(cPuzzle[x][j].getCandidates())){
                                    newCand = false;
                                    tilesRow.get(candidatesRow.indexOf(l)).add(cPuzzle[x][j]);

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to 3, if they are it means that we have found a correct subset,
                                     we then try to update the row, by removing the candidates of the subset from the rest of the row.
                                     */
                                    if(listSizeCheck(l,tilesRow.get(candidatesRow.indexOf(l)), size)){
                                        if(removeNakedSubset(cPuzzle,tilesRow.get(candidatesRow.indexOf(l)),l, House.ROW)){

                                            result = true;
                                        /*    List<Integer> temp = new ArrayList<>();
                                            for(Tile t: tilesRow.get(candidatesRow.indexOf(l))){
                                                temp.add(t.getX());
                                            }
                                            System.out.println("Found subset at row " + cPuzzle[i][j].getY() + " "+ l + " with tiles on x-coordinates: " + temp);
                                      */  }
                                    }
                                /*
                                Second case is that some of the candidates of the tile (x,j) is already in a candidate list,
                                if this is the case we'll add all the new candidates to the list. This includes duplicates
                                but these will be removed when we check for the correctness of subsets. We will also add the tile (x,j)
                                to the tiles list.
                                 */
                                }else if(containsSome(l,cPuzzle[x][j].getCandidates())){
                                    newCand = false;
                                    tilesRow.get(candidatesRow.indexOf(l)).add(cPuzzle[x][j]);
                                    l.addAll(cPuzzle[x][j].getCandidates());

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to 3, if they are it means that we have found a correct subset,
                                     we then try to update the row, by removing the candidates of the subset from the rest of the row.
                                     */
                                    if(listSizeCheck(l,tilesRow.get(candidatesRow.indexOf(l)), size)){
                                        if(removeNakedSubset(cPuzzle,tilesRow.get(candidatesRow.indexOf(l)),l, House.ROW)){

                                            result = true;
                                          /*  List<Integer> temp = new ArrayList<>();
                                            for(Tile t: tilesRow.get(candidatesRow.indexOf(l))){
                                                temp.add(t.getX());
                                            }
                                            System.out.println("Found subset at row " + cPuzzle[i][j].getY() + " "+ l + " with tiles on x-coordinates: " + temp);
                                       */ }
                                    }
                                }
                            }

                            /*
                            Last case is that the candidates of tile (x,j) was not found in any of the candidates lists.
                            In this case we will create a new list for this possible subset. We will do this by adding
                            the tile (i,j) and the tile (x,j) to the new list, as well as both of their candidates to
                            the matching list.
                             */
                            if(newCand){
                                List<Integer> newCandList = new ArrayList<>();
                                newCandList.addAll(cPuzzle[x][j].getCandidates());
                                newCandList.addAll(cPuzzle[i][j].getCandidates());

                                List<Tile> newTileList = new ArrayList<>();
                                newTileList.add(cPuzzle[i][j]);
                                newTileList.add(cPuzzle[x][j]);

                                Set<Integer> temp = new HashSet<>();
                                temp.addAll(newCandList);

                                if(temp.size() <= size){
                                    candidatesRow.add(newCandList);
                                    tilesRow.add(newTileList);
                                }

                            }



                        }

                    }

                    // Column
                    for(int y = i ; y < cPuzzle.length ; y++){

                        /*
                         If another tile (x,j) also has *size* or less candidates, we'll check if its part of a possible subset.
                         We do this by checking if the candidates are already in the list above.
                          */
                        if(cPuzzle[i][y].getCandidates().size() <= size && cPuzzle[i][y] != cPuzzle[i][j] && cPuzzle[i][y].getDigit() == 0){
                            boolean newCand = true;

                            // runs through all of the candidate lists, and checks for two different cases:
                            for(List l : candidatesColumn){
                                /*
                                 First case is that every candidate of the tile (x,j) is already in a candidate list,
                                 if this is the case it means that the tile (x,j) can be added the tile list,
                                 since it is a possible candidate for the subset.
                                  */
                                if(l.containsAll(cPuzzle[i][y].getCandidates())){
                                    newCand = false;
                                    tilesColumn.get(candidatesColumn.indexOf(l)).add(cPuzzle[i][y]);

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to *size*, if they are it means that we have found a correct subset,
                                     we then try to update the row, by removing the candidates of the subset from the rest of the row.
                                     */
                                    if(listSizeCheck(l,tilesColumn.get(candidatesColumn.indexOf(l)),size)){
                                        if(removeNakedSubset(cPuzzle,tilesColumn.get(candidatesColumn.indexOf(l)),l, House.COLUMN)){

                                            result = true;
                                        /*    List<Integer> temp = new ArrayList<>();
                                            for(Tile t: tilesColumn.get(candidatesColumn.indexOf(l))){
                                                temp.add(t.getY());
                                            }
                                            System.out.println("Found subset at column " + cPuzzle[i][j].getX() + " "+ l + " with tiles on y-coordinates: " + temp );
                                       */ }
                                    }
                                /*
                                Second case is that some of the candidates of the tile (x,j) is already in a candidate list,
                                if this is the case we'll add all the new candidates to the list. This includes duplicates
                                but these will be removed when we check for the correctness of subsets. We will also add the tile (x,j)
                                to the tiles list.
                                 */
                                }else if(containsSome(l,cPuzzle[i][y].getCandidates())){
                                    newCand = false;
                                    tilesColumn.get(candidatesColumn.indexOf(l)).add(cPuzzle[i][y]);
                                    l.addAll(cPuzzle[i][y].getCandidates());

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to *size*, if they are it means that we have found a correct subset,
                                     we then try to update the row, by removing the candidates of the subset from the rest of the row.
                                     */
                                    if(listSizeCheck(l,tilesColumn.get(candidatesColumn.indexOf(l)), size)){
                                        if(removeNakedSubset(cPuzzle,tilesColumn.get(candidatesColumn.indexOf(l)),l, House.COLUMN)){

                                            result = true;
                                         /*   List<Integer> temp = new ArrayList<>();
                                            for(Tile t: tilesColumn.get(candidatesColumn.indexOf(l))){
                                                temp.add(t.getY());
                                            }
                                            System.out.println("Found subset at column " + cPuzzle[i][j].getX() + " "+ l + " with tiles on y-coordinates: " + temp );
                                          */   }
                                    }
                                }
                            }

                            /*
                            Last case is that the candidates of tile (x,j) was not found in any of the candidates lists.
                            In this case we will create a new list for this possible subset. We will do this by adding
                            the tile (i,j) and the tile (x,j) to the new list, as well as both of their candidates to
                            the matching list.
                             */
                            if(newCand){
                                List<Integer> newCandList = new ArrayList<>();
                                newCandList.addAll(cPuzzle[i][y].getCandidates());
                                newCandList.addAll(cPuzzle[i][j].getCandidates());

                                List<Tile> newTileList = new ArrayList<>();
                                newTileList.add(cPuzzle[i][j]);
                                newTileList.add(cPuzzle[i][y]);

                                Set<Integer> temp = new HashSet<>();
                                temp.addAll(newCandList);

                                if(temp.size() <= 4){
                                    candidatesColumn.add(newCandList);
                                    tilesColumn.add(newTileList);
                                }

                            }



                        }

                    }


                    // Field
                    for(Tile t: cPuzzle[i][j].getField().getTiles()){

                        /*
                         If another tile (x,j) also has *size* or less candidates, we'll check if its part of a possible subset.
                         We do this by checking if the candidates are already in the list above.
                          */
                        if(t.getCandidates().size() <= size && t != cPuzzle[i][j] && t.getDigit() == 0){
                            boolean newCand = true;

                            // runs through all of the candidate lists, and checks for two different cases:
                            for(List l : candidatesField){
                                /*
                                 First case is that every candidate of the tile (x,j) is already in a candidate list,
                                 if this is the case it means that the tile (x,j) can be added the tile list,
                                 since it is a possible candidate for the subset.
                                  */
                                if(l.containsAll(t.getCandidates())){
                                    newCand = false;
                                    tilesField.get(candidatesField.indexOf(l)).add(t);

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to *size*, if they are it means that we have found a correct subset,
                                     we then try to update the row, by removing the candidates of the subset from the rest of the row.
                                     */
                                    if(listSizeCheck(l,tilesField.get(candidatesField.indexOf(l)), size)){
                                        if(removeNakedSubset(cPuzzle,tilesField.get(candidatesField.indexOf(l)),l, House.FIELD)){

                                            result = true;
/*
                                            List<Integer> tempX = new ArrayList<>();
                                            List<Integer> tempY = new ArrayList<>();
                                            for(Tile s:tilesField.get(candidatesField.indexOf(l)) ){
                                                tempX.add(s.getX());
                                                tempY.add(s.getY());
                                            }
                                            System.out.println("Found subset at Field " + cPuzzle[i][j].getX()/3 + "," + cPuzzle[i][j].getY()/3 + " "
                                                    + l + " with tiles on x-coordinates: " + tempX + " and Y: "+ tempY);*/
                                        }
                                    }
                                /*
                                Second case is that some of the candidates of the tile (x,j) is already in a candidate list,
                                if this is the case we'll add all the new candidates to the list. This includes duplicates
                                but these will be removed when we check for the correctness of subsets. We will also add the tile (x,j)
                                to the tiles list.
                                 */
                                }else if(containsSome(l,t.getCandidates())){
                                    newCand = false;
                                    tilesField.get(candidatesField.indexOf(l)).add(t);
                                    l.addAll(t.getCandidates());

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to *size*, if they are it means that we have found a correct subset,
                                     we then try to update the row, by removing the candidates of the subset from the rest of the row.
                                     */
                                    if(listSizeCheck(l,tilesField.get(candidatesField.indexOf(l)), size)){
                                        if(removeNakedSubset(cPuzzle,tilesField.get(candidatesField.indexOf(l)),l, House.FIELD)){

                                            result = true;
/*
                                            List<Integer> tempX = new ArrayList<>();
                                            List<Integer> tempY = new ArrayList<>();
                                            for(Tile s:tilesField.get(candidatesField.indexOf(l)) ){
                                                tempX.add(s.getX());
                                                tempY.add(s.getY());
                                            }
                                            System.out.println("Found subset at Field " + cPuzzle[i][j].getX()/3 + "," + cPuzzle[i][j].getY()/3 + " "
                                                    + l + " with tiles on x-coordinates: " + tempX +" and Y: "+ tempY);*/
                                            }
                                    }
                                }
                            }

                            /*
                            Last case is that the candidates of tile (x,j) was not found in any of the candidates lists.
                            In this case we will create a new list for this possible subset. We will do this by adding
                            the tile (i,j) and the tile (x,j) to the new list, as well as both of their candidates to
                            the matching list.
                             */
                            if(newCand){
                                List<Integer> newCandList = new ArrayList<>();
                                newCandList.addAll(t.getCandidates());
                                newCandList.addAll(cPuzzle[i][j].getCandidates());

                                List<Tile> newTileList = new ArrayList<>();
                                newTileList.add(cPuzzle[i][j]);
                                newTileList.add(t);

                                Set<Integer> temp = new HashSet<>();
                                temp.addAll(newCandList);

                                if(temp.size() <= 4){
                                    candidatesField.add(newCandList);
                                    tilesField.add(newTileList);
                                }
                            }



                        }

                    }


                }

            }
        }

        return result;

    }

    private static boolean containsSome(List<Integer> list1, List<Integer> list2){
        for(Integer i : list2){
            if(list1.contains(i))
                return true;
        }
        return false;
    }

    private static boolean listSizeCheck(List<Integer> list1, List<Tile> list2, int size){
        Set<Integer> temp = new HashSet<>();
        temp.addAll(list1);

        if(temp.size()== size && list2.size() == size)
            return true;

        return false;
    }

    public static boolean removeNakedSubset(Tile[][] cPuzzle, List<Tile> tiles, List<Integer> list, House h){
        boolean result = false;

        if(h == House.ROW) {
            int y = tiles.get(0).getY();

            Set<Integer> temp = new HashSet<>();
            temp.addAll(list);

            for (int x = 0; x < cPuzzle.length; x++) {
                if (!tiles.contains(cPuzzle[x][y]) && cPuzzle[x][y].getCandidates().removeAll(temp)) {
                    result = true;

                }
            }
        } else if( h== House.COLUMN){
            int x = tiles.get(0).getX();

            Set<Integer> temp = new HashSet<>();
            temp.addAll(list);

            for(int y = 0 ; y <cPuzzle.length ; y++){
                if(!tiles.contains(cPuzzle[x][y]) && cPuzzle[x][y].getCandidates().removeAll(temp)){
                    result = true;

                }
            }
        } else if(h == House.FIELD){
            Set<Integer> temp = new HashSet<>();
            temp.addAll(list);

            for(Tile t: tiles.get(0).getField().getTiles()){
                if(!tiles.contains(t) && t.getCandidates().removeAll(temp)){
                    result = true;

                }
            }
        }
        return result;
    }
}
