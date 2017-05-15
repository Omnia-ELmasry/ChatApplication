/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.view.homewindow.contactlist;

import client.MessagingServiceInt;
import clientchat.MyApp;
import clientchat.controller.Controller;
import clientchat.services.rmiservices.MessagingService;
import clientchat.view.chatwindow.ChatWindowController;
import common.UserContact;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Yousef
 */
public class TreeCellFactory extends TreeCell<UserContact> {

    private Controller controller = MyApp.getController();

    @Override
    protected void updateItem(UserContact item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        if (item != null && !empty) {
            FXMLLoader newLoader;
            boolean isOnline = false;
            if (item.isIsOnline()) {
                isOnline = true;
                newLoader = new FXMLLoader(getClass().getResource("/clientchat/view/homewindow/contactlist/contactItemView.fxml"));
            } else {
                newLoader = new FXMLLoader(getClass().getResource("/clientchat/view/homewindow/contactlist/contactItemOfflineView.fxml"));
            }

            Parent contactItemCell = null;
            try {
                contactItemCell = newLoader.load();
                ContactItemViewController contactItemController = newLoader.getController();
                contactItemController.getStatus().setText(item.getStatus().getName());
                contactItemController.getUserName().setText(item.getfName() + " " + item.getlName());
                if (isOnline) {
                    contactItemCell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getClickCount() == 2) {
                                
                                
                                    
                                    int myId = controller.getMyUserContact().getID();
                                    int friendId  = item.getID();
                                    MessagingServiceInt friendService = item.getMessagingService();
                                    controller.getChatService().openChatWindow(myId, friendId, friendService);
                                
                            }

                        }
                    });
                }
            } catch (IOException ex) {
                Logger.getLogger(ChatWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }

            setGraphic(contactItemCell);
        } else if (item == null && !empty) {
            int onlineUsersCount = 0;
            for (Map.Entry<Integer, UserContact> entry : controller.getMyUserContact().getFriends().entrySet()) {
                if (entry.getValue().isIsOnline()) {
                    onlineUsersCount++;
                }
            }
            String stat = "All (" + onlineUsersCount + " / " + controller.getMyUserContact().getFriends().size() + ")";
            setGraphic(new Text(stat));
        } else {
            setGraphic(null);
        }

    }

}
