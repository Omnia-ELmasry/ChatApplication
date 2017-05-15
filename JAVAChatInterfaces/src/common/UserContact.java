/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import client.MessagingServiceInt;
import common.enums.Gender;
import common.enums.Status;
import java.io.Serializable;
import java.util.HashMap;
import javafx.scene.image.Image;

/**
 *
 * @author Yousef
 */
public class UserContact implements Serializable{
    private int ID;
    private String fName;
    private String lName;
    private String userName;
    private String email;
    private Image img;
    private Gender gender;
    private String country;
    private Status status;
    private String txtstatus;
    private boolean isOnline;
    
    private int friendCategory;
    
    private MessagingServiceInt messagingService;
    private HashMap<Integer,UserContact> friends;
    
    public String getFullName(){
        return fName+" "+lName;
    }
    public HashMap<Integer, UserContact> getFriends() {
        return friends;
    }

    public void setFriends(HashMap<Integer, UserContact> friends) {
        this.friends = friends;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    
    
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(int genderId) {
        switch(genderId){
            case 1:
                this.gender = Gender.MALE;
                break;
            case 2:
                this.gender = Gender.FEMALE;
                break;
        }
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(int statusId) {
        switch (statusId) {
            case 1:
                this.status = Status.ONLINE;
                break;
            case 2:
                this.status = Status.AWAY;
                break;
            case 3:
                this.status = Status.BUSY;
                break;

            case 4:
                this.status = Status.OFFLINE;
                break;
        }
    }

    public String getTxtstatus() {
        return txtstatus;
    }

    public void setTxtstatus(String txtstatus) {
        this.txtstatus = txtstatus;
    }

    public MessagingServiceInt getMessagingService() {
        return messagingService;
    }

    public void setMessagingService(MessagingServiceInt messagingService) {
        this.messagingService = messagingService;
    }
    
    
    
}
