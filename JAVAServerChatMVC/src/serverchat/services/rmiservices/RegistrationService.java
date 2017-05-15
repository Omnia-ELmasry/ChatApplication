/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.services.rmiservices;

import client.ClientInt;
import common.Response;
import common.UserContact;
import common.dto.FriendList;
import common.dto.User;
import common.enums.Status;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.*;
import serverchat.ServerApp;
import serverchat.controller.Controller;
import serverchat.dao.ChatUserDAO;

/**
 *
 * @author Yousef
 */
public class RegistrationService extends UnicastRemoteObject implements RegistrationServiceInt{
    Controller controller;
    
    public RegistrationService() throws RemoteException{
        controller = ServerApp.getController();
    }
    @Override
    public void register(int key , ClientInt clientRef){
        controller.getLoggedClients().put(key,clientRef);
        System.out.println("Client added");
    }

    @Override
    public void unRegister(int key , ClientInt clientRef){
        controller.getLoggedClients().remove(key);
        
        System.out.println("Client removed");
    }
    
    public HashMap<Integer,UserContact> getFriends(User user){
        HashMap<Integer,UserContact> friendList = new HashMap<>();
        for(FriendList list : user.getContactList()){
            int friendId = list.getFriendId();
            UserContact userContact = formUserContact(controller.getAllUsers().get(friendId));
            
            //check if the current friend is online to set him as online and assign to him the messagingService object
            //to communicate with him
            if(controller.getLoggedClients().containsKey(friendId)){
                userContact.setIsOnline(true);
                
                try {
//                    System.out.println(controller.getLoggedClients().get(friendId).getMessagingService()+"ff");
                    userContact.setMessagingService(controller.getLoggedClients().get(friendId).getMessagingService());
//                    controller.getLoggedClients()
//                            .get(friendId).getUserContact().getFriends().get(user.getID()).setMessagingService(messagingService);
                } catch (RemoteException ex) {
                    Logger.getLogger(RegistrationService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else
                userContact.setStatus(Status.OFFLINE.getId());
            
            friendList.put(friendId, userContact);
           
        }
        return friendList;
    }
    
    
    public UserContact formUserContact(User user){
        UserContact userContact = new UserContact();
        userContact.setID(user.getID());
        userContact.setUserName(user.getUserName());
        userContact.setEmail(user.getEmail());
        userContact.setCountry(user.getCountry());
        userContact.setImg(user.getImg());
        userContact.setfName(user.getfName());
        userContact.setlName(user.getlName());
        userContact.setGender(user.getGender());
        userContact.setStatus(user.getStatusId());
//        userContact.setFriends(getFriends(user));
        return userContact;
    }
    
    @Override
    public Response login(String userName, String pass) throws RemoteException {
        ChatUserDAO userDao = new ChatUserDAO();
        User user = userDao.authenticate(userName,pass);
        userDao.close();
        
        
        Response response = new Response();
        if(user != null){
            System.out.println("found user");
            response.setResponseOk(true);
            UserContact userContact = formUserContact(user);
            userContact.setFriends(getFriends(user));
            response.setResponseObject(userContact);
        }else{
            System.out.println("not found user");
            response.setResponseOk(false);
            response.setResponseMsg("Please provide valid user name & password");
        }
        return response;
    }

    
     public Response signUp(User newUser) throws RemoteException {
        String userName = newUser.getUserName() ;
        String Email = newUser.getEmail();
        ChatUserDAO userDao = new ChatUserDAO();
        userDao.connect();
        boolean foundname = userDao.signUpUserNameAuthenticate(userName);
        boolean foundemail = userDao.signUpEmailAuthenticate(Email);
        
        Response response = new Response();
        if( foundname  == true  || foundemail == true){
            System.out.println(" found user , failed to register");
            response.setResponseOk(false);
            response.setResponseMsg("you already registered  ");
        }else{
            System.out.println("New user");
            
//            response.setResponseObject(newUser);
            userDao.insertUser(newUser) ;
            response.setResponseOk(true);
            response.setResponseMsg("Done");
            
            controller.getAllUsers().clear();
            controller.getAllUsers().putAll(userDao.getAllUsers());
            userDao.close();
        }
        userDao.close();
        return response;
    }
    
     public void updateMyStatus(int id , Status status){
        try {
            ChatUserDAO chatUserDao = new ChatUserDAO();
            chatUserDao.updateUserStatus(id, status.getId());
            
            controller.getLoggedClients().get(id).getUserContact().setStatus(status.getId());
            controller.getAllUsers().get(id).setStatusId(status.getId());
        } catch (RemoteException ex) {
            Logger.getLogger(RegistrationService.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
