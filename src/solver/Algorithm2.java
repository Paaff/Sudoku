package solver;

/**
 * Created by Pete Laptop on 13-04-2016.
 */

/*
    This class will be the algorithms main.
 */
public class Algorithm2 {


    public static void runAlgorithm2(Tile[][] cPuzzle, Field[][] cField) {


        boolean proceed = true;


        while(proceed) {

            proceed = Singles.nakedSingle(cPuzzle) || Singles.hiddenSingle(cPuzzle)
            || Locked.lockedPointing(cPuzzle,cField) || Locked.lockedClaiming(cPuzzle)
            || NakedSubsets.NakedPairs(cPuzzle) || NakedSubsets.nakedLargeSubsets(cPuzzle, 3)
            || HiddenSubsets.hiddenPairs(cPuzzle, 2) || HiddenSubsets.hiddenPairs(cPuzzle,3)
            || NakedSubsets.nakedLargeSubsets(cPuzzle, 4) || BasicFish.xWing(cPuzzle)
            || BasicFish.swordFish(cPuzzle, 3) || BasicFish.swordFish(cPuzzle, 4);

            if(!proceed){
                // algorithm is executed, solved the possible or could not solve it
                CandidateFinder.runFinder(cPuzzle);

            }


        }


    }



}
