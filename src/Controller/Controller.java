package Controller;

import View.SudokuButton;
import View.SudokuChooserDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import reader.PuzzleReader;
import solver.*;
import java.util.Optional;


/**
 * Created by Paf on 24-02-2016.
 */
public class Controller {


    @FXML
    TextArea textAreaConsole;
    @FXML
    GridPane sudokuGrid;

    PuzzleReader reader = new PuzzleReader();
    PuzzleChecker puzzleChecker = new PuzzleChecker();

    static Tile[][] cPuzzle;
    static Field[][] cFields;

    SudokuButton sudokuButton;
    SudokuChooserDialog sudokuChooserDialog = new SudokuChooserDialog();
    String selectedPuzzle;
    private boolean generatedBefore;


    // Methods for button presses
    public void startButtonClicked() {
        puzzleChooserDialog();

    }

    public void generateSudoku() {
        System.out.println("Clicked Generate Puzzle.");
        textAreaConsole.clear();
        textAreaConsole.appendText("Generating Puzzle from this format:\n");

        // Reading the file where the puzzle is contained and loading it and setting up the fields of the puzzle.

        if(selectedPuzzle == null){
            textAreaConsole.appendText("\nSomething went wrong with the selected of the puzzle.\nA clean slate was loaded as a default.");
            cPuzzle = reader.runReader("puzzle3_clean.txt");
        }else {
            cPuzzle = reader.runReader(selectedPuzzle);
        }

        cFields = reader.setUpFields();

        // Method for printing the puzzle in the GUI Console.
        guiConsolPuzzlePrint(cPuzzle, reader.pSize);

        // Using cPuzzle to get all the loaded sudoku numbers onto individual buttons.
        sudokuGridButtons();


    }

    public void solveButtonClicked() {


        if(puzzleChecker.runChecker(cPuzzle)){
            textAreaConsole.appendText("\nThe current Sudoku is valid.\nSolving will now commence.");
            CandidateFinder.runFinder(cPuzzle);
            LogicalSolver.run(cPuzzle, cFields);

            SudokuButton temp;
            for (int i = 0; i < cPuzzle.length; i++) {
                for (int j = 0; j < cPuzzle.length; j++) {


                        temp = (SudokuButton) getNodeFromSudokuGrid(sudokuGrid,i,j);


                        if(cPuzzle[i][j].getDigit() == 0){
                           // temp.setNotValidStyle();
                            temp.setText("ERROR");

                        } else {
                         //   temp.setValidStyle();
                            temp.setText(cPuzzle[i][j].getDigit() + "");
                        }



                }

            }
        } else {
            textAreaConsole.appendText("\n\nYou tried to use the solver on a invalid Sudoku.\nThe solver will not run untill you provide a valid starting Sudoku.");
        }






    }

    public void exitButtonClicked() {
        System.out.println("Clicked Exit.");
        System.exit(0);
    }

    // Different scenarios depending on the user input on the button.
    private void chooseASudokuNumber(KeyEvent keyEvent) {

        //System.out.print(keyEvent.getTarget().toString());
        if (keyEvent.getCode() != KeyCode.DIGIT0) {

            switch (keyEvent.getCode()) {
                case DIGIT1:
                    // Set the text of the button if 1 is pressed on keyboard.
                    ((SudokuButton) keyEvent.getTarget()).setButtonText("1");
                    break;
                case DIGIT2:
                    ((SudokuButton) keyEvent.getTarget()).setText("2");
                    break;
                case DIGIT3:
                    ((SudokuButton) keyEvent.getTarget()).setText("3");
                    break;
                case DIGIT4:
                    ((SudokuButton) keyEvent.getTarget()).setText("4");
                    break;
                case DIGIT5:
                    ((SudokuButton) keyEvent.getTarget()).setText("5");
                    break;
                case DIGIT6:
                    ((SudokuButton) keyEvent.getTarget()).setText("6");
                    break;
                case DIGIT7:
                    ((SudokuButton) keyEvent.getTarget()).setText("7");
                    break;
                case DIGIT8:
                    ((SudokuButton) keyEvent.getTarget()).setText("8");
                    break;
                case DIGIT9:
                    ((SudokuButton) keyEvent.getTarget()).setText("9");
                    break;
                case BACK_SPACE:
                    ((SudokuButton) keyEvent.getTarget()).setText("");
                    break;



            }

            // Load this specific number into cPuzzle by taking the text from the button and placing it in the stored puzzle.
            // Also check if this new value is valid or not.
          validNumberChecker((SudokuButton) keyEvent.getTarget());


        }
    }

