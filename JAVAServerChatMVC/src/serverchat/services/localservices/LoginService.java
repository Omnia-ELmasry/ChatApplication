/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.services.localservices;


import common.dto.Dto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerInt;
import serverchat.ServerApp;
import serverchat.controller.Controller;
import server.LoginServiceInt;
import serverchat.dao.DbConnector;

/**
 *
 * @author Yousef
 */
public class LoginService extends DbConnector implements LoginServiceInt{
   
    private final Controller controller;
    private ServerInt serverRemote;
    private ResultSet rs;
    public LoginService(){
        tableName = "admin";
        controller = ServerApp.getController();
        
    }

    
    //call db classes and if the user exist open the home page of the admin gui
    @Override
    public boolean login(String userName, String passWord) {
        
        if(connect()){
            try {
                rs = getAll();
                while(rs.next()){
                    if(userName.equals(rs.getString(2))&&passWord.equals(rs.getString(3))){
                        return true;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
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

   
}
