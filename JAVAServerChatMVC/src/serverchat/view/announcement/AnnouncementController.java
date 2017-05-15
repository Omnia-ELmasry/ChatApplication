/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.view.announcement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import serverchat.ServerApp;

/**
 * FXML Controller class
 *
 * @author Masoud
 */
public class AnnouncementController implements Initializable {
    
    @FXML
    private Button sendButton;
    @FXML
    private TextArea announcement;

    public Button getSendButton() {
        return sendButton;
    }

    public void setSendButton(Button sendButton) {
        this.sendButton = sendButton;
    }

    public TextArea getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(TextArea announcement) {
        this.announcement = announcement;
    }
    
    @FXML
    public void sendButtonHandler(){
//        System.out.println(ServerApp.getController().getAdminServices());
        ServerApp.getController().getAdminServices().sendAnnouncement(announcement.getText());
        announcement.clear();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
