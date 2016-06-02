package reader;

//import solver.BruteForce;
import solver.*;


/**
 * Created by minit on 25-02-2016.
 */
public class TMain {

    static Tile[][] cPuzzle;
    static Field[][] cFields;
    static Tile[][] sPuzzle;

    static long starttime;
    public static void main(String[] args){


        // Run the file reader
        PuzzleReader reader = new PuzzleReader();

        int i = 0;
        while(i <= 13) {
            starttime = System.currentTimeMillis();
            i++;

            cPuzzle = reader.runReader(fileName(i));
            cFields = reader.setUpFields();

            // Run the puzzle checker
            PuzzleChecker checker = new PuzzleChecker();
            //System.out.println("The puzzle is legit: " + checker.runChecker(cPuzzle));

            CandidateFinder.runFinder(cPuzzle);

            // Algorithm2
            new BruteForce().solver(cPuzzle);
            //Algorithm2.runAlgorithm2(cPuzzle, cFields);

            //remove backslash for end result.
            // reader.pPuzzle(cPuzzle);

            final long endTime = System.currentTimeMillis();

            System.out.println(/*"\nTotal Execution time: " +*/ (endTime - starttime));
        }



       /*
        BruteForce bForce = new BruteForce();
        sPuzzle = bForce.solver(cPuzzle);

        Algorithm1.runAlgorihm(cPuzzle);
        reader.pPuzzle(cPuzzle);
        CandidateFinder.runFinder(cPuzzle);
        */
    }

    static String fileName(int i){
        String s;
        switch(i){
            case 1:
                s = "puzzle2_1.txt";
                break;
            case 2:
                s = "puzzle3_1.txt";
                break;
            case 3:
                s = "puzzle3_2.txt";
                break;
            case 4:
                s = "puzzle3_20.txt";
                break;
            case 5:
                s = "puzzle3_4.txt";
                break;
            case 6:
                s = "puzzle3_5.txt";
                break;
            case 7:
                s = "puzzle3_6.txt";
                break;
            case 8:
                s = "puzzle3_7.txt";
                break;
            case 9:
                s = "puzzle3_8.txt";
                break;
            case 10:
                s = "puzzle3_9.txt";
                break;
            case 11:
                s = "puzzle3_10.txt";
                break;
            case 12:
                s = "puzzle3_11.txt";
                break;
            case 13:
                s = "puzzle3_12.txt";
                break;

            case 14:
                s="puzzle4_1.txt";
                break;

            default:
                s= null;
        }

        return s;
    }


}
