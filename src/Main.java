
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {

    Buttons testButton, testButton2, testButton3, testButton4, testButton5, testButton6;

    public static void main(String[] args) {

        // Call the launch method in Application.
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Set the stage.
        primaryStage.setTitle("Sudoku");


        // Creating menu buttons
        testButton = new Buttons("Test", true);
        testButton.setOnAction(e -> System.out.println("hej"));

        testButton2 = new Buttons("hey", false);
        testButton2.setOnAction(e -> System.out.println("hhjad"));

        testButton3 = new Buttons("hey", false);
        testButton3.setOnAction(e -> System.out.println("hhjad"));

        testButton4 = new Buttons("hey", false);
        testButton4.setOnAction(e -> System.out.println("hhjad"));

        testButton5 = new Buttons("hey", false);
        testButton5.setOnAction(e -> System.out.println("hhjad"));

        testButton6 = new Buttons("hey", false);
        testButton6.setOnAction(e -> System.out.println("hhjad"));



        // Set the vertical layout
        VBox menuLayout = new VBox();
        menuLayout.setSpacing(30);
        menuLayout.getChildren().addAll(testButton, testButton2, testButton3, testButton4, testButton5, testButton6);


        // A scrollbar where the vertical layout resides.
        ScrollPane scrollPane = new ScrollPane(menuLayout);


        // Setting the main layout using embedded layout.
        BorderPane layout = new BorderPane();
        layout.setLeft(scrollPane);
        // when I have something for the center  layout.setCenter(here);

        // Set the scene.
        Scene scene = new Scene(layout, 1500, 950);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}

