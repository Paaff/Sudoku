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

            // Checking if any progress on the solution can be made by finding a Naked Single.
            if(Singles.nakedSingle(cPuzzle)) {
                // Shit son
                proceed=true;
            }
            // Checking if any progress on the solution can be made by finding a Hidden Single.
            else if(Singles.hiddenSingle(cPuzzle)) {
                proceed=true;
            }

            else if(Locked.lockedPointing(cPuzzle,cField)){
                proceed=true;

            }

            else if(Locked.lockedClaiming(cPuzzle)){
                proceed = true;
            }


            if(!proceed){
                // algorithm is proceed, solved the possible or couldnt solve it

            }


        }


    }



}