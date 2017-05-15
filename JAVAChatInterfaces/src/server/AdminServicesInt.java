/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Yousef
 */
public interface AdminServicesInt {
    public void startServer();
    public void stopServer();
//    public void addUser(int id, String username, String fname, String lname, String email, String password, String gender, String country);
//    public void sendAnnouncement();
    public int getNumOfOnlineUsers();
    public HashMap<String, Integer> getGenderStat();
    public HashMap<String, Integer>  getCountryStat();
    public ArrayList getAllUsersInfos();
}
