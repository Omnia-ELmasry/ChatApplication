/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.view.chatwindow.bubblespeech;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author Yousef
 */
public class BubbleSpeechController implements Initializable {

    @FXML
    private Text txtNode;
    @FXML
    private Label txtFlow;
    @FXML
    private ImageView imgView;
    @FXML
    private Ellipse bbl2;
    @FXML
    private Ellipse bbl1;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label chatDateLabel;

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Label getChatDateLabel() {
        return chatDateLabel;
    }

    public void setChatDateLabel(Label chatDateLabel) {
        this.chatDateLabel = chatDateLabel;
    }
    
    
    public Text getTxtNode() {
        return txtNode;
    }

    public Label getTxtFlow() {
        return txtFlow;
    }

    public void setTxtFlow(Label txtFlow) {
        this.txtFlow = txtFlow;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }

    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
