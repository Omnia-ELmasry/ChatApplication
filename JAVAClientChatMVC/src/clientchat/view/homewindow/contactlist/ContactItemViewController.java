/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.view.homewindow.contactlist;

import clientchat.MyApp;
import clientchat.controller.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Yousef
 */
public class ContactItemViewController implements Initializable {

    @FXML
    private Label userName;
    @FXML
    private Label status;
    private Controller controller;

    public Label getUserName() {
        return userName;
    }

    public Label getStatus() {
        return status;
    }

    /**
     * Initializes the controller class.
     */
    
    public ContactItemViewController(){
        controller = MyApp.getController();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
}
