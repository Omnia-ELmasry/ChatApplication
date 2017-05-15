/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.view.statistics;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import serverchat.ServerApp;
import serverchat.controller.Controller;
import serverchat.services.localservices.AdminServices;

/**
 *
 * @author fatma
 */
public class StatisticsController implements Initializable {

    @FXML
    BarChart<String, Integer> barChart;

    @FXML
    PieChart pieChart;
    
    @FXML
    BarChart<String, Integer> barChartUser;
    @FXML
    private Label onlineLabel;
    @FXML
    private Label offlineLabel;

    Controller controller;

    HashMap hash, countryHash, statusHash;

//    ObservableList<PieChart.Data> pieChartData;
    ObservableList<Data> pieChartData = FXCollections.observableArrayList();

    public StatisticsController() {
        controller = ServerApp.getController();
//        System.out.println(controller);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series  series = new XYChart.Series<>();
        XYChart.Series  seriesUser = new XYChart.Series<>();
        
        // XYChart.Series series = series = new XYChart.Series<>();
        
        AdminServices adminServices = controller.getAdminServices();

        hash = adminServices.getGenderStat();
        barChart.setTitle("Gender Chart");
        Map<String, Integer> secondMap = new HashMap<String, Integer>(hash);
        for (Map.Entry entry : secondMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChart.getData().add(series);


           
        barChartUser.setTitle("User Chart");
         //HashMap<String, Integer> statusHash= null;
        statusHash=adminServices.getUserStatus();
        
        seriesUser.getData().add(new XYChart.Data<>("online",controller.getLoggedClients().size()));
        seriesUser.getData().add(new XYChart.Data<>("offline",controller.getAllUsers().size()-controller.getLoggedClients().size()));
        onlineLabel.setText(String.valueOf(controller.getLoggedClients().size()));
        offlineLabel.setText(String.valueOf(controller.getAllUsers().size()-controller.getLoggedClients().size()));
        barChartUser.getData().add(seriesUser);
            
        

//        /* Pie Chart*/
        countryHash = adminServices.getCountryStat();
        HashMap<String, Integer> countryMap = new HashMap<String, Integer>(countryHash);
        for (Map.Entry entry : countryMap.entrySet()) {
            entry.getKey();
            entry.getValue();
            pieChartData.addAll(new PieChart.Data(entry.getKey().toString(), entry.getValue().hashCode()));
        }
        pieChart.setData(pieChartData);
        
    
        
     
//            if(set.getKey().toString().equalsIgnoreCase("online")){
//                onlineId.setText(set.getValue().toString());
//            }else if(set.getKey().toString().equalsIgnoreCase("offline")){
//                offlineId.setText(set.getValue().toString());
//            }else if(set.getKey().toString().equalsIgnoreCase("away")){
//                awayId.setText(set.getValue().toString());
//            }else if(set.getKey().toString().equalsIgnoreCase("busy")){
//                busyId.setText(set.getValue().toString());
//            }
        }

    }
    

