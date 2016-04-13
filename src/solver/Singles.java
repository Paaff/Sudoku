package solver;

/**
 * Created by Pete Laptop on 13-04-2016.
 */

/*
    This class contains methods for finding so called "Singles" in the sudoku puzzle.
 */

public class Singles {

    // Method checking that the candidate list for a cell only contains of a single candidate.
    public static void nakedSingle(Tile[][] cPuzzle) {
            for(int i = 0; i < cPuzzle.length; i++) {
                for(int j = 0; j < cPuzzle.length; j++){
                    if(cPuzzle[i][j].getCandidates().size() == 1) {

                        // Update the naked single to be the digit.
                        cPuzzle[i][j].setDigit(cPuzzle[i][j].getCandidates().get(0));
                        System.out.println("Found a naked singe"+cPuzzle[i][j].getCandidates().get(0)
                                +  "at: " + i + ","+j);

                    }
                }
            }

    }


    public static void hiddenSingle(Tile[][] cPuzzle){




    }




}
