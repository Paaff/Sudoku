package View;

import javafx.scene.control.ChoiceDialog;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Paf on 19-03-2016.
 */
public class SudokuChooserDialog extends ChoiceDialog {

    private final String[] CHOICES = {"3x3 Puzzle 1", "3x3 Puzzle 2", "3x3 Puzzle 3"};
    private List<String> dialogData;

   public SudokuChooserDialog() {

       setup();

   }

    private void setup() {
        this.setTitle("Choose a Puzzle");
        this.setHeaderText("Please select which Sudoku Puzzle you want");
        this.getItems().addAll(CHOICES);

    }





}
