/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.view.login;

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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.rmi.RemoteException;
import javafx.scene.control.PasswordField;
import javafx.stage.WindowEvent;
import serverchat.ServerApp;
import serverchat.controller.Controller;
import serverchat.model.Model;

/**
 * FXML Controller class
 *
 * @author Masoud
 */
public class LoginController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;

    private Controller controller;
    private Model model;
    private Stage stage;
    private Parent root = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        controller = ServerApp.getController();
        model = controller.getModel();
       

        
    }

    @FXML
    public void loginButtonHandler(ActionEvent event) {
        
        try {
            if(model.getLoginService().login(userName.getText(), password.getText())){
                start(stage, "/serverchat/view/adminjob/adminJob.fxml");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start(Stage stage, String fxml) {
      
        try {
            root = FXMLLoader.load(getClass().getResource(fxml));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.stage = new Stage();
        Scene scene = new Scene(root);
        this.stage.setTitle("Server");
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        loginButton.getScene().getWindow().hide();
        this.stage.show();
        this.stage.setOnCloseRequest((WindowEvent event) -> {
            this.stage.setIconified(true);
            event.consume();
            

        });
    }

    public TextField getUserName() {
        return userName;
    }

    public void setUserName(TextField userName) {
        this.userName = userName;
    }

    public PasswordField getPassword() {
        return password;
    }

    public void setPassword(PasswordField password) {
        this.password = password;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(Button loginButton) {
        this.loginButton = loginButton;
    }

}
