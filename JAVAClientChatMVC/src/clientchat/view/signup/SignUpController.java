/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.view.signup;

import clientchat.MyApp;
import clientchat.controller.Controller;
import clientchat.services.localservices.RegistrationService;
import clientchat.services.localservices.SignUpServices;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTogglePane;
import common.Response;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import common.dto.User;
import static java.awt.SystemColor.text;
import java.io.IOException;
import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import static java.util.Collections.list;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Toggle;

/**
 * FXML Controller class
 *
 * @author omnia
 */
public class SignUpController implements Initializable {

    private Controller controller;

    private String combo = null;
    @FXML
    private JFXTextField fristName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXPasswordField password;

    @FXML
    private ComboBox<String> country;

    @FXML
    private Button Singup;

    @FXML
    private Button Cancel;

    private RegistrationService signUpService;

    @FXML
    private Label msg1 = new Label();

    @FXML
    private Label msg2 = new Label();

    @FXML
    private Label msg3 = new Label();

    @FXML
    private Label msg4 = new Label();

    @FXML
    private Label msg5 = new Label();

    @FXML
    private Label msg6 = new Label();

    @FXML
    private Label msg7 = new Label();

    @FXML
    private Label msg8 = new Label();

    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView doneImage;
    @FXML
    private RadioButton female;

    @FXML
    private ToggleGroup gender;
    private String genderCheck;
    @FXML
    private RadioButton male;
   @FXML
    private Label resultMsg;
   
    public Label getResultMsg(){
       return resultMsg;
    }
    User newUser = new User();

    String nameFormat = "[a-zA-Z]*";
    String mail = "[a-zA-Z_][a-zA-Z0-9_]*@[a-zA-Z][a-zA-Z0-9_]+(\\.[a-zA-Z][a-zA-Z0-9_]*)+";
    ObservableList<String> list = FXCollections.observableArrayList("Afghanistan", "Albania", "Algeria", "American Samoa", "Anguilla", "Australia", "Belize", "Benin", "Canada", "China", "Egypt", "Germany", "Kuwait", "Lebanon", "Libya", "Morocco", "Oman", "Qatar", "Saudi Arabia", "South Africa", "Sudan", "Turkey", "UAE", "UK", "USA");
    boolean rflag = false, valid = false;
    int gender_type = 1;
    boolean cflag = false;
  
    public SignUpController() {

        
        controller = MyApp.getController();

        signUpService = controller.getRegistrationService();
    }

