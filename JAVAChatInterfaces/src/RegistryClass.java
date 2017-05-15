
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yousef
 */
public class RegistryClass extends Application  {
    public static void main(String[] args){
        launch(args);
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       try {
            Registry registry = LocateRegistry.createRegistry(5005);
            
        } catch (RemoteException ex) {
            Logger.getLogger(RegistryClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
