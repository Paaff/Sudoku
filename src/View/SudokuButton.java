package View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;

/**
 * Created by Paf on 05-03-2016.
 */
public class SudokuButton extends Button {

    private String buttonText;
    private String buttonID;
    private final double MAX_WIDTH = 50.0, MAX_HEIGHT = 50.0;

    public SudokuButton(String buttonID, String buttonText) {
        this.buttonID = buttonID;
        this.buttonText = buttonText;
        setup();

    }

    private void setup() {
        this.setId("sudokuGridButton:" + buttonID);
        this.setAlignment(Pos.CENTER);
        this.setTextAlignment(TextAlignment.CENTER);
        this.setMaxSize(MAX_WIDTH, MAX_HEIGHT);


        if(buttonText.equals("0")) {
            this.setText("");
        } else {
            this.setText(buttonText);
          //  this.setStyle("-font: 48 arial");

        }



    }
}