    @FXML
    void registrationBtn(ActionEvent event) {

        
        // animateLogo() ;
       
        msg1.setText("");
        msg2.setText("");
        msg3.setText("");
        msg4.setText("");
        msg5.setText("");
        msg6.setText("");
         msg7.setText("");
         msg8.setText("");
         
    //    combo = country.getValue().toString(); //moshkala hena 
        if (fristName.getText().isEmpty()) {
            msg1.setText("Please Enter Your First Name");

        } else if (!fristName.getText().matches(nameFormat)) {
            msg1.setText("Invalid First Name");
        }
        
        
        if (lastName.getText().isEmpty()) {
            msg2.setText("Please Enter Your Last Name");
        } else if (!lastName.getText().matches(nameFormat)) {
            msg2.setText("Invalid Last Name");
        }
        if (password.getText().isEmpty()) {

            msg5.setText("Please Enter Password");
        } else if (!check_password()) {
            msg5.setText("Please Enter 8 or more characters in Password Field");

        }
        if (userName.getText().isEmpty()) {
            msg3.setText("Please Enter Your UserName");
        } else if (!userName.getText().matches(nameFormat)) {
            msg3.setText("Invalid userName");
        }
        if (!rflag) {
            msg7.setText("select gender");
        }
        
        if (email.getText().isEmpty()) {
            msg4.setText("Please Enter The E-mail");
        } else if (!check_mail()) {
            msg4.setText("Invalid Mail");

        }
        if (country.getValue() == null) {
            msg8.setText("Please Enter Country");
        }
        
        
        if (msg1.getText().isEmpty() && msg2.getText().isEmpty() 
                && msg3.getText().isEmpty() && msg4.getText().isEmpty() 
                && msg5.getText().isEmpty() && msg7.getText().isEmpty()
                && msg8.getText().isEmpty()) {
            newUser.setfName(fristName.getText());
            newUser.setlName(lastName.getText());
            newUser.setUserName(userName.getText());
            newUser.setPassword(password.getText());
            newUser.setEmail(email.getText());
            newUser.setGender(gender_type);
            newUser.setCountry((String) country.getValue());

            signUpService.signUpCall(newUser,this);
//            System.out.println(resp);
//            if(resp.isResponseOk() == false)
//                resultMsg.setText(resp.getResponseMsg());
             // animation dont move ?

//            if (resp.isResponseOk()) {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Registration Done  ");
//                alert.setContentText("You registered succesfully !");
//                alert.showAndWait();
//
//                try {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientchat/view/login/loginView.fxml"));
//                    controller.setLoader(loader);
//                    Parent root = loader.load();
//                    controller.setRoot(root);
//                    controller.getPrimaryStage().setScene(new Scene(root));
//                    controller.getPrimaryStage().show();
//                } catch (IOException ex) {
//                    Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
            
        } else {
            animateLogo();
        }

        
    }

    public boolean check_mail() {
        boolean flag = false;
        if (email.getText().matches(mail)) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public boolean check_password() {
        boolean flag = false;
        int length = password.getText().length();
        if (length < 8) {
            flag = false;
        } else {
            msg5.setVisible(false);
            flag = true;
        }
        return flag;
    }

    public void animateLogo() {
        logoImage.setVisible(true);
        TranslateTransition tt
                = new TranslateTransition(Duration.seconds(60), logoImage);

        tt.setFromX(-(logoImage.getFitWidth()));
        tt.setCycleCount(Timeline.INDEFINITE);
        tt.play();
    }

    public void animateDone() {
        doneImage.setVisible(true);
        logoImage.setVisible(false);
        TranslateTransition tt
                = new TranslateTransition(Duration.seconds(60), doneImage);

        tt.setFromX(-(doneImage.getFitWidth()));
        //  tt.setToX( signUpView.getPrefWidth() );
        tt.setCycleCount(Timeline.INDEFINITE);
        tt.play();
    }

    @FXML
    public void country(ActionEvent event) {
        combo = country.getValue().toString();
        System.err.println(combo);
        if (country.getValue() != null || !country.getValue().toString().isEmpty()) {
            cflag = true;
        } else {
            cflag = false;
        }
    }

    public void Radio_changed(ActionEvent event) {
        if (male.isSelected()) {
            genderCheck = male.getText();
            rflag = true;
            gender_type = 2;
        }
        if (female.isSelected()) {
            genderCheck = female.getText();
            rflag = true;
            gender_type = 1;
        }
        System.err.println(gender_type);

    }

    @FXML
    void cancelBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientchat/view/login/loginView.fxml"));
            controller.setLoader(loader);  
            Parent root = loader.load();
            controller.setRoot(root);
            controller.getPrimaryStage().setScene(new Scene(root));
            controller.getPrimaryStage().show();
        } catch (IOException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        country.getItems().addAll(list);
        
                gender.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                    public void changed(ObservableValue<? extends Toggle> ov,
                            Toggle old_toggle, Toggle new_toggle) {
                        if (gender.getSelectedToggle() != null) {
                            if (male.isSelected()) {
                                genderCheck = male.getText();
                                gender_type = 1;
                            }
                            if (female.isSelected()) {
                                genderCheck = female.getText();
                                gender_type = 2;
                            }
                            rflag = true;
                        }
                    }
                });

    }

}
