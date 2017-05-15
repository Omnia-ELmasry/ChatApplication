/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.ClientInt;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Yousef
 */
public interface GroupChatServiceInt extends Remote{
    public void tellOthers(String msg) throws RemoteException;
}
