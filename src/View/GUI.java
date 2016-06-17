package View;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



/**
 * Created by Paf on 05-03-2016.
 */

public class GUI extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Sudoku");
        // Set to maximize on screen
        primaryStage.setMaximized(true);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("View/sudokuButton.css");
        primaryStage.setScene(scene);
        primaryStage.show();


    }




}
