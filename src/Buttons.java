
import javafx.scene.control.Button;

/**
 * Created by Peter on 17-02-2016.
 */
public class Buttons extends Button {

    // Finals for the dimensions of buttons
    private final double BIGBUTTON_HEIGHT = 100;
    private final double BIGBUTTON_WIDTH = 300;
    private final double MEDIUMBUTTON_HEIGHT = 80;
    private final double MEDIUMBUTTON_WIDTH = 200;

    private String title;
    private boolean big;


    public Buttons(String Title, boolean Big ) {
        this.title = Title;
        this.big = Big;

        setup(big);


    }

    private void setup(Boolean bigButton) {
        if (bigButton) {
            this.setPrefSize(BIGBUTTON_WIDTH, BIGBUTTON_HEIGHT);
            this.setText(title);
        } else {
            this.setPrefSize(MEDIUMBUTTON_WIDTH, MEDIUMBUTTON_HEIGHT);
            this.setText(title);
        }
    }



}

