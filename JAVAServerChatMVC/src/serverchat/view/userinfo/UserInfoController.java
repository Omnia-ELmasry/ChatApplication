/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.view.userinfo;

import common.dto.User;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import serverchat.ServerApp;
import serverchat.controller.Controller;
import serverchat.dao.ChatUserDAO;
import serverchat.services.localservices.AdminServices;

/**
 *
 * @author fatma
 */
public class UserInfoController implements Initializable {
//
//    @FXML
//    Label Id;
//    @FXML
//    Label userName;
//    @FXML
//    Label fName;
//    @FXML
//    Label lName;
//    @FXML
//    Label email;
//    @FXML
//    Label gender;
//    @FXML
//    Label statusId;
//    @FXML
//    Label country;
//    @FXML
//     TreeView tvId;
//    

    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;
    @FXML
    private TableView tableview;

    Controller controller;

    public UserInfoController() {
        controller = ServerApp.getController();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
//        User user = new User();
//        AdminServices adminServices = controller.getAdminServices();
//        user = adminServices.getUserInfo();
//        fName.setText(user.getfName());
//        lName.setText(user.getlName());
//        email.setText(user.getEmail());
// fName.setText(user.getGender());
//fName.setText(user.getStatusId());

    ResultSet resultSet;
            AdminServices adminServices = controller.getAdminServices();
        resultSet = adminServices.getUserInfoRS();

        
        try {

             /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */
            data = FXCollections.observableArrayList();
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                //System.out.println("1"+ col.getText());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                      
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                      //    System.out.println(param.getValue().get(j).toString()+ "  " +"j"+ j);
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
//                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
//                        System.out.println("2"+param);
//                        return new SimpleStringProperty(param.getValue().get(j).toString());
//                    }
                });

                tableview.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }
            
            /**
             * ******************************
             * Data added to ObservableList * ******************************
             */
            while (resultSet.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
//                    if(resultSet.getInt(i)==1){
//                       row.add("Male"); 
//                    }else if( resultSet.getInt("gender")==2){
//                         row.add("Female"); 
//                    }
//                    else{  
                    String str = resultSet.getString(i);
                    
                    System.out.println(str);
                    if(i==5){
                        if(str.equals("1"))
                            str="male";
                        else str="female";
                        
                    }
                    if(i==7){
                        if(str.equals("1"))
                            str="online";
                        else if(str.equals("2")) str="away";
                        else if(str.equals("3")) str="busy";
                        else if(str.equals("4")) str="ofline";
                        
                    }
                    row.add(str);
//                    }
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
                //FINALLY ADDED TO TableView
                tableview.setItems(data);
                
            }

            
        } catch (SQLException ex) {
            Logger.getLogger(UserInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
          
  
    }

}
