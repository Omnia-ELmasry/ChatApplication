/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.services.rmiservices;

import clientchat.controller.Controller;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import client.MessagingServiceInt;
import clientchat.fileclient.FileClient;
import clientchat.fileclient.FileClientInt;
import clientchat.fileserver.FileServer;
import clientchat.services.localservices.ChatService;
import clientchat.view.chatwindow.bubblespeech.BubbleSpeechController;
import clientchat.view.homewindow.NotificationController;
import common.Message;
import common.UserContact;
import common.enums.Status;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Yousef
 */
public class MessagingService extends UnicastRemoteObject implements MessagingServiceInt {
    Controller controller;
    FileServer fs = new FileServer();
    ButtonType buttonSave;
    ButtonType buttonDontSave;
    public MessagingService() throws RemoteException{};
    
    public void setFile(String fileName){
        fs.setFile(fileName);
    }
    
    public FileServer getFileServer(){
        return fs;
    }
    
    public MessagingService(Controller controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void receive(Message msg){
        controller.getChatService().receiveMsg(msg);
    }
    
    @Override
    public void updateFriendStatus(int userId,Status status){
        controller.getMyUserContact().getFriends().get(userId).setStatus(status.getId());
        if(status == Status.OFFLINE)
            controller.getMyUserContact().getFriends().get(userId).setIsOnline(false);
        else
            controller.getMyUserContact().getFriends().get(userId).setIsOnline(true);
        controller.homeWindowController.getContactList().refresh();
    }
    
    @Override
    public void updateFriendServiceObject(int friendId,MessagingServiceInt messagingService){
        System.out.println(messagingService);
        System.out.println(controller.getMyUserContact().getFriends().get(friendId));
        controller.getMyUserContact().getFriends().get(friendId).setMessagingService(messagingService);
    }

    @Override
    public void receiveAnnouncement(String ann){
         Notifications notificationBiulder = Notifications.create()
           .hideCloseButton() .position(Pos.CENTER).darkStyle().title("Announcement !").text(ann);
        Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                       notificationBiulder.showInformation();
                    }
                });
    }
    
    @Override
    public void showNotification(String title,String text){
        Notifications notificationBiulder = Notifications.create()
           .hideCloseButton() .position(Pos.BOTTOM_RIGHT).darkStyle().title(title).text(text);
        Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                       notificationBiulder.show();
                    }
                });
    }
    @Override
    public void receiveNotification(int id) {       
        FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/clientchat/view/homewindow/notification.fxml"));
       
        Parent node = null;
                            
        try {
            node = newLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MessagingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        NotificationController notificationController = newLoader.getController();
        notificationController.getFriendName().setText("Your Friend "+controller.getMyUserContact().getFriends().get(id).getFullName()+" is now online");
        
        
        Notifications notificationBiulder = Notifications.create()
           .hideCloseButton() .position(Pos.BOTTOM_RIGHT).darkStyle().graphic(node);
        Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                       notificationBiulder.show();
                    }
                });
        
        
            
    }
    @Override
    public void receiveFile(int id,File file){
        try {
            MessagingServiceInt friendService = controller.getMyUserContact().getFriends().get(id).getMessagingService();
//            File f1 = new File("E:/sour/license.txt");
            FileInputStream in = new FileInputStream(file);
            byte[] mydata = new byte[1024 * 1024];
            int mylen = in.read(mydata);
            while (mylen > 0) {
                friendService.sendData(file.getName(), mydata, mylen);
                mylen = in.read(mydata);
            }
            
            Notifications notificationBiulder = Notifications.create()
           .hideCloseButton() .position(Pos.BOTTOM_RIGHT).darkStyle().title("Alert").text("your file has been sent successfully");
        Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                       notificationBiulder.show();
                    }
                });
        
        friendService.showNotification("Alert", "You have received a new file");
        } catch (IOException ex) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public boolean sendData(String filename, byte[] data, int len) throws RemoteException {
        try {
            File directory = new File("C:/chatReceived files");
            if(!directory.isDirectory())
                directory.mkdir();
            File f = new File(directory,filename); // el path beta3 el file elly ha recivoooo
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
    
   
    @Override
    public boolean requestTransfer() {
        Optional<ButtonType> result = confirmSave();
        if (result.get() == buttonSave) {
            return true;
        } else if(result.get() == buttonDontSave){
            return false; 
        }
        return false;
    }
    
    public Optional<ButtonType> confirmSave() {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");

            ButtonType buttonSave = new ButtonType("Accept");
            ButtonType buttonDontSave = new ButtonType("Decline");

            alert.getButtonTypes().setAll(buttonSave, buttonDontSave);
            alert.setContentText("Your friend is wanting to send you a file ");
            return alert.showAndWait();
        }
    
}
