package solver;

/**
 * Created by minit on 02-03-2016.
 */

public class BruteForce {
/*
    int[][] puzzleCords;
    int[][] cPuzzle;
    int pos;

    public int[][] solver(int[][] cPuzzle){
        this.cPuzzle = cPuzzle;

        puzzleCords = new int[2][cPuzzle.length * cPuzzle.length];

        findEmptyCells();

        boolean solverDone= false;
        int cPos = 0;
        PuzzleChecker pChecker = new PuzzleChecker();

        while(!solverDone && cPos <= pos){
            int xPos = puzzleCords[0][cPos];
            int yPos = puzzleCords[1][cPos];
            cPuzzle[xPos][yPos]++;

            if(cPuzzle[xPos][yPos] > cPuzzle.length){
                if(cPos == 0){
                    solverDone = true;
                    System.out.println("No solution");
                }else{
                    cPos--;
                    System.out.println("Going back");
                    cPuzzle[xPos][yPos] = 0;
                }
            }else if(pChecker.checkCord(cPuzzle,xPos,yPos)){
                cPos++;
            }

            System.out.println(cPos +" / "+ pos);

        }

        return cPuzzle;

    }

    public void findEmptyCells(){
        pos = 0;
        for(int i = 0 ; i < cPuzzle.length ; i++){
            for(int j = 0 ; j < cPuzzle[i].length ; j++){
                if(cPuzzle[i][j] == 0){
                    puzzleCords[0][pos] = i;
                    puzzleCords[1][pos] = j;
                    pos++;
                }
            }
        }


    }*/
}
