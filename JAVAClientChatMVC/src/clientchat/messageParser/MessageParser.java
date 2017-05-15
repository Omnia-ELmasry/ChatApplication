/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchat.messageParser;

import clientchat.MyApp;
import clientchat.controller.Controller;
import common.Message;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author fatma
 */
public class MessageParser {

    /**
     * @param args the command line arguments
     */
    
    public static void copyFile(File file, File destFolder) {
        try {
            File destFile;
            destFile = new File(destFolder, file.getName());
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            destFile.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));

            int theByte;
            while((theByte = bis.read()) != -1){
                bos.write(theByte);
            }
            bis.close();
            bos.flush();
            bos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void parseMsgs(ArrayList<Message> msgs,int friendId){
        Controller controller = MyApp.getController();
        List<Body> bodyList = new ArrayList<>();
        int myId = controller.getMyUserContact().getID();
        for(Message msg:msgs){
            if(msg.save){
            Body body = new Body();
            if(msg.getMessageType().equals(Message.MessageType.TEXT)){
                body.content = (String) msg.getMessageContent();
                body.date = msg.getDate().toGMTString();
                if(Integer.parseInt(msg.getSenderId()) == myId)
                    body.from = "me";
                else{
                   
                    body.from = controller.getMyUserContact().getFriends().get(Integer.parseInt(msg.getSenderId())).getfName()+" says :";
                }
                bodyList.add(body);
                
            }
            }
        }

        List<MsgElement> msgElementList = new ArrayList<>();
        msgElementList.add(new MsgElement("To XYZ", bodyList));

        MsgRoot msgRoot = new MsgRoot("Msg Header", msgElementList);

        try {
            JAXBContext jAXBContext = JAXBContext.newInstance(MsgRoot.class);
            Marshaller jaxbMarshaller = jAXBContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", "<?xml-stylesheet type='text/xsl' href='Message.xsl'?><!DOCTYPE msgRoot SYSTEM 'MsgOutput.dtd'>");
            
            File directory = new File("C:/chatSessions");
            if(!directory.isDirectory())
                directory.mkdir();
            
            
            
            File userDirectory = new File(directory,controller.getMyUserContact().getUserName());
            if(!userDirectory.isDirectory())
                userDirectory.mkdir();
            
            File friendDirectory = new File(userDirectory,controller.getMyUserContact().getFriends().get(friendId).getUserName());
            if(!friendDirectory.isDirectory())
                friendDirectory.mkdir();
            
            File msgXsl = new File("src\\clientchat\\messageParser\\Message.xsl");
            if(!new File(directory,"Message.xsl").exists())
                copyFile(msgXsl,friendDirectory);
            
            File chatImg = new File("src\\clientchat\\messageParser\\chatBg.jpg");
            if(!new File(directory,"chatBg.jpg").exists())
                copyFile(chatImg,friendDirectory);
            
            File chatcss = new File("src\\clientchat\\messageParser\\chatMsg.css");
            if(!new File(directory,"chatMsg.css").exists())
                copyFile(chatcss,friendDirectory);
            
            File fileDtd = new File("src\\clientchat\\messageParser\\MsgOutput.dtd");
            if(!new File(directory,"MsfOutput.dtd").exists())
                copyFile(fileDtd,friendDirectory);
            
            
//            String fileName = new Date("yyyy.MM.dd_'at'_hh:mm:ss").toGMTString()+".xml";
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd_'at'_hh-mm-ss");
            String fileName = ft.format(new Date()).toString()+".xml";
            System.out.println(fileName);
            File xmlFile = new File(friendDirectory,fileName);
            
//            File xmlFile = new File("src\\clientchat\\messageParser\\MsgOutput.xml");
            if(!xmlFile.exists())
                xmlFile.createNewFile();
            jaxbMarshaller.marshal(msgRoot, xmlFile);

            jaxbMarshaller.marshal(msgRoot, System.out);

            System.out.println("java object converted to xml successfully.");

        } 
        catch (JAXBException ex) {
            ex.printStackTrace();

        } catch (IOException ex) {
            Logger.getLogger(MessageParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, TransformerException {

        List<Body> bodyList = new ArrayList<>();
        bodyList.add(new Body("Content", "me", "2017"));
        bodyList.add(new Body("Msg Second Content", "Yousef says:", "2011"));
        bodyList.add(new Body("Msg Secoffnd Content", "Ahmed says:", "2011"));
        bodyList.add(new Body("Msg Secoffnd Content", "Ahmed says:", "2011"));
        bodyList.add(new Body("Msg Secoffnd Contenfdsfsdfsdfsdfsdfsdfsdfsdfsfdt", "Ahmed says:", "2011"));
        
        List<MsgElement> msgElementList = new ArrayList<>();
        msgElementList.add(new MsgElement("To XYZ", bodyList));

        MsgRoot msgRoot = new MsgRoot("Msg Header", msgElementList);

//        List<MsgRoot> msgRootList = new ArrayList<>();
//        msgRootList.add(new MsgRoot("Msg Header", msgElementList));
        try {
            JAXBContext jAXBContext = JAXBContext.newInstance(MsgRoot.class);
            Marshaller jaxbMarshaller = jAXBContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", "<?xml-stylesheet type='text/xsl' href='Message.xsl'?>");

//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File xmlFile = new File("src\\clientchat\\messagetest\\MsgOutput.xml");

            jaxbMarshaller.marshal(msgRoot, xmlFile);


            jaxbMarshaller.marshal(msgRoot, System.out);

            System.out.println("java object converted to xml successfully.");

//            DOMSource source= new DOMSource();
//	StreamResult result= new StreamResult(xmlFile);
//	
//	TransformerFactory tf= TransformerFactory.newInstance();
//	 Transformer transformer = tf.newTransformer();
//     transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//
//	 transformer.transform(source, result);
            /*
            
            // create html page reflects xml 
              TransformerFactory tFactory = TransformerFactory.newInstance();

            
            Source xslDoc = new StreamSource("src\\messagetest\\Message.xsl");
            Source xmlDoc = new StreamSource(xmlFile);

            String outputFileName = "src\\messagetest\\catalog.html";
            OutputStream htmlFile = new FileOutputStream(outputFileName);

            Transformer transformer = tFactory.newTransformer(xslDoc);
            transformer.transform(xmlDoc, new StreamResult(htmlFile));
             */
        } //
        catch (JAXBException ex) {
            ex.printStackTrace();

        }
    }

}
