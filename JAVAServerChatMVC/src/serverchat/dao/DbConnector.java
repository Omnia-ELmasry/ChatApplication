/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.dao;
import common.dto.Dto;
import oracle.jdbc.driver.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yousef
 */
public abstract class DbConnector<T extends Dto> {
    {
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;
    
    protected String dbUserName;
    protected String dbPassword;
    protected String ipAddress;
    protected String port;
    protected String tableName;
    
    public DbConnector(){
        dbUserName = "dbuser";
        dbPassword = "dbuser";
        ipAddress = "127.0.0.1";
        port = "1521";
    }
    
    public boolean connect(){
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@"+ipAddress+":"+port+":xe",dbUserName,dbPassword);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    protected ResultSet getAll(){
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String queryString = new String("Select * from "+tableName+" order by id");
        
            resultSet = statement.executeQuery(queryString);
            
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    protected int getNumOfRecords(ResultSet resultSet) {
        if (resultSet == null) {
            return 0;
        }
        try {
            resultSet.last();
            return resultSet.getRow();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                resultSet.beforeFirst();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        }
        return 0;
    }
   
    
    public abstract T getById(int id);
    
    public abstract int insert(T object);
    
    public abstract int update(T object);
    
    public abstract int delete(T object);
    
      
    
    public void runQuery(String query){
        try {
            statement = connection.createStatement();
            String queryString = new String(query);
        
            ResultSet rs = statement.executeQuery(queryString);
            
//            while(rs.next()){
//                System.out.println(rs.getString(2));
//            }
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    public void close(){
        try {
           // statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
