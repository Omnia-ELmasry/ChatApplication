/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.dao;

import common.dto.FriendList;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yousef
 */
public class FriendListDAO extends DbConnector<FriendList>{
    PreparedStatement preparedStatement;
    Statement stm;
    String queryString;
    
    
    
    public FriendListDAO() {
        tableName = "friendlist";
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@" + ipAddress + ":" + port + ":xe", dbUserName, dbPassword);

            stm = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(ChatUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<FriendList> getFriendsIds(int userId){
        String query = "SELECT * FROM " + tableName +" where USER_ID = "+userId;
        try {

            resultSet = stm.executeQuery(query);
            ArrayList<FriendList> friends = new ArrayList<>();
            
            while (resultSet.next()) {
                FriendList obj = new FriendList();
                obj.setUserId(resultSet.getInt("USER_ID"));
                obj.setFriendId(resultSet.getInt("FRIEND_ID"));
                obj.setCategoryId(resultSet.getInt("CATEGORY_ID"));
                friends.add(obj);
            }
            return friends;
        } catch (SQLException ex) {
            Logger.getLogger(ChatUserDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return null;
    }
    
    
    @Override
    public FriendList getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(FriendList object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(FriendList object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(FriendList object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
