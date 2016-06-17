package reader;

import solver.*;


/**
 * Created by minit on 25-02-2016.
 *
 * This class was made for testing the solvers. It called this class' main method will calculate
 * the run time for running the logical solver on 11 puzzle that was given by the instructor.
 */
public class TMain {

    static Tile[][] cPuzzle;
    static Field[][] cFields;

    static long starttime;
    public static void main(String[] args){
        int i = 1;
            // Run the file reader
            PuzzleReader reader = new PuzzleReader();
            cPuzzle = reader.runReader("puzzle3_clean.txt");
            reader.setUpFields();

            while (i <= 40) {
                starttime = System.currentTimeMillis();

                cPuzzle = reader.runReader("Puzzle3_" + i + ".txt");
                cFields = reader.setUpFields();

                // Run the puzzle checker
                PuzzleChecker checker = new PuzzleChecker();


                // LogicalSolver
                if (checker.runChecker(cPuzzle)) {
                    LogicalSolver.run(cPuzzle, cFields);
                } else {
                   System.out.print("Wont solve. "); // if the puzzle does not follow sudoku rules from the start.
                }

                final long endTime = System.currentTimeMillis();

                // displaying run time result.
                System.out.println( (endTime - starttime));
                i++;

            }
    }




}
