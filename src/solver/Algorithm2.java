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

            proceed = false;

            if(Singles.nakedSingle(cPuzzle)){
                proceed = true;
            } else if(Singles.hiddenSingle(cPuzzle)){
                proceed = true;
            } else if(Locked.lockedPointing(cPuzzle,cField)){
                proceed = true;
            } else if(Locked.lockedClaiming(cPuzzle)){
                proceed = true;
            } else if(NakedSubsets.NakedPairs(cPuzzle)){
                proceed = true;
            } else if(NakedSubsets.nakedLargeSubsets(cPuzzle, 3)){
                proceed = true;
            } else if(HiddenSubsets.hiddenPairs(cPuzzle, 2)){
                proceed = true;
            } else if(HiddenSubsets.hiddenPairs(cPuzzle,3)){
                proceed = true;
            } else if(NakedSubsets.nakedLargeSubsets(cPuzzle, 4)){
                proceed = true;
            } else if(BasicFish.xWing(cPuzzle)){
                proceed = true;
            } else if(BasicFish.swordFish(cPuzzle, 3)){
                proceed = true;
            } else if(BasicFish.swordFish(cPuzzle, 4)){
                proceed = true;
            }

            if(!proceed){
                /* The Algorithm has exhausted all it's methods and it either could solve it completely or it has come to a stand still.
                   If it couldn't solve it, use brute force to solve the remaining.
                   So basically our brute force method acts as method to finish the puzzle and if it's already solved, a checker to see that it actually is completely done.
                */

                BruteForce bruteForce = new BruteForce();
                cPuzzle = bruteForce.solver(cPuzzle);




                // This method has no reason to but run here other than using its print method when implementing new methods.
                // CandidateFinder.runFinder(cPuzzle);

            }


        }


    }



}
