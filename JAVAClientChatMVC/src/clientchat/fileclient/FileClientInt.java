package clientchat.fileclient;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Masoud
 */
public interface FileClientInt extends Remote {

    public boolean sendData(String filename, byte[] data, int len) throws RemoteException;

    public String getName() throws RemoteException;
}
