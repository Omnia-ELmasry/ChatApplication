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
public enum Gender {
    MALE(1,"Male"),FEMALE(2,"Female");
    
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    private Gender(int id,String name){  
        this.id = id;
        this.name = name;
    } 
    
}
