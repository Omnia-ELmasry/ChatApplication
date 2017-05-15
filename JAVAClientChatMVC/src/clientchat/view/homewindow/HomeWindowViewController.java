/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.view.homewindow;

import client.MessagingServiceInt;
import clientchat.MyApp;
import clientchat.controller.Controller;
import clientchat.view.homewindow.contactlist.TreeCellFactory;
import common.UserContact;
import common.enums.Status;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import sun.security.x509.EDIPartyName;

/**
 * FXML Controller class
 *
 * @author Yousef
 */
public class HomeWindowViewController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView userImageView;
    @FXML
    private Label usernameLabel;
    @FXML
    private ComboBox<Status> statusComboBox;
    @FXML
    private TreeView<UserContact> contactList;
    
    private Controller controller;
    private ButtonType buttonOk;
    private ButtonType buttonCancel;
    public Optional<ButtonType> confirmExit() {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");

            buttonOk = new ButtonType("Ok");
            
            buttonCancel = new ButtonType("Cancel");

            alert.getButtonTypes().setAll(buttonOk, buttonCancel);
            alert.setContentText("Are You Sure you want to exit ?");
            return alert.showAndWait();
        }
    public void exitApp() {
        Optional<ButtonType> result = confirmExit();
        if (result.get() == buttonOk) {
            Platform.exit();
            if (controller.getMyUserContact() != null) {
                controller.removeClient();
            }
            System.exit(0);
        }

    }
    public BorderPane getBorderPane() {
        return borderPane;
    }

    public ImageView getUserImageView() {
        return userImageView;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public ComboBox<Status> getStatusComboBox() {
        return statusComboBox;
    }

    public TreeView<UserContact> getContactList() {
        return contactList;
    }
    
    
    public HomeWindowViewController(){
        controller = MyApp.getController();
    }
    
    public void notifyFriends(){
        controller.getChatService().notifyMyStatusToFriends(controller.getMyUserContact().getStatus());
        controller.getChatService().pushNotification();
       
    }
    public void provideServiceToFriends(){
        controller.getChatService().provideServiceObject();
    }
    
    @FXML
    public void handleComboBox(){
         Status selectedStatus = statusComboBox.getSelectionModel().getSelectedItem();
         controller.getChatService().notifyMyStatusToFriends(selectedStatus);
         controller.getChatService().updateStatus(selectedStatus);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         statusComboBox.getItems().addAll(Status.ONLINE,Status.AWAY,Status.BUSY);
         
         statusComboBox.setCellFactory(new Callback<ListView<Status>, ListCell<Status>>() {
             @Override
             public ListCell<Status> call(ListView<Status> param) {
                  return new ListCell<Status>(){
                      @Override
                      protected void updateItem(Status item, boolean empty) {
                         super.updateItem(item, empty);
                         if (item == null || empty) {
                             setGraphic(null);
                         } else {
                             ImageView imgView  = new ImageView();
                             Image img = null;
                             if(item.equals(Status.ONLINE)){
                                    img = new Image(getClass().getResourceAsStream("/clientchat/resources/online-icon.png"));
                             }else if(item.equals(Status.AWAY)){
                                    img = new Image(getClass().getResourceAsStream("/clientchat/resources/away-icon.png"));
                             }else if(item.equals(Status.BUSY)){
                                   img = new Image(getClass().getResourceAsStream("/clientchat/resources/busy-icon.png"));
                             }
                             imgView.setImage(img);
                             imgView.setFitHeight(20);
                             imgView.setFitWidth(20);
                             FlowPane fp = new FlowPane();
                             fp.getChildren().add(imgView);
                             fp.getChildren().add(new Text(item.getName()));
                             
                             fp.setMaxWidth(80);
                             setGraphic(fp);
                         }
                     }

                 };
             }
         });
         
         statusComboBox.setButtonCell(statusComboBox.getCellFactory().call(null));
         statusComboBox.setValue(controller.getMyUserContact().getStatus());
        
        
        
        
        usernameLabel.setText(controller.getMyUserContact().getfName()+" "+controller.getMyUserContact().getlName());
        
        TreeItem<UserContact> root = new TreeItem<>();
        root.setExpanded(true);
        for(Map.Entry<Integer,UserContact> entry:controller.getMyUserContact().getFriends().entrySet()){
            root.getChildren().add(new TreeItem(entry.getValue()));
        }
        
        contactList.setRoot(root);
        
        contactList.setCellFactory(new Callback<TreeView<UserContact>, TreeCell<UserContact>>() {
            @Override
            public TreeCell<UserContact> call(TreeView<UserContact> param) {
                return new TreeCellFactory();
            }
        });
        
        contactList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        contactList.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.ENTER){
                ObservableList<TreeItem<UserContact>> selectedItems =  contactList.getSelectionModel().getSelectedItems();
                
//
                        for(TreeItem<UserContact> s : selectedItems){
                                int myId = controller.getMyUserContact().getID();
                                int friendId  = s.getValue().getID();
                                MessagingServiceInt friendService = s.getValue().getMessagingService();
                                controller.getChatService().openChatWindow(myId, friendId, friendService);
                        }
            }
                
            
        });
        
        contactList.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                int index = contactList.getSelectionModel().getSelectedIndex();
                TreeItem<UserContact> item = contactList.getSelectionModel().getSelectedItem();
                if(!item.getValue().isIsOnline())
                    contactList.getSelectionModel().clearSelection(index);
             }
         });
//        contactList.setOnKeyPressed(new EventHandler<Event>() {
//
//                    @Override
//                    public void handle(Event event) {
//                        ObservableList<String> selectedItems =  listView.getSelectionModel().getSelectedItems();
//
//                        for(String s : selectedItems){
//                            System.out.println("selected item " + s);
//                        }
//
//                    }
//
//                });
        notifyFriends();
        provideServiceToFriends();
    } 
    
}
