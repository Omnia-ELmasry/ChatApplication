package clientchat.fileserver;

import clientchat.fileserver.FileServerInt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import clientchat.fileclient.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Masoud
 */
public class FileServer extends UnicastRemoteObject implements FileServerInt {

    private String file = "";

    public FileServer() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    public void setFile(String f) {
        file = f;
    }

    @Override
    public boolean login(FileClientInt c) throws RemoteException {
        /*
		 *
		 * Sending The File...
		 * 
         */
        try {
            File f1 = new File(file);
            FileInputStream in = new FileInputStream(f1);
            byte[] mydata = new byte[1024 * 1024];
            int mylen = in.read(mydata);
            while (mylen > 0) {
                c.sendData(f1.getName(), mydata, mylen);
                mylen = in.read(mydata);
            }
        } catch (IOException ex) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

}
