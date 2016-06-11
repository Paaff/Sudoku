package solver;

import java.util.concurrent.locks.Lock;

/**
 * Created by Pete Laptop on 13-04-2016.
 */

/*
    This class will be the algorithms main.
 */
public class LogicalSolver {


    public static void run(Tile[][] cPuzzle, Field[][] cField) {


        boolean proceed = true;

        CandidateFinder.runFinder(cPuzzle);

       //  System.out.println("\nOperations used:");
        while(proceed) {

            proceed = Singles.nakedSingle(cPuzzle) || Singles.hiddenSingle(cPuzzle)
            || Locked.lockedPointing(cPuzzle,cField) || Locked.lockedClaiming(cPuzzle)
            || NakedSubsets.NakedPairs(cPuzzle) || NakedSubsets.nakedLargeSubsets(cPuzzle,3)
            || HiddenSubsets.hiddenPairs(cPuzzle,2) || HiddenSubsets.hiddenPairs(cPuzzle,3)
            || NakedSubsets.nakedLargeSubsets(cPuzzle,4) || BasicFish.xWing(cPuzzle)
            || BasicFish.swordFish(cPuzzle,3) || BasicFish.swordFish(cPuzzle,4);



            if(!proceed){
                /* The Algorithm has exhausted all it's methods and it either could solve it completely or it has come to a stand still.
                   If it couldn't solve it, use brute force to solve the remaining.
                   So basically our brute force method acts as method to finish the puzzle and if it's already solved, a checker to see that it actually is completely done.
                */

                new BruteForce().solver(cPuzzle);





                // This method has no reason to be run here other than using its print method when implementing new methods.
                // CandidateFinder.runFinder(cPuzzle);

            }


        }

        //System.out.println();


    }



}
