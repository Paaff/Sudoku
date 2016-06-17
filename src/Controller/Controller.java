package Controller;

import View.SudokuButton;
import View.SudokuChooserDialog;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import reader.PuzzleReader;
import solver.*;
import java.util.Optional;


/**
 * Created by Paf on 24-02-2016.
 */
public class Controller {

    // FXML Fields.
    @FXML
    TextArea textAreaConsole;
    @FXML
    GridPane sudokuGrid;

    // Solver Fields.
    PuzzleReader reader = new PuzzleReader();
    PuzzleChecker puzzleChecker = new PuzzleChecker();
    static Tile[][] cPuzzle;
    static Field[][] cFields;

    // Controller fields.
    SudokuButton sudokuButton;
    SudokuChooserDialog sudokuChooserDialog = new SudokuChooserDialog();
    private String selectedPuzzle;
    private boolean generatedBefore;
    private String typedString = "";
    private boolean sameSizeGrid;
    private int lastGridSize;


    // If Start Button is clicked:
    public void startButtonClicked() {
        puzzleChooserDialog();

    }


    /*
        Generatas the sudoku the user has chosen. If the user has never chosen one, load a clean puzzle.
        If the user has chosen one before, tries to choose a new one, but exits out of the chooser, load the latest one the user loaded.
     */
    public void generateSudoku() {
        textAreaConsole.clear();
        textAreaConsole.appendText("Generating Puzzle from this format:\n");

        // The selectedPuzzle string is a string that contains the users choice.
        if(selectedPuzzle == null){
            textAreaConsole.appendText("\nSomething went wrong with the selected of the puzzle.\nA clean slate was loaded as a default.");
            cPuzzle = reader.runReader("puzzle3_clean.txt");
        }else {
            // Read the file that contains the corresponding puzzle.
            cPuzzle = reader.runReader(selectedPuzzle);
        }
        cFields = reader.setUpFields();


        // Method for printing the puzzle in the GUI Console.
        guiConsolPuzzlePrint(cPuzzle, reader.pSize);

        // Setting up the columns and rows in the GridPane
        ColumnConstraints column = new ColumnConstraints();
        RowConstraints row = new RowConstraints();
        for(int i = 0; i < cPuzzle.length; i++) {
            sudokuGrid.addColumn(i);
            column.setHgrow(Priority.SOMETIMES);
            column.setMinWidth(10.0);
            sudokuGrid.addRow(i);
            row.setMinHeight(10);
            row.setVgrow(Priority.SOMETIMES);
        }

        // Using cPuzzle to get all the loaded sudoku numbers onto individual buttons.
        sudokuGridButtons();


    }
    // Method for when the solve button is  clicked.

    /*
    When the solver button is checked, we first check that the puzzle the user wants us to solve is even a valid one.
    If it is, we run the solver and updates all the buttons on the puzzle.
     */
    public void solveButtonClicked() {

        // Check if the sudoku puzzle is valid before trying to solve it.
        if(puzzleChecker.runChecker(cPuzzle)){
            textAreaConsole.appendText("\nThe current Sudoku is valid.\nSolving will now commence.\n");
            CandidateFinder.runFinder(cPuzzle);
            LogicalSolver.run(cPuzzle, cFields);

            SudokuButton updatedSudokubutton;
            for (int i = 0; i < cPuzzle.length; i++) {
                for (int j = 0; j < cPuzzle.length; j++) {

                        updatedSudokubutton = (SudokuButton) getNodeFromSudokuGrid(sudokuGrid,i,j);

                        if(cPuzzle[i][j].getDigit() == 0){
                            updatedSudokubutton.setText("ERROR");
                        } else {
                            updatedSudokubutton.setText(cPuzzle[i][j].getDigit() + "");
                        }
                }

            }
        textAreaConsole.appendText("The solving has finished!\n");
         // Notify the user that the puzzle is not valid and the solve will not start.
        } else {
            textAreaConsole.appendText("\n\nYou tried to use the solver on a invalid Sudoku.\nThe solver will not run untill you provide a valid starting Sudoku.");
        }

    }

    // Simple method for when the user wants to exit the application.
    public void exitButtonClicked() {
        System.exit(0);
    }

    /*
        Depending on the user input on the sudoku button different cases are handled.
        We have on purpose restricted the use of many buttons to limit potential bugs when entering gibberish into a button for example.
     */
    private void chooseASudokuNumber(KeyEvent keyEvent) {

        // Backspace will delete the text on the button.
        if (keyEvent.getCode() == (KeyCode.BACK_SPACE)) {
            typedString = "";
            ((SudokuButton) keyEvent.getTarget()).setButtonText(typedString);

         // If the input on the button was the keyevent equal to the enter button. We enter the text on the button
            // This text as already been checked by the next logic.
        } else if (keyEvent.getCode() == KeyCode.ENTER) {
                ((SudokuButton) keyEvent.getTarget()).setButtonText(typedString);
                numberChecker((SudokuButton) keyEvent.getTarget());
                typedString = "";

        // ensure that it's only the numbers buttons you can press to add something to the string thats going on the button.
        } else try { if(keyEvent.getText().matches("[0-9]*")) {
                          typedString += keyEvent.getText();


                    if(Integer.parseInt(typedString) > cPuzzle.length) {
                        typedString = "";

                       } else {
                        // If you want to know what is being pressed and added to the
                      //  System.out.println(typedString);
                    }
                 }
        }catch (NumberFormatException e) {
            textAreaConsole.appendText("");
        }

    }


