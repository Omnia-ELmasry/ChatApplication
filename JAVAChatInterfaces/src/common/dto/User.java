
package common.dto;

import common.UserContact;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;


/**
 *
 * @author omnia
 */
public class User extends Dto {

    private int ID;
    private String fName;
    private String lName;
    private String userName;
    private String email;
    private String password;
    private String imgPath;
    private Image img;
    private int gender;
    private String country;
    private int statusId;
    private String txtStatus;
    private String ip;
    private ArrayList<FriendList> contactList;

    public User() {
    }

    public User(int ID, String fName, String lName, String userName, String email, String password) {
        this.ID = ID;
        this.fName = fName;
        this.lName = lName;
        this.userName = userName;
        this.email = email;
        this.password = password;
            
    }

    public Image getImg() {
        return img;
    }


    public ArrayList<FriendList> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<FriendList> contactList) {
        this.contactList = contactList;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
        if(this.imgPath != null )
            this.img = new Image(this.imgPath);
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getTxtStatus() {
        return txtStatus;
    }

    public void setTxtStatus(String txtStatus) {
        this.txtStatus = txtStatus;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setCountry(ComboBox<?> country) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    

}
