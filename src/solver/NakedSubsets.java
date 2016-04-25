package solver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minit on 20-04-2016.
 */
public class NakedSubsets {

    public static boolean NakedPairs(Tile[][]cPuzzle){
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

                            if(GenericMethods.removeNakedPairRow(cPuzzle,pair)){

                                result = true;
                                System.out.println("Found naked pair at row " + cPuzzle[i][j].getY());
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

                            if(GenericMethods.removeNakedPairColumn(cPuzzle,pair)){
                                result = true;
                                System.out.println("Found naked pair at column " + cPuzzle[i][j].getX());
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

                            if(GenericMethods.removeNakedPairField(cPuzzle,pair)){
                                result = true;
                                System.out.println("Found naked pair in field " + t.getX()/3 + ", " + t.getY()/3);
                            }
                        }

                    }

                }

            }
        }


        return result;
    }

    public static boolean NakedTriples(Tile[][] cPuzzle) {
        boolean result = false;
        // running through all tiles
        for(int i = 0 ; i < cPuzzle.length ; i++){
            for(int j = 0 ; j <cPuzzle.length ; j++){

                /*
                Check to see if the empty tile has 3 or less candidates, but not 0.
                 */
                if(cPuzzle[i][j].getCandidates().size() <= 3 && cPuzzle[i][j].getDigit() == 0){

                    /*
                    Two lists are added. One is a list of all possible triple candidates.
                    This means that looking at tile with coordinate (i,j), we will make a list of all possible triple sets


                    The list candidates consists of all possible candidate matches for the tile with coordinate (i,j).
                    The list tiles consists of the different tiles being a part of this triple.
                    Pairing lists has the same indexing in the different lists.
                     */
                    List<List<Integer>> candidates = new ArrayList<>();
                    List<List<Tile>> tiles = new ArrayList<>();

                    // Row
                    // Checking for triples on the row where the tile(i,j) is located.
                    for(int x = i ; x < cPuzzle.length ; x++){

                        /*
                         If another tile (x,j) also has 3 or less candidates, we'll check if its part of a possible triple.
                         We do this by checking if the candidates are already in the list above.
                          */
                        if(cPuzzle[x][j].getCandidates().size() <= 3 && cPuzzle[x][j] != cPuzzle[i][j] && cPuzzle[x][j].getDigit() == 0){
                            boolean newCand = true;

                            // runs through all of the candidate lists, and checks for two different cases:
                            for(List l : candidates){
                                /*
                                 First case is that every candidate of the tile (x,j) is already in a candidate list,
                                 if this is the case it means that the tile (x,j) can be added the tile list,
                                 since it is a possible candidate for the triple.
                                  */
                                if(l.containsAll(cPuzzle[x][j].getCandidates())){
                                    newCand = false;
                                    tiles.get(candidates.indexOf(l)).add(cPuzzle[x][j]);

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to 3, if they are it means that we have found a correct triple,
                                     we then try to update the row, by removing the candidates of the triple from the rest of the row.
                                     */
                                    if(GenericMethods.sizeOfThree(l,tiles.get(candidates.indexOf(l)))){
                                        if(GenericMethods.removeNakedTripleRow(cPuzzle,tiles.get(candidates.indexOf(l)),l)){

                                            result = true;
                                            System.out.println("Found triples at row " + cPuzzle[i][j].getY() + " "+ l + " " + tiles.get(candidates.indexOf(l)));
                                        }
                                    }
                                /*
                                Second case is that some of the candidates of the tile (x,j) is already in a candidate list,
                                if this is the case we'll add all the new candidates to the list. This includes duplicates
                                but these will be removed when we check for the correctness of triples. We will also add the tile (x,j)
                                to the tiles list.
                                 */
                                }else if(GenericMethods.containsSome(l,cPuzzle[x][j].getCandidates())){
                                    newCand = false;
                                    tiles.get(candidates.indexOf(l)).add(cPuzzle[x][j]);
                                    l.addAll(cPuzzle[x][j].getCandidates());

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to 3, if they are it means that we have found a correct triple,
                                     we then try to update the row, by removing the candidates of the triple from the rest of the row.
                                     */
                                    if(GenericMethods.sizeOfThree(l,tiles.get(candidates.indexOf(l)))){
                                        if(GenericMethods.removeNakedTripleRow(cPuzzle,tiles.get(candidates.indexOf(l)),l)){

                                            result = true;
                                            System.out.println("Found triples at row " + cPuzzle[i][j].getY() +" "+ l + " " + tiles.get(candidates.indexOf(l)));
                                        }
                                    }
                                }
                            }

                            /*
                            Last case is that the candidates of tile (x,j) was not found in any of the candidates lists.
                            In this case we will create a new list for this possible triple. We will do this by adding
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

                                candidates.add(newCandList);
                                tiles.add(newTileList);
                            }



                        }

                    }

                    // Column
                    for(int y = i ; y < cPuzzle.length ; y++){

                        /*
                         If another tile (x,j) also has 3 or less candidates, we'll check if its part of a possible triple.
                         We do this by checking if the candidates are already in the list above.
                          */
                        if(cPuzzle[i][y].getCandidates().size() <= 3 && cPuzzle[i][y] != cPuzzle[i][j] && cPuzzle[i][y].getDigit() == 0){
                            boolean newCand = true;

                            // runs through all of the candidate lists, and checks for two different cases:
                            for(List l : candidates){
                                /*
                                 First case is that every candidate of the tile (x,j) is already in a candidate list,
                                 if this is the case it means that the tile (x,j) can be added the tile list,
                                 since it is a possible candidate for the triple.
                                  */
                                if(l.containsAll(cPuzzle[i][y].getCandidates())){
                                    newCand = false;
                                    tiles.get(candidates.indexOf(l)).add(cPuzzle[i][y]);

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to 3, if they are it means that we have found a correct triple,
                                     we then try to update the row, by removing the candidates of the triple from the rest of the row.
                                     */
                                    if(GenericMethods.sizeOfThree(l,tiles.get(candidates.indexOf(l)))){
                                        if(GenericMethods.removeNakedTripleRow(cPuzzle,tiles.get(candidates.indexOf(l)),l)){

                                            result = true;
                                            System.out.println("Found triples at column " + cPuzzle[i][j].getX() + " "+ l + " " + tiles.get(candidates.indexOf(l)));
                                        }
                                    }
                                /*
                                Second case is that some of the candidates of the tile (x,j) is already in a candidate list,
                                if this is the case we'll add all the new candidates to the list. This includes duplicates
                                but these will be removed when we check for the correctness of triples. We will also add the tile (x,j)
                                to the tiles list.
                                 */
                                }else if(GenericMethods.containsSome(l,cPuzzle[i][y].getCandidates())){
                                    newCand = false;
                                    tiles.get(candidates.indexOf(l)).add(cPuzzle[i][y]);
                                    l.addAll(cPuzzle[i][y].getCandidates());

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to 3, if they are it means that we have found a correct triple,
                                     we then try to update the row, by removing the candidates of the triple from the rest of the row.
                                     */
                                    if(GenericMethods.sizeOfThree(l,tiles.get(candidates.indexOf(l)))){
                                        if(GenericMethods.removeNakedTripleRow(cPuzzle,tiles.get(candidates.indexOf(l)),l)){

                                            result = true;
                                            System.out.println("Found triples at column " + cPuzzle[i][j].getX() +" "+ l + " " + tiles.get(candidates.indexOf(l)));
                                        }
                                    }
                                }
                            }

                            /*
                            Last case is that the candidates of tile (x,j) was not found in any of the candidates lists.
                            In this case we will create a new list for this possible triple. We will do this by adding
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

                                candidates.add(newCandList);
                                tiles.add(newTileList);
                            }



                        }

                    }


                    // Field
                    for(Tile t: cPuzzle[i][j].getField().getTiles()){

                        /*
                         If another tile (x,j) also has 3 or less candidates, we'll check if its part of a possible triple.
                         We do this by checking if the candidates are already in the list above.
                          */
                        if(t.getCandidates().size() <= 3 && t != cPuzzle[i][j] && t.getDigit() == 0){
                            boolean newCand = true;

                            // runs through all of the candidate lists, and checks for two different cases:
                            for(List l : candidates){
                                /*
                                 First case is that every candidate of the tile (x,j) is already in a candidate list,
                                 if this is the case it means that the tile (x,j) can be added the tile list,
                                 since it is a possible candidate for the triple.
                                  */
                                if(l.containsAll(t.getCandidates())){
                                    newCand = false;
                                    tiles.get(candidates.indexOf(l)).add(t);

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to 3, if they are it means that we have found a correct triple,
                                     we then try to update the row, by removing the candidates of the triple from the rest of the row.
                                     */
                                    if(GenericMethods.sizeOfThree(l,tiles.get(candidates.indexOf(l)))){
                                        if(GenericMethods.removeNakedTripleRow(cPuzzle,tiles.get(candidates.indexOf(l)),l)){

                                            result = true;
                                            System.out.println("Found triples at Field " + cPuzzle[i][j].getX()/3 + "," + cPuzzle[i][j].getY()/3 + " "+ l + " " + tiles.get(candidates.indexOf(l)));
                                        }
                                    }
                                /*
                                Second case is that some of the candidates of the tile (x,j) is already in a candidate list,
                                if this is the case we'll add all the new candidates to the list. This includes duplicates
                                but these will be removed when we check for the correctness of triples. We will also add the tile (x,j)
                                to the tiles list.
                                 */
                                }else if(GenericMethods.containsSome(l,t.getCandidates())){
                                    newCand = false;
                                    tiles.get(candidates.indexOf(l)).add(t);
                                    l.addAll(t.getCandidates());

                                    /*
                                    Every time any of the lists are updated, we check to see if both lists lengths are equal
                                    to 3, if they are it means that we have found a correct triple,
                                     we then try to update the row, by removing the candidates of the triple from the rest of the row.
                                     */
                                    if(GenericMethods.sizeOfThree(l,tiles.get(candidates.indexOf(l)))){
                                        if(GenericMethods.removeNakedTripleRow(cPuzzle,tiles.get(candidates.indexOf(l)),l)){

                                            result = true;
                                            System.out.println("Found triples at field " + cPuzzle[i][j].getX() + "," + cPuzzle[i][j].getY() +" "+ l + " " + tiles.get(candidates.indexOf(l)));
                                        }
                                    }
                                }
                            }

                            /*
                            Last case is that the candidates of tile (x,j) was not found in any of the candidates lists.
                            In this case we will create a new list for this possible triple. We will do this by adding
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

                                candidates.add(newCandList);
                                tiles.add(newTileList);
                            }



                        }

                    }


                }

            }
        }

        return result;

    }
}
