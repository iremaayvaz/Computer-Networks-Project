/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project1;

import java.net.InetAddress;

/**
 *
 * @author iremayvaz
 */
public class Bonus {
    
    public String id;
    public int agir_silahlar;
    public int suvariler;
    public int piyadeler;
    
    public Bonus(InetAddress ip, int port){
        this.id = (ip + ":" + port).toString();
        this.agir_silahlar = 0;
        this.piyadeler = 0;
        this.suvariler = 0;
    }
    
    
}
