/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import server.ServerInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.AdminServicesInt;
import server.LoginServiceInt;
import serverchat.ServerApp;
import serverchat.controller.Controller;
import serverchat.services.localservices.AdminServices;
import serverchat.services.localservices.LoginService;
import serverchat.services.rmiservices.RegistrationService;

/**
 *
 * @author Yousef
 */
public class Model extends UnicastRemoteObject implements ServerInt{
    Controller controller;
    
    public Model() throws RemoteException{
        controller = ServerApp.getController();
    }
   
    

    public RegistrationService getRegistrationService(){
        try {
            return new RegistrationService();
        } catch (RemoteException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
   

     @Override
    public LoginServiceInt getLoginService() throws RemoteException {
        return new LoginService();
    }

    @Override
    public AdminServices getAdminServices() throws RemoteException {
        return new AdminServices();
    }
   

    
    
}
