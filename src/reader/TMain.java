package reader;

//import solver.BruteForce;
import solver.*;


/**
 * Created by minit on 25-02-2016.
 */
public class TMain {

    static Tile[][] cPuzzle;
    static Field[][] cFields;

    static long starttime;
    public static void main(String[] args){


        // Run the file reader
        PuzzleReader reader = new PuzzleReader();

        int i = 1;
      while(i <= 40) {
            starttime = System.currentTimeMillis();

            cPuzzle = reader.runReader("puzzle3_" + i + ".txt");
            cFields = reader.setUpFields();

            // Run the puzzle checker
            PuzzleChecker checker = new PuzzleChecker();
            //System.out.println("The puzzle is legit: " + checker.runChecker(cPuzzle));



            // LogicalSolver
            //new BruteForce().solver(cPuzzle);
            LogicalSolver.run(cPuzzle, cFields);

            //remove backslash for end result.
            //reader.pPuzzle(cPuzzle);

            final long endTime = System.currentTimeMillis();

            //System.out.println(/*"\nTotal Execution time: " +*/ (endTime - starttime));
           i++;

       }



       /*
        BruteForce bForce = new BruteForce();
        sPuzzle = bForce.solver(cPuzzle);

        Algorithm1.runAlgorihm(cPuzzle);
        reader.pPuzzle(cPuzzle);
        CandidateFinder.runFinder(cPuzzle);
        */
    }




}
