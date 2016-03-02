import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import reader.PuzzleReader;
/**
 * Created by Paf on 24-02-2016.
 */
public class Controller {

   @FXML TextArea textAreaConsole;
   @FXML GridPane sudokuGrid;
    PuzzleReader reader = new PuzzleReader();
    int hintCounter = 0;



    // Methods for button presses
    public void startButtonClicked() {
        System.out.println("Clicked start.");
    }

    public void generateButtonClicked() {
        System.out.println("Clicked Generate Puzzle.");
        textAreaConsole.appendText("Generating Puzzle from this format:\n");
        reader.runReader(); // Reading the file where the puzzle is contained.
        guiConsolPuzzlePrint(reader.sPuzzle, reader.pSize); // Method for printing the puzzle in the GUI Console.

    }

    public void solveButtonClicked() {
        System.out.println("Clicked Solve Puzzle.");
    }

    public void analyzeButtonClicked() {
        System.out.println("Clicked Analyze Solver.");
    }

    public void optionsButtonClicked() {
        System.out.println("Clicked Options.");
    }

    public void exitButtonClicked() {
        System.out.println("Clicked Exit.");
    }

    public void hintButtonClicked() {
        System.out.println("Clicked Hint.");
        textAreaConsole.appendText("### Hint " + hintCounter + " ###\n");
        hintCounter++;
    }

    public void goBackOneStepButtonClicked() {
        System.out.println("Clicked Go Back One Step.");
    }

    public void startTimerButtonClicked() {
        System.out.println("Clicked Start Timer.");
    }

    public void pauseTimerButtonClicked() {
        System.out.println("Clicked Pause Timer.");
    }


    // SudokuGrid
    private void sudokuGridFill() {
        int col = 0, row = 0;
        for (Node node : sudokuGrid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {

            }
        }


    }

    // TextArea
    private void guiConsolPuzzlePrint(int[][] tPuzzle, int pSize){


        textAreaConsole.appendText("Cell size chosen: " + pSize+"\n");


        for(int i = 0; i < pSize*pSize ; i++ ){
            if(i == 3 || i == 6) {
                for(int y = 0 ; y < 9 ; y++) {
                    textAreaConsole.appendText("----"); // Line breaks for showing the n*n fields
                }
                textAreaConsole.appendText("\n");
            }

            for(int j = 0; j < pSize*pSize ; j++){
                if(j == 2 || j == 5){
                    textAreaConsole.appendText(tPuzzle[j][i] + "   |   "); // Line breaks for showing the n*n fields
                }else {
                    textAreaConsole.appendText(tPuzzle[j][i] + "   ");
                }


            }
            textAreaConsole.appendText("\n");
        }
    }
}

