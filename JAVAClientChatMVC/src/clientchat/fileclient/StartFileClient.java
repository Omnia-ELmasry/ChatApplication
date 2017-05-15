package clientchat.fileclient;
import clientchat.fileserver.FileServerInt;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
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
public class StartFileClient {

    public static void main(String[] args) {
        try {
            FileClient c = new FileClient("imed");
            Registry reg = LocateRegistry.getRegistry();
            FileServerInt server = (FileServerInt) reg.lookup("send file");
            server.login(c);
            System.out.println("Listening.....");
            Scanner s = new Scanner(System.in);
            while (true) {
                String line = s.nextLine();
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(StartFileClient.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
