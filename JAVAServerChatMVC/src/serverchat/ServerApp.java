/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import serverchat.controller.Controller;
import serverchat.view.statistics.StatisticsController;
import static javafx.application.Application.launch;

/**
 *
 * @author Yousef
 */
public class ServerApp extends Application {
   
    private static Controller controller;

    public static Controller getController() {
        return controller;
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        controller = new Controller();
        controller.start(primaryStage);
    }

    

    public static void main(String[] args) {
        launch(args);
    }
    
}