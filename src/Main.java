/**
 * Created by Paf on 23-02-2016.
 */

import View.GUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main   {

    static GUI gui;
    public static void main(String[] args) throws IOException {
        gui = new GUI();
        gui.launch(GUI.class);


    }

}