package clientchat.fileserver;


import clientchat.fileclient.FileClientInt;
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
public interface FileServerInt extends Remote {

    public boolean login(FileClientInt c) throws RemoteException;
}
