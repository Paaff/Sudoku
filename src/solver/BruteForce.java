package solver;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by minit on 02-03-2016.
 */

public class BruteForce {

    int[][] puzzleCords;
    Tile[][] cPuzzle;

    public void solver(Tile[][] cPuzzle){
        this.cPuzzle = cPuzzle;

        puzzleCords = new int[2][cPuzzle.length * cPuzzle.length];
        /*
            2 double lists managing the coordinates of the empty cells.
            One for the ones yet to be evaluated and on for the ones that has been evaluated.
         */
        List<List<Integer>> cords = findEmptyCells(cPuzzle);
        List<List<Integer>> checkedCords = new ArrayList<List<Integer>>();
        checkedCords.add(new ArrayList<>());
        checkedCords.add(new ArrayList<>());

        // Variable for remembering the number of empty cells in the beginning.
        int oldSize = cords.get(0).size();

        boolean solverDone = false;
        PuzzleChecker pChecker = new PuzzleChecker();

        while(!solverDone && !cords.get(0).isEmpty() && checkedCords.get(0).size() <= oldSize){
            int xPos = cords.get(0).get(0); // the current x coordinate
            int yPos = cords.get(1).get(0); // the current y coordinate

            int i = cPuzzle[xPos][yPos].getDigit()+1;   // incrementing the current digit.
            cPuzzle[xPos][yPos].setDigit(i);

            /*
                Checking if the digit has crossed the maximum.
             */
            if(cPuzzle[xPos][yPos].getDigit() > cPuzzle.length){
                /*
                    If the cell is the first empty cell and has crossed the maximum,
                    there cant be any solution for the puzzle.
                 */
                if(checkedCords.get(0).size() == 0){
                    solverDone = true;
                    cPuzzle[xPos][yPos].setDigit(0);
                    /*
                        otherwise, look back one cell to an already checked empty cell,
                        and reevaluate with incrementing the number.
                        "Popping" from the 'checked' stack and pushing back to the 'pending' stack.
                     */
                }else{
                    cPuzzle[xPos][yPos].setDigit(0);
                    xPos=checkedCords.get(0).get(0);
                    yPos=checkedCords.get(1).get(0);

                    cords.get(0).add(0,xPos);
                    cords.get(1).add(0,yPos);

                    checkedCords.get(0).remove(0);
                    checkedCords.get(1).remove(0);
                }
                /*
                    If the proposed digit is valid, pop coordinates from 'pending' stack to 'checked' stack.
                 */
            }else if(pChecker.checkCord(cPuzzle,xPos,yPos)){
                checkedCords.get(0).add(0,xPos);
                checkedCords.get(1).add(0,yPos);

                cords.get(0).remove(0);
                cords.get(1).remove(0);

            }


        }


    }

    private List findEmptyCells(Tile[][] cPuzzle){
        List<List<Integer>> coords = new ArrayList<>();
        coords.add(new ArrayList<>());
        coords.add(new ArrayList<>());

        for(int i =0 ; i < cPuzzle.length ; i++){
            for(int j = 0 ; j <  cPuzzle.length ; j++){
                if(cPuzzle[i][j].getDigit() == 0){
                    coords.get(0).add(i);
                    coords.get(1).add(j);
                }
            }
        }
        return coords;

    }

}
