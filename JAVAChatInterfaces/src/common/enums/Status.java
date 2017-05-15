/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.enums;

/**
 *
 * @author Yousef
 */
public enum Status {
    ONLINE(1,"Online"),AWAY(2,"Away"),BUSY(3,"Busy"),OFFLINE(4,"Offline");
    
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private Status(int id,String name){  
        this.id = id;
        this.name = name;
    }  
}
