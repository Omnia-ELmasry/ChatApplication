/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import common.Message;
import common.enums.Status;
import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Yousef
 */
public interface MessagingServiceInt extends Remote{
    void receive(Message msg)throws RemoteException;
    void updateFriendStatus(int userId,Status status) throws RemoteException;
    void updateFriendServiceObject(int userId,MessagingServiceInt messagingService) throws RemoteException;
    void receiveNotification(int id) throws RemoteException;
    void receiveAnnouncement(String ann) throws RemoteException;
    void receiveFile(int id,File file) throws RemoteException;
    boolean sendData(String filename, byte[] data, int len) throws RemoteException;

    boolean requestTransfer() throws RemoteException;
    void showNotification(String title,String text) throws RemoteException;
}
