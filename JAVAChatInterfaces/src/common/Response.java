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
public class Response implements Serializable{
    private String responseMsg;
    private boolean responseOk;
    private Object responseObject;

    public Response() {
        responseMsg = null;
        responseOk = false;
        responseObject = null;
        
    }

    public Response(String responseMsg, boolean responseOk, Object responseObject) {
        this.responseMsg = responseMsg;
        this.responseOk = responseOk;
        this.responseObject = responseObject;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public boolean isResponseOk() {
        return responseOk;
    }

    public void setResponseOk(boolean responseStatus) {
        this.responseOk = responseStatus;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
    
    
}
