/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.view.adminjob;

import client.ClientInt;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Map;
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
import javafx.scene.control.ToggleButton;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import serverchat.ServerApp;
import serverchat.controller.Controller;
import serverchat.model.Model;

/**
 * FXML Controller class
 *
 * @author Masoud
 */
public class AdminJobController implements Initializable {

    /**
     * Initializes the controller class.
     */
    boolean flag;
    String serverState="off";

    @FXML
    private ToggleButton toggleButton;
    @FXML
    private Button userAddButton;
    @FXML
    private Button adminAddButton;
    @FXML
    private Button usersInfoButton;
    @FXML
    private Button statisticsButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button announceBtn;
    
    
    private Controller controller;
    private Model model;
    private Parent root;
    private Stage stage;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        controller = ServerApp.getController();
        model = controller.getModel();
    }  
    
    @FXML
    void sendAnnounce(ActionEvent event) {
        start(stage, "/serverchat/view/announcement/announcement.fxml", "Announcement");
    }
    
    @FXML
    private void toggleButtonHandler(ActionEvent event){
        if(flag){
            try {
                model.getAdminServices().stopServer();
            } catch (RemoteException ex) {
                Logger.getLogger(AdminJobController.class.getName()).log(Level.SEVERE, null, ex);
            }
            serverState="off";
            toggleButton.setText(serverState);
            flag = false;
            announceBtn.setDisable(true);
        }else{
            try {
                model.getAdminServices().startServer();
            } catch (RemoteException ex) {
                Logger.getLogger(AdminJobController.class.getName()).log(Level.SEVERE, null, ex);
            }
            serverState="on";
            toggleButton.setText(serverState);
            flag = true;
            announceBtn.setDisable(false);
        }
    }
    
    @FXML
    private void userAddButtonHandler(ActionEvent event){
        
        start(stage, "/serverchat/view/useradd/userAdd.fxml", "Add User");
    }
    
    @FXML
    private void adminAddButtonHandler(ActionEvent event){
        
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/serverchat/view/adminAdd/adminAdd.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(AdminJobController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void usersInfoButtonHandler(ActionEvent event){
          
        start(stage, "/serverchat/view/userinfo/userInfo.fxml", "Users");
    }
    
    @FXML
    private void statisticsButtonHandler(ActionEvent event){
        
        start(stage, "/serverchat/view/statistics/statistics.fxml", "Statistics");
          
      /*  Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/userAdd/userAdd.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(AdminJobController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        toggleButton.getScene().getWindow().hide();
        stage.show();*/
    }
    
    @FXML
    private void logOutButtonHandler(ActionEvent event){
        try {
            if(toggleButton.isSelected())
                model.getAdminServices().stopServer();
            
            
            logOutButton.getScene().getWindow().hide();
            start(stage, "/serverchat/view/login/login.fxml", "Log In");
            
        } catch (RemoteException ex) {
            Logger.getLogger(AdminJobController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void start(Stage stage, String fxml , String windo) {
        this.stage = stage;
        try {
            root = FXMLLoader.load(getClass().getResource(fxml));
            stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle(windo);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent event) -> {
            if(windo.equals("Log In")){
                System.exit(0);
            }
        });
        
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        
    }
    
    
}
