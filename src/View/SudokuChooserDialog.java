package View;

import javafx.scene.control.ChoiceDialog;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Paf on 19-03-2016.
 */
public class SudokuChooserDialog extends ChoiceDialog {

    // Testing String Array, the names and the choosing process could use some more smart work. A bit more dynamical.
    // Added the puzzle4_1.txt and 5 for testing purpose.
    private final String[] CHOICES = {"clean", "1", "2", "3", "4", "5", "6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","puzzle4_1.txt","puzzle5_1.txt"};


   public SudokuChooserDialog() {
       setup();

   }

    private void setup() {
        this.setTitle("Choose a Puzzle");
        this.setHeaderText("Please select which Sudoku Puzzle you want");
        this.getItems().addAll(CHOICES);


    }





}
