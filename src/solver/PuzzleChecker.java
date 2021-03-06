package solver;

/**
 * Created by minit on 01-03-2016.
 */
public class PuzzleChecker {

    public boolean runChecker(Tile[][] cPuzzle){

        if(cPuzzle == null)
            return false;

        for(int i = 0; i < cPuzzle.length ; i++ ){
            for(int j = 0; j < cPuzzle[i].length ; j++){
                if (!checkCord(cPuzzle, i, j )){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkCord(Tile[][] cPuzzle, int x, int y){
        // Base case, where there is no number on the field.
        if(cPuzzle[x][y].getDigit() == 0)
            return true;

        // Checking x-axis
        for(int i = 0; i < cPuzzle.length ; i++){
            if (i != x && cPuzzle[x][y].getDigit() == cPuzzle[i][y].getDigit()) {
                return false;
            }
        }
        // Checking y-axis
        for(int i = 0; i < cPuzzle.length ; i++){
            if(i != y && cPuzzle[x][y].getDigit() == cPuzzle[x][i].getDigit()) {
                return false;
            }
        }

        // Checking the field
        for(Tile t: cPuzzle[x][y].getField().getTiles()){
            if(t != cPuzzle[x][y] && t.getDigit() == cPuzzle[x][y].getDigit()){
                return false;
            }
        }

        return true;
    }

}
