package Controller;

import View.SudokuButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import reader.PuzzleReader;
import solver.Field;
import solver.PuzzleChecker;
import solver.Tile;
import View.SudokuButton;


/**
 * Created by Paf on 24-02-2016.
 */
public class Controller {

    @FXML TextArea textAreaConsole;
    @FXML GridPane sudokuGrid;
    PuzzleReader reader = new PuzzleReader();
    PuzzleChecker puzzleChecker = new PuzzleChecker();
    private int hintCounter = 0;
    SudokuButton sudokuButton;



    // Methods for button presses
    public void startButtonClicked() {
        System.out.println("Clicked start.");
    }

    public void generateButtonClicked() {
        System.out.println("Clicked Generate Puzzle.");
        textAreaConsole.appendText("Generating Puzzle from this format:\n");

        // Reading the file where the puzzle is contained and loading it and setting up the fields of the puzzle.
        reader.runReader();
        reader.setUpFields();

        // Method for printing the puzzle in the GUI Console.
        guiConsolPuzzlePrint(reader.sPuzzle, reader.pSize);

        // Using reader.sPuzzle to get all the loaded sudoku numbers onto indidivual buttons.
        sudokuGridButtons();


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


    private void chooseASudokuNumber(KeyEvent keyEvent) {
        
        //System.out.print(keyEvent.getTarget().toString());
        if(keyEvent.getCode()!= KeyCode.DIGIT0) {
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

            }

            // Load this specific number into reader.sPuzzle by taking the text from the button and placing it in the stored puzzle.
            // Also check if this new value is valid or not.
            validNumberChecker((SudokuButton) keyEvent.getTarget());


        }
    }

    private void validNumberChecker(SudokuButton button) {

        // Take the button text, convert it to integer and insert it in the sPuzzle using the coordinates from the button.
        // These coords matches with how the sPuzzle is set up.
        int userDigit = Integer.parseInt(button.getButtonText());
        reader.sPuzzle[button.getButtonXPos()][button.getButtonYPos()].setDigit(userDigit);
        System.out.println(reader.sPuzzle[button.getButtonXPos()][button.getButtonYPos()].getDigit() + ": New value set in the reader.sPuzzle");

        if(puzzleChecker.runChecker(reader.sPuzzle)) {
            button.setValidStyle();
            System.out.println("valid");
        } else {
            button.setNotValidStyle();
            System.out.println("not valid");
        }

    }


    private void sudokuGridButtons() {
        int digit;
            for(int i = 0; i < reader.pSize*reader.pSize; i++) {
                for(int j = 0; j < reader.pSize*reader.pSize; j++) {
                    digit = reader.sPuzzle[j][i].getDigit();
                    sudokuButton = new SudokuButton("", ""+digit, j,i);
                    sudokuButton.setOnKeyPressed(this::chooseASudokuNumber);


                    sudokuGrid.add(sudokuButton, j, i);

                }
            }

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

