/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.services.localservices;

import client.MessagingServiceInt;
import clientchat.MyApp;
import clientchat.controller.Controller;
import clientchat.messageParser.MessageParser;
import clientchat.messageParser.MsgRoot;
import clientchat.view.chatwindow.ChatWindowController;
import com.jfoenix.controls.JFXToggleButton;
import common.Message;
import common.UserContact;
import common.enums.Status;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Yousef
 */
public class ChatService {
    private Controller controller;
    public ChatService(){
        controller = MyApp.getController();
    }
    
    
    public void saveChatSession(ChatWindowController chatWindowController) {
        ArrayList<Message> msgs = chatWindowController.getMsgsFromScreen();
        int friendId = chatWindowController.getFriendId();
        if(msgs.size() > 0)
            MessageParser.parseMsgs(msgs,friendId);
    }
    
    public void openChatWindow(int myId,int friendId,MessagingServiceInt myFriendService) {
        try {
            Stage s2 = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientchat/view/chatwindow/chatWindow.fxml"));


            ChatWindowController chatWindowController = new ChatWindowController(myId, friendId, myFriendService);
            controller.openedChatWindows.put(String.valueOf(friendId), chatWindowController);
            loader.setController(chatWindowController);
            BorderPane pane = loader.load();
            Scene scene = new Scene(pane);
            s2.setScene(scene);
            s2.setResizable(false);
            s2.requestFocus();
            s2.show();
            
            
            
            s2.setOnCloseRequest((WindowEvent event) -> {
                controller.openedChatWindows.remove(String.valueOf(friendId));
                if(chatWindowController.getSaveChat().isSelected()){
                    saveChatSession(chatWindowController);
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void receiveMsg(Message msg) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //check if there is an opened chat window with the sender id
                if (controller.openedChatWindows.containsKey(msg.getSenderId())) {
                    controller.openedChatWindows.get(msg.getSenderId()).receiveMsg(msg);
                } else {
                    int senderIdFriend = Integer.parseInt(msg.getSenderId());
                    MessagingServiceInt senderFriendService = controller.getMyUserContact().getFriends().get(Integer.parseInt(msg.getSenderId())).getMessagingService();
                    int receiverIdMe = Integer.parseInt(msg.getReceiverId());

                    openChatWindow(receiverIdMe, senderIdFriend,senderFriendService);
                    controller.openedChatWindows.get(msg.getSenderId()).receiveMsg(msg);
                }
            }
        });

    }
    
    
    public void pushNotification(){
        HashMap<Integer,UserContact> friends = controller.getMyUserContact().getFriends();
        for(Map.Entry<Integer,UserContact> entry:friends.entrySet()){
            
            MessagingServiceInt messagingService = entry.getValue().getMessagingService();
            if(messagingService != null && entry.getValue().isIsOnline()){
                 Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            messagingService.receiveNotification(controller.getMyUserContact().getID());
                        } catch (RemoteException ex) {
                            Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
               
            }
        }
    }
    public void notifyMyStatusToFriends(Status status){
        HashMap<Integer,UserContact> friends = controller.getMyUserContact().getFriends();
        for(Map.Entry<Integer,UserContact> entry:friends.entrySet()){
            
            MessagingServiceInt messagingService = entry.getValue().getMessagingService();
            if(messagingService != null && entry.getValue().isIsOnline()){
            try {
                System.out.println("user id : "+entry.getKey());
                messagingService.updateFriendStatus(controller.getMyUserContact().getID(), status);
                controller.getMyUserContact().setStatus(status.getId());
            } catch (RemoteException ex) {
                Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
        
    }
    
    public void provideServiceObject() {
        HashMap<Integer, UserContact> friends = controller.getMyUserContact().getFriends();
        for (Map.Entry<Integer, UserContact> entry : friends.entrySet()) {
            MessagingServiceInt messagingService = entry.getValue().getMessagingService();
            if (messagingService != null && entry.getValue().isIsOnline()) {
                try {
                    System.out.println(controller.getModel().getMessagingService());
                    messagingService.updateFriendServiceObject(controller.getMyUserContact().getID(), controller.getModel().getMessagingService());
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    
    public void updateStatus(Status status){
        try {
            
            controller.getServerRemote().getRegistrationService().updateMyStatus(controller.getMyUserContact().getID(), status);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void sendMsg(Message msg) {
        if(msg.getChatType().equals(Message.ChatType.PEER_TO_PEER)){
            int receiverId = Integer.parseInt(msg.getReceiverId());
            UserContact friendUserContact = controller.getMyUserContact().getFriends().get(receiverId);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            friendUserContact.getMessagingService().receive(msg);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            
            
        }
    }
}
