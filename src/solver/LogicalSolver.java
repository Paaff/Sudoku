package solver;

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
        while(proceed) {

            proceed = Singles.nakedSingle(cPuzzle) || Singles.hiddenSingle(cPuzzle)
            || Locked.lockedPointing(cPuzzle,cField) || Locked.lockedClaiming(cPuzzle)
            || NakedSubsets.nakedPairs(cPuzzle) || NakedSubsets.nakedLargeSubsets(cPuzzle,3)
            || HiddenSubsets.findHS(cPuzzle,2) || HiddenSubsets.findHS(cPuzzle,3)
            || NakedSubsets.nakedLargeSubsets(cPuzzle,4) || BasicFish.xWing(cPuzzle)
            || BasicFish.swordFish(cPuzzle,3) || BasicFish.swordFish(cPuzzle,4);



            if(!proceed){
                // When solver cannot continue, tel the brute force method to take over.

                new BruteForce().solver(cPuzzle);
            }
        }
    }



}
