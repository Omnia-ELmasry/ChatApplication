/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;

/**
 *
 * @author Yousef
 */
public class Request implements Serializable{
    private String userId;
    private Object requestObject;

    public Request() {
    }

    public Request(String userId, Object requestObject) {
        this.userId = userId;
        this.requestObject = requestObject;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(Object requestObject) {
        this.requestObject = requestObject;
    }

   
}
