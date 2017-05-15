/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import client.ClientInt;

import common.Response;
import common.dto.User;

/**
 *
 * @author Yousef
 */
public interface ServerInt extends Remote{
    
    
    public RegistrationServiceInt getRegistrationService() throws RemoteException;
    
    public LoginServiceInt getLoginService() throws RemoteException;
    public AdminServicesInt getAdminServices() throws RemoteException;
    
}
