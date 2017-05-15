/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.view.useradd;

import serverchat.view.adminjob.AdminJobController;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import serverchat.ServerApp;
import serverchat.controller.Controller;
import serverchat.model.Model;

/**
 * FXML Controller class
 *
 * @author Masoud
 */
public class UserAddController implements Initializable {

    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private TextField gender;
    @FXML
    private TextField email;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField country;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    
    private Controller controller;
    private Model model;
    private Parent root;
    private Stage stage;
    
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
    public void addButtonHandler(ActionEvent event){
        try {
            model.getAdminServices().addUser(model.getAdminServices().setUserId(), userName.getText(), fName.getText(), lName.getText(), email.getText(), password.getText(), Integer.parseInt(gender.getText()), country.getText());
            addButton.getScene().getWindow().hide();
        } catch (RemoteException ex) {
            Logger.getLogger(UserAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void cancelButtonHandler(ActionEvent event){
        
        addButton.getScene().getWindow().hide();
    }
//    public void start(Stage stage, String fxml) {
//        this.stage = stage;
//        try {
//            root = FXMLLoader.load(getClass().getResource(fxml));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        stage = new Stage();
//        Scene scene = new Scene(root);
//        stage.setTitle("Add User");
//        stage.setScene(scene);
//        addButton.getScene().getWindow().hide();
//        stage.show();
//    }

    public TextField getfName() {
        return fName;
    }

    public void setfName(TextField fName) {
        this.fName = fName;
    }

    public TextField getlName() {
        return lName;
    }

    public void setlName(TextField lName) {
        this.lName = lName;
    }

    public TextField getGender() {
        return gender;
    }

    public void setGender(TextField gender) {
        this.gender = gender;
    }

    public TextField getCountry() {
        return country;
    }

    public void setCountry(TextField country) {
        this.country = country;
    }

    public TextField getEmail() {
        return email;
    }

    public void setEmail(TextField email) {
        this.email = email;
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

    public PasswordField getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(PasswordField confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public Button getCancleButton() {
        return cancelButton;
    }

    public void setCancleButton(Button cancleButton) {
        this.cancelButton = cancleButton;
    }    
    
}
