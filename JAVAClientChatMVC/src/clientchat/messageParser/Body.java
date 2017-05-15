/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.messageParser;

/**
 *
 * @author fatma
 */
class Body {
    String content;
    String from;
    String date;

    Body(){}
    public Body(String content, String from, String date) {
        this.content = content;
        this.from = from;
        this.date = date;
    }

    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
