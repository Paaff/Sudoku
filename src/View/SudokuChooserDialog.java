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

    private final String[] CHOICES = {"clean", "1", "2", "3", "4", "5", "6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};


   public SudokuChooserDialog() {

       setup();

   }

    private void setup() {
        this.setTitle("Choose a Puzzle");
        this.setHeaderText("Please select which Sudoku Puzzle you want (3x3)");
        this.getItems().addAll(CHOICES);


    }





}
