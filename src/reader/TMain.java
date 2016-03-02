package reader;

//import solver.BruteForce;
import solver.Field;
import solver.PuzzleChecker;
import solver.Tile;


/**
 * Created by minit on 25-02-2016.
 */
public class TMain {

    static Tile[][] cPuzzle;
    static Field[][] cFields;
    static int[][] sPuzzle;

    public static void main(String[] args){
        // Run the file reader
        PuzzleReader reader = new PuzzleReader();
        cPuzzle = reader.runReader();
        cFields = reader.setUpFields();

        // Run the puzzle checker
        PuzzleChecker checker = new PuzzleChecker();
        System.out.println(checker.runChecker(cPuzzle));
/*
        BruteForce bForce = new BruteForce();
        sPuzzle = bForce.solver(cPuzzle);

        reader.pPuzzle(sPuzzle);*/
    }


}
