/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.services.localservices;

import client.ClientInt;
import common.dto.Dto;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverchat.dao.ChatUserDAO;
import server.AdminServicesInt;
import serverchat.ServerApp;
import serverchat.controller.Controller;
import serverchat.dao.DbConnector;
import serverchat.model.Model;

/**
 *
 * @author Yousef
 */
public class AdminServices extends DbConnector implements AdminServicesInt {

    private ChatUserDAO chatUserDAOObj;
    private Controller controller = ServerApp.getController();
    private Model model = controller.getModel();
    private final String insert;
    private PreparedStatement addUserStatement;
    private ResultSet rs;
    private HashMap<Integer, ClientInt> loggedClients;

    public AdminServices() {
        //super();
        tableName = "chatuser";
        insert = "insert into chatuser (id,username,fname,lname,email,password,gender,country) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            if (connect()) {
                addUserStatement = connection.prepareStatement(insert, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
      public ResultSet getUserInfoRS() {
        ChatUserDAO chatUserDAO = new ChatUserDAO();
        ResultSet resultSet = chatUserDAO.getAllInfoResultSet();
        return resultSet;
    }
    @Override
    public void startServer() {
        try {
            controller.getRegistry().rebind("ChatService", model);
        } catch (RemoteException ex) {
            Logger.getLogger(AdminServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stopServer() {
        try {
            controller.getRegistry().unbind("ChatService");
            
            for(Map.Entry<Integer,ClientInt> entry:controller.getLoggedClients().entrySet()){
                entry.getValue().exitApp();
            }
            controller.getLoggedClients().clear();
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(AdminServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public void sendAnnouncement(String announcement) {
        
        
        loggedClients = controller.getLoggedClients();
        for(Map.Entry<Integer,ClientInt> entry : loggedClients.entrySet()){
            try {
                entry.getValue().getMessagingService().receiveAnnouncement(announcement);
            } catch (RemoteException ex) {
                Logger.getLogger(AdminServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        Iterator iterator = loggedClients.entrySet().iterator();
//        while(iterator.hasNext()){
//            Map.Entry pair = (Map.Entry) iterator.next();
//            pair.getValue()
//        } 
    }

    @Override
    public int getNumOfOnlineUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     // All User Status (Online,Away, Busy ,Offline)

    public HashMap<String, Integer> getUserStatus(){
        chatUserDAOObj = new ChatUserDAO();
       HashMap<String, Integer> statusHash = null;
        statusHash=chatUserDAOObj.getAllStatus();
        return statusHash;
    }

    @Override
    public HashMap<String, Integer> getGenderStat() {
        chatUserDAOObj = new ChatUserDAO();
        HashMap<String, Integer> hash= null;
        hash=chatUserDAOObj.getAllGenders();

//        System.out.println("getgenderState"+hash.clone());
//        ResultSet rs = chatUserDAOObj.getFemaleGender();
//        try {
//            femaleCount = rs.getInt("total");
//           
//            rs = chatUserDAOObj.getMalegender();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(AdminServices.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
        return hash;
    }

    @Override
    public HashMap<String, Integer> getCountryStat() {
        chatUserDAOObj = new ChatUserDAO();
        HashMap<String, Integer> countryHash = chatUserDAOObj.getAllCountries();
        return countryHash;
    }

    @Override
    public ArrayList getAllUsersInfos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void addUser(int id, String username, String fname, String lname, String email, String password, int gender, String country) {

        try {
            addUserStatement.setInt(1, id);
            addUserStatement.setString(2, username);
            addUserStatement.setString(3, fname);
            addUserStatement.setString(4, lname);
            addUserStatement.setString(5, email);
            addUserStatement.setString(6, password);
            addUserStatement.setInt(7, gender);
            addUserStatement.setString(8, country);
            addUserStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Dto getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(Dto object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(Dto object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(Dto object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public int setUserId() {
        int usersNum=0;
        if(connect()){
                rs = getAll();
            try {
                while(rs.next()){
                    usersNum++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usersNum+1;
    }
}
