import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import reader.PuzzleReader;
import solver.Field;
import solver.Tile;

/**
 * Created by Paf on 24-02-2016.
 */
public class Controller {

   @FXML TextArea textAreaConsole;
   @FXML GridPane sudokuGrid;
    TextField sudokuGridTile = new TextField();
    PuzzleReader reader = new PuzzleReader();
    Field[][] cFields;

    int hintCounter = 0;




    // Methods for button presses
    public void startButtonClicked() {
        System.out.println("Clicked start.");
    }

    public void generateButtonClicked() {
        System.out.println("Clicked Generate Puzzle.");
        textAreaConsole.appendText("Generating Puzzle from this format:\n");
        reader.runReader(); // Reading the file where the puzzle is contained.
        cFields = reader.setUpFields();
        guiConsolPuzzlePrint(reader.sPuzzle, reader.pSize); // Method for printing the puzzle in the GUI Console.
        sudokuGridFill();
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

        for(Field f: cFields){
            for(Tile t: f.getTiles()){

            }
        }

        sudokuGridTile.setAlignment(Pos.CENTER);
        sudokuGridTile.setMaxHeight(50.0);
        sudokuGridTile.setMaxWidth(50.0);
        sudokuGridTile.setId("gridTile_" + row + col);
        sudokuGrid.add(sudokuGridTile, col, row);
        sudokuGridTile.setText("hejee");

    }

    // TextArea
    private void guiConsolPuzzlePrint(Tile[][] tPuzzle, int pSize){

        textAreaConsole.appendText("The size of the puzzle: " + pSize +"\n");

        for(int i = 0; i < pSize*pSize ; i++ ){
            if((i) % pSize == 0 && i != 0) {

                for(int y = 0 ; y < pSize*pSize ; y++) {
                    textAreaConsole.appendText("---"); // Line breaks for showing the n*n fields
                }
                textAreaConsole.appendText("\n");
            }

            for(int j = 0; j < pSize*pSize ; j++){
                if((1 + j) % pSize == 0 && j != pSize*pSize-1){
                    textAreaConsole.appendText(tPuzzle[j][i].getDigit() + "  |  "); // Line breaks for showing the n*n fields
                }else {
                    textAreaConsole.appendText(tPuzzle[j][i].getDigit() + "   ");
                }


            }

            textAreaConsole.appendText("\n");

        }



    }
}

