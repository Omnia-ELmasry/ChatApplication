/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.model;

import clientchat.controller.Controller;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import server.*;
import client.ClientInt;
import clientchat.services.rmiservices.MessagingService;
import client.MessagingServiceInt;
import common.UserContact;
import javafx.application.Platform;
/**
 *
 * @author Yousef
 */
public class Model extends UnicastRemoteObject implements ClientInt {
    Controller controller;
    private String userId;
    public Model(Controller controller) throws RemoteException{
        
        this.controller = controller;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public MessagingServiceInt getMessagingService() throws RemoteException{
        return new MessagingService(controller);
    }
    
    public UserContact getUserContact(){
        return controller.getMyUserContact();
    }
    
    public void exitApp(){
        Platform.runLater(new Runnable() {
               @Override
               public void run() {
                   Platform.exit();
                   System.exit(0);
               }
           });
    }
    
}
