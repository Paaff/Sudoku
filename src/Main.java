import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// This is only a test - Mathias - 1 - 2 - 3 - Check!

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
        testButton = new Buttons("Start Game", true);
        testButton.setOnAction(e -> System.out.println("Timer started."));

        testButton2 = new Buttons("Set Difficulty", false);
        testButton2.setOnAction(e -> System.out.println("Setting Difficulty"));

        testButton3 = new Buttons("Options", false);
        testButton3.setOnAction(e -> System.out.println("Entering Options."));

        testButton4 = new Buttons("Generate Sudoku", false);
        testButton4.setOnAction(e -> System.out.println("Generating Sudoku."));

        testButton5 = new Buttons("Sudoku Solver", false);
        testButton5.setOnAction(e -> System.out.println("Solving current Sudoku puzzle."));

        testButton6 = new Buttons("Statistics", false);
        testButton6.setOnAction(e -> System.out.println("Solver statitics."));



        // Set the vertical layout
        VBox menuLayout = new VBox();
        menuLayout.setSpacing(30);
        menuLayout.setPadding(new Insets(20, 20, 10, 20));
        menuLayout.getChildren().addAll(testButton, testButton2, testButton3, testButton4, testButton5, testButton6);



        // A scrollbar where the vertical layout resides.
        ScrollPane scrollPane = new ScrollPane(menuLayout);


        // Setting the main layout using embedded layout.
        BorderPane layout = new BorderPane();
        layout.setLeft(scrollPane);
        // when I have something for the center  layout.setCenter(here);

        // Set the scene.
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}

