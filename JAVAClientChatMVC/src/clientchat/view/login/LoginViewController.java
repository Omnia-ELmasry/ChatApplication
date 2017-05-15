/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.view.login;

import clientchat.MyApp;
import clientchat.controller.Controller;
import clientchat.services.localservices.RegistrationService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import common.Response;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author omnia
 */
public class LoginViewController implements Initializable {

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXTextField ipAddress;
    public static String ipAddressGlobal;
    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXButton signupBtn;
    
       @FXML
    private Label msg1;

    @FXML
    private Label msg2 ;

    @FXML
    private Label msg3;

    private RegistrationService registrationService;
    private Controller controller;
    public LoginViewController() {
        this.msg3 = new Label();
        this.msg2 = new Label();
        this.msg1 = new Label();
        controller = MyApp.getController();

        registrationService = controller.getRegistrationService();
    }

    @FXML
    void userLogin(ActionEvent event) {

        String userNamee = userName.getText();
        String pass = password.getText();

        if (registrationService.registryLookup(ipAddress.getText())) {
            registrationService.login(userNamee, pass);
        } else {
//            Alert alert = new Alert(AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setContentText("please provide valid IP address");
//            alert.showAndWait();

            msg3.setText("please provide valid IP address");
        }

    }

    public void showResponse(Response response) {
//        Alert alert = new Alert(AlertType.ERROR);
//        alert.setTitle("Error");
//        alert.setContentText(response.getResponseMsg());
//        alert.showAndWait(); 

   msg1.setText(response.getResponseMsg());
    }

    @FXML
    void userSignup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientchat/view/signup/signUpView.fxml"));
            controller.setLoader(loader);
            Parent root = loader.load();
            controller.setRoot(root);
//            MainWindowController mainWindowController = loader.getController();
            
            controller.getPrimaryStage().setScene(new Scene(root));
            controller.getPrimaryStage().setResizable(false);
            controller.getPrimaryStage().show();
        } catch (IOException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ipAddressGlobal = ipAddress.getText();
    }

}