    // Simple method to check what number was added to the button.
    private void numberChecker(SudokuButton button) {

        if (!button.getButtonText().equals("")) {
            // Take the button text, convert it to integer and insert it in the sPuzzle using the coordinates from the button.
            // These coords matches with how the sPuzzle is set up.
            int userDigit = Integer.parseInt(button.getButtonText());
            cPuzzle[button.getButtonXPos()][button.getButtonYPos()].setDigit(userDigit);

            textAreaConsole.appendText("\nYou have placed number: " + userDigit + " on position " + button.getButtonXPos() +", " + button.getButtonYPos());


        } else {

            // Ensure that when we use the backspace to delete a number, that it also gets updated in the cPuzzle to a NULL , in our case a 0 is the NULL
            cPuzzle[button.getButtonXPos()][button.getButtonYPos()].setDigit(0);
        }


    }


    // Creating sudokuGridButtons.
    private void sudokuGridButtons() {

        // Logic to ensure that we know when we load a new sudoku puzzle in if its the same size as the lastest one.
        if(lastGridSize == cPuzzle.length) {
            sameSizeGrid = true;
        } else{
            sameSizeGrid = false;
        }

        // If it's a totally new size we must clear the children on the grid and add new buttons.
        if(!generatedBefore || !sameSizeGrid) {
            sudokuGrid.getChildren().clear();
            // Insert a sudokuButton for each "place" in the puzzle.
            int digit;
            for (int i = 0; i < cPuzzle.length; i++) {
                for (int j = 0; j < cPuzzle.length; j++) {

                    digit = cPuzzle[i][j].getDigit();

                    // Create button, set color and add a key listener.
                    sudokuButton = new SudokuButton("" + digit, i, j);
                    coloringButton(sudokuButton, cPuzzle, i, j);
                    sudokuButton.setOnKeyPressed(this::chooseASudokuNumber);

                    // add button to grid.
                    sudokuGrid.add(sudokuButton, i, j);
                    sudokuGrid.setMargin(sudokuButton, new Insets(2,2,2,2));

                    // Now this is the latest generated and its generated before.
                    generatedBefore = true;



                }
            }
            // If we already have the buttons generated in the right size grid, we just need to alter the text they have written on them instead of creating a whole bunch of new ones.
        } else {
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

        lastGridSize = cPuzzle.length;
    }

    // TextArea (Prints to the GUI console)
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
                    textAreaConsole.appendText(tPuzzle[j][i].getDigit() + " | "); // Line breaks for showing the n*n fields
                } else {
                    textAreaConsole.appendText(tPuzzle[j][i].getDigit() + " ");
                }


            }

            textAreaConsole.appendText("\n");

        }

        textAreaConsole.appendText("\nHow to play:\nClick on a button. Type in the number you want on it.\nPress ENTER when you are done typing." +
                "\nIf nothing appears, the number you have pressed is invalid." +
                "\nThen press BACKSPACE and enter a new number or to delete an existing one.\n");



    }

    // Activates a popup dialog for choosing a puzzle.
    // This method is self-explanatory.
    private void puzzleChooserDialog() {

        Optional<String> result = sudokuChooserDialog.showAndWait();
        String answerSelected;

        if (result.isPresent()) {
            answerSelected = result.get();

        } else {
            textAreaConsole.appendText("\nYou didnt make a choice for a specific puzzle so we loaded the same as number 2.");
            answerSelected = "clean";

        }

        // Hard coded logic to test out the big puzzles. This is only for the purpose of testing large sudoku puzzles.
        if(answerSelected.equals("puzzle4_1.txt") || answerSelected.equals("puzzle5_1.txt")) {
            selectedPuzzle = answerSelected;
        }else {
            selectedPuzzle = "puzzle3_" + answerSelected + ".txt";

        }

        // Call this method to start the generating process.
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

    // Method for differentiating between the different "Fields" in the sudoku grid so it's more appealing to the user.
    private void coloringButton(SudokuButton sudokuButton, Tile[][] cPuzzle, int i, int j) {

        // Heavy logic to determine which field we are in. We simply use that the integer will floor the mod 2 and we therefor know if its an even number or not.
        if(
                (((int)(cPuzzle[i][j].getX()/Math.sqrt(cPuzzle.length))%2 == 0) && ((int)(cPuzzle[i][j].getY()/Math.sqrt(cPuzzle.length))%2 == 0))
                ||
                        (((int)(cPuzzle[i][j].getX()/Math.sqrt(cPuzzle.length))%2 != 0) && ((int)(cPuzzle[i][j].getY()/Math.sqrt(cPuzzle.length))%2 != 0))

                )

        {
              sudokuButton.getStyleClass().add("buttonBlue");
        } else {
            sudokuButton.getStyleClass().add("buttonBisque");

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
