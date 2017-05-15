/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.services.localservices;

import clientchat.MyApp;
import clientchat.controller.Controller;
import clientchat.view.login.LoginViewController;
import common.Response;
import common.dto.User;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import server.ServerInt;

/**
 *
 * @author omnia
 */


public class SignUpServices {
    
     private Controller controller;
    private ServerInt serverRemote;

    public SignUpServices(){
        controller = MyApp.getController();
    }
    
    public boolean registryLookup(String ipAddress){
      try {
          
           Registry reg = LocateRegistry.getRegistry(ipAddress);
           controller.setRegistry(reg);
           serverRemote = (ServerInt)reg.lookup("ChatService");
           controller.setServerRemote(serverRemote);
           return true;
       } catch (RemoteException ex) {
           System.out.println("can't connect to server");
           return false;
       }catch(Exception ex){
            ex.printStackTrace();
       }
      return false;
   }
    public void signUpCall( User user ){
      Platform.runLater(() -> {
            try {
                 Response response = serverRemote.getRegistrationService().signUp(user);
                 if (response != null) {
                    if (response.isResponseOk()) {

//                        controller.setUserContact((User) response.getResponseObject());
//                        registerModel();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientchat/view/signup/signUp.fxml"));
                        controller.setLoader(loader);
                        Parent root = loader.load();
                        controller.setRoot(root);
                                           
                        controller.getPrimaryStage().setScene(new Scene(root));
                        controller.getPrimaryStage().show();
                    } else {
                        LoginViewController loginController = controller.getLoader().getController();
                        loginController.showResponse(response);
                    }
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    

        
    
    
   
}
