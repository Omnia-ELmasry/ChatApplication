/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.view.chatwindow;

import client.MessagingServiceInt;
import clientchat.MyApp;
import clientchat.controller.Controller;
import clientchat.view.chatwindow.bubblespeech.BubbleSpeechController;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import common.ColorUtil;
import common.Message;
import common.UserContact;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Yousef
 */
public class ChatWindowController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView userImageView;
    @FXML
    private Label usernameLabel;
    @FXML
    private ListView<Message> chatPane;
//    @FXML
//    private JFXTextArea txtArea;
//    @FXML
//    private Button btnSend;
    
    @FXML
    private JFXTextField txtArea;


    
    @FXML
    private ColorPicker colorChooser;
    
    @FXML
    private JFXToggleButton saveChat;
    
    @FXML
    private ComboBox<String> fontCombo;
    
    @FXML
    private Label friendStatus;
    private MessagingServiceInt friendService;
    private int myId;
    private int friendId;
    private UserContact friendUserContact;
    
    private Controller controller;
    
    @FXML
    private ImageView sndFile;

    @FXML
    void sendFile(MouseEvent event) {
        try {
            FileChooser chooser = new FileChooser();
                //chooser.setInitialDirectory(new File ("C:/"));
                chooser.setTitle("Please select file");
                File sourceFile = chooser.showOpenDialog(controller.getPrimaryStage());
                if(sourceFile != null)
                    friendService.receiveFile(controller.getMyUserContact().getID(),sourceFile);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public JFXToggleButton getSaveChat(){
        
        return saveChat;
        
    }
    public ChatWindowController(){}
    public ChatWindowController(int myId,int friendId,MessagingServiceInt friendService){
        controller = MyApp.getController();
        this.myId = myId;
        this.friendId = friendId;
        this.friendService = friendService;
        friendUserContact = controller.getMyUserContact().getFriends().get(friendId);
        
        
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
    
    
    
    public MessagingServiceInt getFriendService() {
        return friendService;
    }
    
    public void setFriendService(MessagingServiceInt service){
        this.friendService = service;
    }
    
    
    
    public ImageView getUserImageView() {

        return userImageView;

    }

    public ListView<Message> getChatPane() {
        return chatPane;
    }

//    public JFXTextArea getTxtArea() {
//        return txtArea;
//    }
//
//    public Button getBtnSend() {
//        return btnSend;
//    }

    /**
     * Initializes the controller class.
     */
    public ColorPicker getColorChooser() {
        return colorChooser;
    }

    public void receiveMsg(Message msg){
        if(msg.getSenderId().equals(String.valueOf(friendId))){
            if(saveChat.isSelected())
                msg.save = true;
            chatPane.getItems().add(msg);
        }
    }
    
    public ArrayList<Message> getMsgsFromScreen(){
       ObservableList<Message> myList = chatPane.getItems();
       ArrayList<Message> list = new ArrayList<>();
       for (Message m : myList){
           list.add(m);
       }
       return list;
    }

    
    @FXML
    public void sendMsg(){
        Message msg = new Message();
        msg.setSenderId( String.valueOf(myId));
        msg.setReceiverId(String.valueOf(friendId));
        msg.setMessageType(Message.MessageType.TEXT);
        msg.setChatType(Message.ChatType.PEER_TO_PEER);
        msg.setColor(ColorUtil.fxToAwt(colorChooser.getValue()));
        msg.setFont(fontCombo.getValue());
        msg.setMessageContent((String) txtArea.getText().trim());
        msg.setDate(new Date());
        if(saveChat.isSelected())
            msg.save = true;
        chatPane.getItems().add(msg);
        txtArea.clear();
        controller.getChatService().sendMsg(msg);
        
       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          txtArea.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.ENTER)
                sendMsg();
            
        });
        colorChooser.setValue(Color.BLACK);
        usernameLabel.setText(friendUserContact.getFullName());
        friendStatus.setText(friendUserContact.getStatus().getName());
        saveChat.setSelected(true);
        for(String family:Font.getFamilies()){
            fontCombo.getItems().add(family);
        }
//       
        chatPane.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> param) {
                return new ListCell<Message>(){
                    @Override
                    protected void updateItem(Message item, boolean empty) {
                        super.updateItem(item, empty); 
                        if (item != null && !empty) {
                            FXMLLoader newLoader ;
                            if( Integer.parseInt(item.getSenderId()) == myId ){
                                this.setAlignment(Pos.BASELINE_RIGHT);
                                
                                newLoader = new FXMLLoader(getClass().getResource("/clientchat/view/chatwindow/bubblespeech/bubbleSpeechRightView.fxml"));
                            }else{
                                this.setAlignment(Pos.BASELINE_LEFT);
                                newLoader = new FXMLLoader(getClass().getResource("/clientchat/view/chatwindow/bubblespeech/bubbleSpeechView.fxml"));
                            }
                                Parent chatNode = null;
                            try {
                                chatNode = newLoader.load();
                                BubbleSpeechController bubbleSpeachController = newLoader.getController();
                                Text txt = new Text((String) item.getMessageContent());
                                if(txt.getLayoutBounds().getWidth() > 350){
                                    bubbleSpeachController.getTxtNode().setWrappingWidth(350);
                                }
                                bubbleSpeachController.getTxtNode().setText((String) item.getMessageContent());
                                bubbleSpeachController.getTxtNode().setTextAlignment(TextAlignment.LEFT);
                               
                                bubbleSpeachController.getTxtNode().setFill(ColorUtil.awtToFx(item.getColor()));
                                bubbleSpeachController.getTxtNode().setFont(Font.font(item.getFont()));

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
//                            flowPane.getChildren().add(chatNode);
                            setGraphic(chatNode);
                        } else {
                            setGraphic(null);
                        }
                        
                    }
                   
                };
            }
        });
        

    }    
    
}
