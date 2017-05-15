package clientchat.fileserver;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Masoud
 */
public class StartFileServer {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {

            Registry reg = LocateRegistry.getRegistry();

            FileServer fs = new FileServer();
            fs.setFile("D:/bleach.ico");// el path beta3 el file ally hab3atoh
            reg.rebind("send file", fs);
            System.out.println("File Server is Ready");

        } catch (RemoteException ex) {
            Logger.getLogger(StartFileServer.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
