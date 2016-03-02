package solver;

/**
 * Created by minit on 02-03-2016.
 */

public class BruteForce {

    int[][] puzzleCords;
    Tile[][] cPuzzle;
    int pos;

    public Tile[][] solver(Tile[][] cPuzzle){
        this.cPuzzle = cPuzzle;

        puzzleCords = new int[2][cPuzzle.length * cPuzzle.length];

        findEmptyCells();

        boolean solverDone= false;
        int cPos = 0;
        PuzzleChecker pChecker = new PuzzleChecker();
        int empty = pos;

        while(!solverDone && cPos <= pos && empty != 0){
            int xPos = puzzleCords[0][cPos];
            int yPos = puzzleCords[1][cPos];
            int i = cPuzzle[xPos][yPos].getDigit() + 1;
            cPuzzle[xPos][yPos].setDigit(i);

            if(cPuzzle[xPos][yPos].getDigit() > cPuzzle.length){
                if(cPos == 0){
                    solverDone = true;
                    cPuzzle[xPos][yPos].setDigit(0);
                }else{
                    cPos--;
                    cPuzzle[xPos][yPos].setDigit(0);
                    empty++;
                }
            }else if(pChecker.checkCord(cPuzzle,xPos,yPos)){
                cPos++;
                empty--;
            }


        }

        return cPuzzle;

    }

    /*
        Defining all of the empty cells in the puzzle
     */
    public void findEmptyCells(){
        pos = 0;
        for(int i = 0 ; i < cPuzzle.length ; i++){
            for(int j = 0 ; j < cPuzzle[i].length ; j++){
                if(cPuzzle[i][j].getDigit() == 0){
                    puzzleCords[0][pos] = i;
                    puzzleCords[1][pos] = j;
                    pos++;
                }
            }
        }


    }
}
