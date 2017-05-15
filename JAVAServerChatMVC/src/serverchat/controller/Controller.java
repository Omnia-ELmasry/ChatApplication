/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.controller;

import client.ClientInt;
import common.dto.User;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.ServerInt;
import serverchat.dao.ChatUserDAO;
import serverchat.model.Model;
import serverchat.services.localservices.AdminServices;

/**
 *
 * @author Yousef
 */
public class Controller {
    private Registry registry;
    private Model model;
    private HashMap<Integer,ClientInt> loggedClients = new HashMap<>();
    private HashMap<Integer,User> allUsers = new HashMap<>();
    private Parent root;
    private FXMLLoader loader;
    private Stage primaryStage;
   //setters and getters

    public HashMap<Integer, User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(HashMap<Integer, User> allUsers) {
        this.allUsers = allUsers;
    }
    
    
    public HashMap<Integer, ClientInt> getLoggedClients() {
        return loggedClients;
    }

    public void setLoggedClients(HashMap<Integer, ClientInt> loggedClients) {
        this.loggedClients = loggedClients;
    }
    
    
    public void setModel(Model model) {
        this.model = model;
        
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

   

    public Model getModel() {
        return model;
    }

   
    public Parent getRoot() {
        return root;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public Registry getRegistry() {
        return registry;
    }
    
   //-----------------------------------------------------
//   public Controller(){
//       ChatUserDAO chatUserDao = new ChatUserDAO();
//       allUsers = chatUserDao.getAllUsers();
//       chatUserDao.close();
//       
//   }
//    public void start(Stage primaryStage) throws IOException {
//     
//
//        try {
//            Model obj = new Model();
////            Registry reg = LocateRegistry.createRegistry(5005);
//            Registry reg = LocateRegistry.getRegistry();
//            reg.rebind("ChatService", obj);
//        } catch (RemoteException ex) {
//            ex.printStackTrace();
//        }
//    }
    
    
    public Controller() {
        try {
            model = new Model();
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        ChatUserDAO chatUserDao = new ChatUserDAO();
        allUsers = chatUserDao.getAllUsers();
        chatUserDao.close();

    }

    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        loader = new FXMLLoader(getClass().getResource("/serverchat/view/login/login.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        this.primaryStage.setTitle("Log In");
        this.primaryStage.setScene(scene);
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
        registry = LocateRegistry.getRegistry();
        
        primaryStage.setOnCloseRequest((WindowEvent event) -> {

            System.exit(0);

        });
    }
    
    public AdminServices getAdminServices(){
        return new AdminServices();
    }
}
