/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.services.localservices;

import clientchat.MyApp;
import clientchat.controller.Controller;
import clientchat.model.Model;
import clientchat.services.rmiservices.MessagingService;
import clientchat.view.homewindow.HomeWindowViewController;
import clientchat.view.homewindow.NotificationController;
import clientchat.view.login.LoginViewController;
import clientchat.view.signup.SignUpController;
import common.Response;
import common.UserContact;
import common.dto.User;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import org.controlsfx.control.Notifications;
import server.RegistrationServiceInt;
import server.ServerInt;

/**
 *
 * @author Yousef
 */
public class RegistrationService extends LocalService implements RegistrationServiceInterface {

    private Controller controller;
    private ServerInt serverRemote;
    Response response;

    public RegistrationService() {
        controller = MyApp.getController();
    }
    // i need to know how to validate ip and check it + 

    public boolean registryLookup(String ipAddress) {
        try {

            Registry reg = LocateRegistry.getRegistry(ipAddress);
            controller.setRegistry(reg);
            serverRemote = (ServerInt) reg.lookup("ChatService");
            if(serverRemote == null)
                return false;
            controller.setServerRemote(serverRemote);
            return true;
        } catch (RemoteException ex) {
            System.out.println("can't connect to server");
            return false;
        } catch(java.rmi.NotBoundException ex){
             Notifications notificationBiulder = Notifications.create().title("Error").text("Server is not available")
                                    .hideCloseButton().position(Pos.CENTER).darkStyle();
                                    notificationBiulder.showError();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void registerModel() {
        try {
            Model m = new Model(controller);
            controller.setModel(m);
            RegistrationServiceInt remoteRegistration = serverRemote.getRegistrationService();
            remoteRegistration.register(controller.getMyUserContact().getID(), m);

//           controller.getMyUserContact().setMessagingService(m.getMessagingService());
        } catch (RemoteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void login(String userNamee, String pass) {
        
        Platform.runLater(() -> {
            try {
                Response response = serverRemote.getRegistrationService().login(userNamee, pass);
                if (response != null) {
                    if (response.isResponseOk()) {
                        controller.setMyUserContact((UserContact) response.getResponseObject());
                        registerModel();
//                        System.out.println(controller.getMyUserContact().getFriends().get(5).getfName());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/clientchat/view/homewindow/homeWindowView.fxml"));
                        controller.setLoader(loader);
                        Parent root = loader.load();
                        controller.setRoot(root);
                        controller.homeWindowController = loader.getController();

                        controller.getPrimaryStage().setScene(new Scene(root));
                        controller.getPrimaryStage().show();
                    } else {
                        LoginViewController loginController = controller.getLoader().getController();
                        loginController.showResponse(response);
                    }
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    public void signUpCall(User user, SignUpController signController) {
        
        Platform.runLater(() -> {
            try {

                String ip = LoginViewController.ipAddressGlobal;
                if (registryLookup(ip)) {

                    response = serverRemote.getRegistrationService().signUp(user);

                    if (response != null) {
                        if (response.isResponseOk()) {
                            signController.animateDone();
//                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                            alert.setTitle("Registration Done  ");
//                            alert.setContentText("You registered succesfully !");
//                            alert.showAndWait();
//                            FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/clientchat/view/homewindow/notification.fxml"));
//
//                            Parent node = null;
//
//                            try {
//                                node = newLoader.load();
//                            } catch (IOException ex) {
//                                Logger.getLogger(MessagingService.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            NotificationController notificationController = newLoader.getController();
//                            notificationController.getFriendName().setText("You registered succesfully !");
//                            Image img = new Image(getClass().getResourceAsStream("/clientchat/resources/Done2.png"));
//                            notificationController.getImg().setImage(img);
                            Notifications notificationBiulder = Notifications.create().title("Confirmation").text("You registered succesfully !")
                                    .hideCloseButton().position(Pos.CENTER).darkStyle();
                                    notificationBiulder.showConfirm();


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

                        } else {
                            signController.getResultMsg().setText(response.getResponseMsg());
                            signController.animateLogo();
                        }

                    }

                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } 
        });

    }

    @Override
    public void signUp(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