    // Uses the PuzzleChecker to check if the number chosen on a button is valid / not valid.
    // The Valid checking not used anymore, but the feedback to the user is still useful 10-06
    private void validNumberChecker(SudokuButton button) {

        if (!button.getButtonText().equals("")) {
            // Take the button text, convert it to integer and insert it in the sPuzzle using the coordinates from the button.
            // These coords matches with how the sPuzzle is set up.
            int userDigit = Integer.parseInt(button.getButtonText());
            cPuzzle[button.getButtonXPos()][button.getButtonYPos()].setDigit(userDigit);
            System.out.println(cPuzzle[button.getButtonXPos()][button.getButtonYPos()].getDigit() + ": New value set in the cPuzzle");
            textAreaConsole.appendText("\nYou have placed number: " + userDigit + " on position " + button.getButtonXPos() +", " + button.getButtonYPos());


            if (puzzleChecker.runChecker(cPuzzle)) {
             //   button.setValidStyle();
                System.out.println("valid");
            } else {
              //  button.setNotValidStyle();
                System.out.println("not valid");
            }
        } else {

            // Ensure that when we use the backspace to delete a number, that it also gets updated in the cPuzzle to a NULL , in our case a 0 is the NULL
            cPuzzle[button.getButtonXPos()][button.getButtonYPos()].setDigit(0);
        }


    }


    // Creating sudokuGridButtons.
    private void sudokuGridButtons() {

        if(!generatedBefore) {

            // Insert a sudokuButton for each "place" in the puzzle.
            int digit;
            for (int i = 0; i < cPuzzle.length; i++) {
                for (int j = 0; j < cPuzzle.length; j++) {

                    digit = cPuzzle[i][j].getDigit();

                    sudokuButton = new SudokuButton("" + digit, i, j);

                    coloringButton(sudokuButton, cPuzzle, i, j);

                    sudokuButton.setOnKeyPressed(this::chooseASudokuNumber);

                    sudokuGrid.add(sudokuButton, i, j);

                    generatedBefore = true;
                }
            }

            // If we already have the buttons generated, we just need to alter the text they have written on them instead of creating a whole bunch of new ones.
        } else
        {
            int digit;
            for (int i = 0; i < cPuzzle.length; i++) {
                for (int j = 0; j < cPuzzle.length; j++) {

                    digit = cPuzzle[i][j].getDigit();

                    sudokuButton = (SudokuButton) getNodeFromSudokuGrid(sudokuGrid,i,j);

                    if(digit == 0) {
                        sudokuButton.setText("");
                    } else {
                        sudokuButton.setText(digit +"");
                    }

                }
            }
        }
    }

    // TextArea (Prints to the GUI console
    private void guiConsolPuzzlePrint(Tile[][] tPuzzle, int pSize) {

        textAreaConsole.appendText("The size of the puzzle: " + pSize + "\n");

        for (int i = 0; i < pSize * pSize; i++) {
            if ((i) % pSize == 0 && i != 0) {

                for (int y = 0; y < pSize * pSize; y++) {
                    textAreaConsole.appendText("---"); // Line breaks for showing the n*n fields
                }
                textAreaConsole.appendText("\n");
            }

            for (int j = 0; j < pSize * pSize; j++) {
                if ((1 + j) % pSize == 0 && j != pSize * pSize - 1) {
                    textAreaConsole.appendText(tPuzzle[j][i].getDigit() + "  |  "); // Line breaks for showing the n*n fields
                } else {
                    textAreaConsole.appendText(tPuzzle[j][i].getDigit() + "   ");
                }


            }

            textAreaConsole.appendText("\n");

        }


    }

    // Activates a popup dialog for choosing a puzzle.
    private void puzzleChooserDialog() {

        Optional<String> result = sudokuChooserDialog.showAndWait();
        String answerSelected = "";

        if (result.isPresent()) {
            answerSelected = result.get();

        } else {
            textAreaConsole.appendText("\nYou didnt make a choice for a specific puzzle so we loaded the same as number 2.");
            answerSelected = "clean";

        }

        selectedPuzzle = "puzzle3_" +answerSelected+".txt";
        generateSudoku();

    }

    // Method for retrieving a sudokubutton from the sudokugrid by the coloumn and row index.
    private Node getNodeFromSudokuGrid(GridPane sudokuGrid, int coloumn, int row) {
        for (Node node : sudokuGrid.getChildren()) {
            if (sudokuGrid.getColumnIndex(node) == coloumn && sudokuGrid.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    // Method for differenciating between the different "Fields" in the sudoku grid so it's more appealing to the user.
    private void coloringButton(SudokuButton sudokuButton, Tile[][] cPuzzle, int i, int j) {

        if((cPuzzle[i][j].getX()/3 == 1 && cPuzzle[i][j].getY()/3 == 0) ||
                (cPuzzle[i][j].getX()/3 == 0 && cPuzzle[i][j].getY()/3 == 1) ||
                (cPuzzle[i][j].getX()/3 == 2 && cPuzzle[i][j].getY()/3 == 1) ||
                (cPuzzle[i][j].getX()/3 == 1 && cPuzzle[i][j].getY()/3 == 2)) {
            sudokuButton.setStyle("-fx-background-color: lightskyblue;");
        } else {
            sudokuButton.setStyle("-fx-background-color: bisque;");
        }

    }

    public void validateSolutionButtonClicked() {

        textAreaConsole.appendText("\nChecking the puzzle.");
        if(puzzleChecker.runChecker(cPuzzle)) {
            textAreaConsole.appendText("\n - There was no errors in the current puzzle.");
        } else {
            textAreaConsole.appendText("\n - There is one or more errors in the puzzle.\nCheck that there are no numbers violating the Sudoku rules.");
        }

    }
}
