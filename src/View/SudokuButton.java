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
    private int xPos;
    private int yPos;
    private final double MAX_WIDTH = 50.0, MAX_HEIGHT = 50.0;

    public SudokuButton(String buttonID, String buttonText, int xPos, int yPos) {
        this.buttonID = buttonID;
        this.buttonText = buttonText;
        this.xPos = xPos;
        this.yPos = yPos;
        setup();

    }

    private void setup() {
        this.setId("sudokuGridButton:" + buttonID);
        this.setAlignment(Pos.CENTER);
        this.setTextAlignment(TextAlignment.CENTER);
        this.setMaxSize(MAX_WIDTH, MAX_HEIGHT);


        if(buttonText.equals("0")) {
            this.setText("NULL");
            this.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        } else {
            this.setText(buttonText);
          //  this.setStyle("-font: 48 arial");

        }



    }

    public void setButtonText(String buttonText) {
        this.setText(buttonText);
    }

    public String getButtonText() {
        return this.getText();
    }

    public int getButtonXPos() {
        return this.xPos;
    }
    public int getButtonYPos() {
        return this.yPos;
    }
}
