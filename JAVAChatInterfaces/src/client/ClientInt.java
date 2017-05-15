/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import common.UserContact;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Yousef
 */
public interface ClientInt extends Remote{
    public MessagingServiceInt getMessagingService() throws RemoteException;
    public UserContact getUserContact() throws RemoteException;
    public void exitApp() throws RemoteException;
}
