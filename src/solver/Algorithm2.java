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

       //  System.out.println("\nOperations used:");
        while(proceed) {

            proceed = false;

            if(Singles.nakedSingle(cPuzzle)){
                proceed = true;
                System.out.println("Naked Single");
            } else if(Singles.hiddenSingle(cPuzzle)){
                proceed = true;
                System.out.println("Hidden Single");
            } else if(Locked.lockedPointing(cPuzzle,cField)){
                proceed = true;
                System.out.println("Lock pointing");
            } else if(Locked.lockedClaiming(cPuzzle)){
                proceed = true;
                System.out.println("Locked Claiming");
            } else if(NakedSubsets.NakedPairs(cPuzzle)){
                proceed = true;
                System.out.println("Naked Pairs");
            } else if(NakedSubsets.nakedLargeSubsets(cPuzzle, 3)){
                proceed = true;
                System.out.println("Naked Subsets");
            } else if(HiddenSubsets.hiddenPairs(cPuzzle, 2)){
                proceed = true;
                System.out.println("Hidden Pairs");
            } else if(HiddenSubsets.hiddenPairs(cPuzzle,3)){
                proceed = true;
                System.out.println("hidden triple");
            } else if(NakedSubsets.nakedLargeSubsets(cPuzzle, 4)){
                proceed = true;
                System.out.println("Naked Subset");
            } else if(BasicFish.xWing(cPuzzle)){
                proceed = true;
                System.out.println("x Wing");
            } else if(BasicFish.swordFish(cPuzzle, 3)){
                proceed = true;
                System.out.println("sword fish");
            } else if(BasicFish.swordFish(cPuzzle, 4)){
                proceed = true;
                System.out.println("sword fish 4");
            }

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
