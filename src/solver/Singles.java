package solver;

/**
 * Created by Pete Laptop on 13-04-2016.
 */

/*
    This class contains methods for finding so called "Singles" in the sudoku puzzle.
 */

public class Singles {

    // Method checking that the candidate list for a cell only contains of a single candidate.
    public static boolean nakedSingle(Tile[][] cPuzzle) {
        boolean result = false;

            for(int i = 0; i < cPuzzle.length; i++) {
                for(int j = 0; j < cPuzzle.length; j++){
                    if(cPuzzle[i][j].getCandidates().size() == 1) {

                        int digit = cPuzzle[i][j].getCandidates().get(0);
                        // Update the naked single to be the digit.
                        cPuzzle[i][j].setAndClear(digit);
                        result = true;

                        removeCandidate(cPuzzle,digit,cPuzzle[i][j]);

                    }
                }
            }
        return result;
    }


    public static boolean hiddenSingle(Tile[][] cPuzzle){

        boolean result = false;
        // Go through 1 - 9 in candidates.

        for(int i = 1; i <= cPuzzle.length; i++) {

            // Looping through the entire puzzle.
            for(int j = 0; j < cPuzzle.length; j++) {
                for(int k = 0; k < cPuzzle.length; k++) {

                    if(cPuzzle[j][k].getCandidates().contains(i)) {

                        int xCount=0;
                        int yCount=0;
                        int fieldCount=0;

                        // Case 1 X-axis
                        for(int x = 0; x < cPuzzle.length; x++) {
                            if(cPuzzle[x][k].getCandidates().contains(i)
                                    && cPuzzle[x][k] != cPuzzle[j][k]){
                                xCount++;
                            }
                        }



                        // Case 2
                        for(int y = 0; y < cPuzzle.length ; y++){
                            if (cPuzzle[j][y].getCandidates().contains(i)
                                    && cPuzzle[j][y] != cPuzzle[j][k]) {
                                yCount++;
                            }
                        }



                        // Case 3
                        for(Tile t : cPuzzle[j][k].getField().getTiles()){
                            if(t.getCandidates().contains(i) && t != cPuzzle[j][k]){
                                fieldCount++;
                            }
                        }

                        if(xCount==0 || yCount==0 || fieldCount ==0){

                            //then hidden single
                            result = true;

                            cPuzzle[j][k].setAndClear(i);

                            removeCandidate(cPuzzle,i,cPuzzle[j][k]);
                        }


                    }


                }
            }



        }
        return result;

    }
    // Method that removes the placed candidate from all of the tiles in the same houses as the specific tile.
    private static void removeCandidate(Tile[][] puzzle, int digit, Tile t) {
        for (Tile s : t.getField().getTiles()) {
            s.getCandidates().remove(new Integer(digit));
        }

        for(int i = 0 ; i< puzzle.length ;i++){
            puzzle[t.getX()][i].getCandidates().remove(new Integer(digit));

            puzzle[i][t.getY()].getCandidates().remove(new Integer(digit));
        }


    }


}
