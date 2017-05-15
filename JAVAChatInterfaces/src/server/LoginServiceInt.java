/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import common.dto.User;

/**
 *
 * @author Yousef
 */
public interface LoginServiceInt {
   public boolean login(String userName, String passWord);

}
