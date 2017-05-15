/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.messageParser;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author fatma
 */
@XmlRootElement
//@XmlSeeAlso(MsgElement.class)
public class MsgRoot{
    String header;
    private List<MsgElement> msgElement;

    MsgRoot(){}
    MsgRoot(String header,List<MsgElement> msgElement){
        this.header=header;
        this.msgElement= msgElement;
    }
    public String getHeader() {
        return header;
    }
    @XmlElement
    public void setHeader(String header) {
        this.header = header;
    }

    public List<MsgElement> getMsgElement() {
        return msgElement;
    }
    @XmlElement
    public void setMsgElement(List<MsgElement> msgElement) {
        this.msgElement = msgElement;
    }
    
}
