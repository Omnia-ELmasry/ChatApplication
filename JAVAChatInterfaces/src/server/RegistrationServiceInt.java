/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.ClientInt;
import common.Response;
import common.dto.User;
import common.enums.Status;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Yousef
 */
public interface RegistrationServiceInt extends Remote {
    public void register(int id, ClientInt clientRef) throws RemoteException;

    public void unRegister(int id, ClientInt clientRef) throws RemoteException;
    
    public Response login(String userName,String pass) throws RemoteException;
    public Response signUp(User user) throws RemoteException;
    public void updateMyStatus(int id, Status status) throws RemoteException;
}
