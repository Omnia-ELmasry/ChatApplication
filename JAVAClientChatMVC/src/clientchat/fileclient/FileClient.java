package clientchat.fileclient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
public class FileClient extends UnicastRemoteObject implements FileClientInt {

    private static final long serialVersionUID = 1L;
    public String name;

    public FileClient(String n) throws RemoteException {
        super();
        name = n;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public boolean sendData(String filename, byte[] data, int len) throws RemoteException {
        try {
            File f = new File("E:/mnmnmn.ico"); // el path beta3 el file elly ha recivoooo
            f.createNewFile();
            try (FileOutputStream out = new FileOutputStream(f, true)) {
                out.write(data, 0, len);
                out.flush();
            }
            System.out.println("Done writing data...");
        } catch (IOException ex) { 
            Logger.getLogger(FileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
