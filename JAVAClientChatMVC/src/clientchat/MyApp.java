/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat;

import clientchat.controller.Controller;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 *
 * @author Yousef
 */
public class MyApp extends Application {
   
    private static Controller controller;

    public static Controller getController() {
        return controller;
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            controller = new Controller();
            controller.start(primaryStage);
        } catch (NotBoundException ex) {
            Logger.getLogger(MyApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    public static void main(String[] args) {
        launch(args);
    }
    
}
