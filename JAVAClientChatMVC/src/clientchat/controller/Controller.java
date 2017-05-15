/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.controller;

//import clientchat.View;
import client.MessagingServiceInt;
import clientchat.model.Model;
import clientchat.services.localservices.ChatService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import server.*;
import clientchat.services.localservices.RegistrationService;
import clientchat.services.localservices.SignUpServices;
import clientchat.services.rmiservices.MessagingService;
import clientchat.view.chatwindow.ChatWindowController;
import clientchat.view.homewindow.HomeWindowViewController;
import clientchat.view.login.LoginViewController;
import common.UserContact;
import common.dto.User;
import common.enums.Status;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Yousef
 */

public class Controller {
   private Model model;
   private ServerInt serverRemote;
   private Parent root;
   private FXMLLoader loader;
   private Registry registry;
   private UserContact myUserContact;
   private Stage primaryStage;
   public HomeWindowViewController homeWindowController;
   public HashMap<String,ChatWindowController> openedChatWindows = new HashMap<>();
   //setters and getters
    public void setModel(Model model) {
        this.model = model;
        
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public UserContact getMyUserContact() {
        return myUserContact;
    }

    public void setMyUserContact(UserContact myUserContact) {
        this.myUserContact = myUserContact;
    }

    

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setServerRemote(ServerInt serverRef) {
        this.serverRemote = serverRef;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public Model getModel() {
        return model;
    }

    public ServerInt getServerRemote() {
        return serverRemote;
    }

    public Parent getRoot() {
        return root;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public Registry getRegistry() {
        return registry;
    }

    

    public Stage getPrimaryStage() {
        return primaryStage;
    }

   //-----------------------------------------------------
   public Controller() throws NotBoundException{

   }
  
   
   public void start(Stage primaryStage) throws IOException{
       this.primaryStage = primaryStage;
       loader = new FXMLLoader(getClass().getResource("/clientchat/view/login/loginView.fxml"));

        root = loader.load();
        LoginViewController loginController = loader.getController();
        
        
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            if(myUserContact == null)
               System.exit(1);
            primaryStage.setIconified(true);
            event.consume();
        });
        
//        secondaryStage.setOnCloseRequest(event -> {
//    secondaryStage.setIconified(true);
//    event.consume();
//});
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Chat Application");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
   }
   
   public RegistrationService getRegistrationService(){
       return new RegistrationService();
   }
   
   public ChatService getChatService(){
       return new ChatService();
   }
   public SignUpServices getSignupServices(){
       return new SignUpServices();
       
   }
   
   public void removeClient(){
       try {
           
           getChatService().notifyMyStatusToFriends(Status.OFFLINE);
           serverRemote.getRegistrationService().unRegister(myUserContact.getID(), model);
           Platform.runLater(new Runnable() {
               @Override
               public void run() {
                   Platform.exit();
                   System.exit(0);
               }
           });
           
       } catch (RemoteException ex) {
           Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
       }
   }

   //============================================================

    
   
}
