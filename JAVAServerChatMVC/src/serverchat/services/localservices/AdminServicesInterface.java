/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.services.localservices;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Yousef
 */
public interface AdminServicesInterface {
    public boolean startServer();
    public boolean stopServer();
    public void sendAnnouncement();
    public int getNumOfOnlineUsers();
    public HashMap<String, Integer> getGenderStat();
    public HashMap<String, Integer>  getCountryStat();
    public ArrayList getAllUsersInfos();
}
