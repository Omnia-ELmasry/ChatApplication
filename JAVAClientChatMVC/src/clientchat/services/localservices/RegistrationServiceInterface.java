/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.services.localservices;

import common.dto.User;

/**
 *
 * @author Yousef
 */
public interface RegistrationServiceInterface {
   public void login(String userNamee, String pass);
   public void signUp(User user);
}
