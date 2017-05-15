/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.view.login;

import serverchat.dao.*;
import common.dto.User;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Yousef
 */
public class ChatUserDAO extends DbConnector<User> {

    PreparedStatement preparedStatement;
    Statement stm;
    String queryString;


    public ChatUserDAO() {
        tableName = "chatuser";
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@" + ipAddress + ":" + port + ":xe", dbUserName, dbPassword);

            stm = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(ChatUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public HashMap<Integer,User> getAllUsers(){
        try {
            HashMap<Integer, User> list = new HashMap<>();
            resultSet = getAll();
            
            while (resultSet.next()) {
                
                User user = new User();    
                user.setID(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setfName(resultSet.getString("fname"));
                user.setlName(resultSet.getString("lname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setGender(resultSet.getInt("gender"));
                user.setImgPath(resultSet.getString("img"));
                user.setStatusId(resultSet.getInt("status_id"));
                
                user.setContactList(new FriendListDAO().getFriendsIds(resultSet.getInt("id")));
                list.put(resultSet.getInt("id"),user);
                
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ChatUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
     public HashMap<String, Integer> getAllStatus() {
        HashMap<String, Integer> statusHash = new HashMap<>();
        int onlineCount = 0, offlineCount=0, awayCount=0, busyCount=0;
        
      // Status.ONLINE
        String statusQuery = "select STATUS_ID from " + tableName;
        try {
            resultSet = stm.executeQuery(statusQuery);
            while (resultSet.next()) {
                switch (resultSet.getInt(1)) {
                    case 1:
                        onlineCount++;
                        statusHash.put("Online", onlineCount);
                        break;
                    case 2:
                        awayCount++;
                        statusHash.put("Away", awayCount);
                        break;
                    case 3:
                         busyCount++;
                        statusHash.put("Busy", busyCount);
                        break;
                    case 4:
                         offlineCount++;
                        statusHash.put("Offline", offlineCount);
                        break;
                    default:
                        System.out.println("UnKnown Status");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChatUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return statusHash;
    }
    
     
    public HashMap<String, Integer> getAllGenders() {
    //for Gender
//    public HashMap<String, Integer> getAllRecords() {

        HashMap<String, Integer> hash = new HashMap<>();

        String userQuery = "SELECT GENDER FROM " + tableName;
//        String userQuery = "SELECT GENDER FROM chatuser";
        try {

            resultSet = stm.executeQuery(userQuery);
                int maleCount = 0;
                int femaleCount = 0;
                int undefinedGender = 0;
            while (resultSet.next()) {

                switch (resultSet.getInt(1)) {
                    case 1:
                        maleCount++;
                        hash.put("male", maleCount);
                        break;
                    case 2:
                        femaleCount++;
                        hash.put("female", femaleCount);
                        break;
                    default:
                        undefinedGender++;
                        hash.put("unDefinedGender", undefinedGender);
                        break;
                }

                /* 
                //oldCode
//                  if (resultSet.getString(1).equalsIgnoreCase("male")) {
                 if (resultSet.getInt(1)== 1) {
                    maleCount++;
                    hash.put("male", maleCount);
//                } else if (resultSet.getString(1).equalsIgnoreCase("female")) {
                  } else if (resultSet.getInt(1)==2){

                    femaleCount++;
                    hash.put("female", femaleCount);
                } else {
                    undefinedGender++;
                    hash.put("unDefinedGender", undefinedGender);
                }
                 */
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChatUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println("getgenderState"+hash.toString());
        return hash;
    }

    public HashMap<String, Integer> getAllCountries(){
        int countryCount = 0;
        HashMap<String, Integer> countryHash = new HashMap<>();
        String countryQuery = "SELECT country FROM " + tableName;
        try {
            resultSet = stm.executeQuery(countryQuery);

            String countryName = null;
            int newCounter = 1;
            while (resultSet.next()) {
                countryCount++;
                countryHash.put(resultSet.getString(1), countryCount);

                for (Map.Entry<String, Integer> entry : countryHash.entrySet()) {
                    String key = entry.getKey();
                    int value = entry.getValue();
                    
                     if (resultSet.getString(1).equalsIgnoreCase(key)) {
                         value++;
                          countryHash.put(resultSet.getString(1), value);
                     }else{
                         countryHash.put(resultSet.getString(1), newCounter);
                     }
                }
                
//                
//                
//                if (resultSet.getString(1).equalsIgnoreCase(countryName)) {
//                    countryCount++;
//                    countryHash.put(resultSet.getString(1), countryCount);
//                    countryName = resultSet.getString(1);
//                    System.out.println(countryName + countryCount);
//
//                } else {
//
//                    countryHash.put(resultSet.getString(1), ++newCounter);
//                    countryName = resultSet.getString(1);
//                    System.out.println(countryName + countryCount);
//                    newCounter = 0;
//                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(ChatUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return countryHash;
    }

    @Override
    public User getById(int id
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(User object
    ) {
//        
        return 0;
    }

    @Override
    public int delete(User object
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public User authenticate(String userName, String pass) {
        try {
            String query = "select * from " + tableName + " where username = ? and password = ?";
            preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            User user = new User();
            if (getNumOfRecords(resultSet) > 0) {
                resultSet.next();
                user.setID(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setfName(resultSet.getString("fname"));
                user.setlName(resultSet.getString("lname"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setGender(resultSet.getInt("gender"));
//                user.setImgPath(resultSet.getString("img"));
                user.setStatusId(resultSet.getInt("status_id"));
                
                user.setContactList(new FriendListDAO().getFriendsIds(resultSet.getInt("id")));
                return user;

            } else {
                return null;
            }
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean signUpUserNameAuthenticate(String userName) {

        try {
            String query = "select * from " + tableName + " where username = ? ";
            System.out.println(query);
            preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setString(1, userName);

            resultSet = preparedStatement.executeQuery();

            if (getNumOfRecords(resultSet) == 0) {

                return false;
            } else {

                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ChatUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }       //preparedStatement.close();

    public boolean signUpEmailAuthenticate(String Email) {

        try {
            String query = "select * from " + tableName + " where email = ? ";
            System.out.println(query);
            preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setString(1, Email);

            resultSet = preparedStatement.executeQuery();

            if (getNumOfRecords(resultSet) == 0) {

                return false;
            } else {

                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ChatUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int insertUser(User user){
         int affectedRows = 0;
        try {
          
            int newId=0;
            stm = connection.createStatement();
            String query = new String("select chatuser_id_seq.nextval from dual");
        
            ResultSet res = stm.executeQuery(query);
            
            while(res.next()){
                newId = res.getInt(1);
            }
        
//            rs.last();
//            res.afterLast();
            
            connection.setAutoCommit( true );
             String queryString = "insert into chatuser (\"ID\", \"USERNAME\", \"FNAME\", \"LNAME\", \"EMAIL\", \"PASSWORD\", \"GENDER\",\"COUNTRY\",\"STATUS_ID\") "
                     + "values("+newId+",'"+user.getUserName()+"','"+user.getfName()+"','"+
                     user.getlName()+"','"+user.getEmail()+"','"+user.getPassword()+"','"+user.getGender()+"','"+user.getCountry()+"',1)";
                     
            System.out.println(queryString);
        
            affectedRows = stm.executeUpdate(queryString);
            
            
           
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
              
    }
    
    @Override
    public int insert(User user) {
        try {

            preparedStatement = connection.prepareStatement("insert into "+tableName+""
                    + "(\"ID\", \"USERNAME\", \"FNAME\", \"LNAME\", \"EMAIL\", \"PASSWORD\", \"GENDER\",\"COUNTRY\") "
                    + "values(chatuser_id_seq.nextval,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getfName());
            preparedStatement.setString(3, user.getlName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getGender());
            preparedStatement.setString(7, user.getCountry());
            preparedStatement.executeUpdate();
            preparedStatement = (PreparedStatement) stm.executeQuery(queryString);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                return 1;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0;
    }
    
    public int updateUserStatus(int id , int status){
         int affectedRows = 0;
        try {
            connection.setAutoCommit( true );
             String queryString = "update chatuser set STATUS_ID="+status+" where id = "+id;
            System.out.println(queryString);
        
            affectedRows = stm.executeUpdate(queryString);
            
            
            
           
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
              
    }
    
    //chat userDAO
    public ResultSet getAllInfoResultSet() {
        String infoQuery = "select username, fname, lname, email, gender, country, status_id as Status from " + tableName;
        try {
            resultSet = stm.executeQuery(infoQuery);
//            while(resultSet.next()){
//                int genderId=resultSet.getInt("gender");
//               
//            }
        } catch (SQLException ex) {
            Logger.getLogger(ChatUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

}
