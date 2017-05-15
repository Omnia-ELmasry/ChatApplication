/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.messageParser;

import java.util.List;

/**
 *
 * @author fatma
 */
class MsgElement {
    String to;
    private List<Body>body;

    public MsgElement() {
    }

    
    public MsgElement(String to, List<Body> body) {
        this.to = to;
        this.body = body;
    }
    

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<Body> getBody() {
        return body;
    }
    
    public void setBody(List<Body> body) {
        this.body = body;
    }
    
}
