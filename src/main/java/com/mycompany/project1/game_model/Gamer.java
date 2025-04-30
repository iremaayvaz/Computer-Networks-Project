/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project1.game_model;

import java.util.ArrayList;

/**
 *
 * @author iremayvaz
 */
public class Gamer {

    private String gamerID;
    private int totalTroops; // birlik sayısı
    private String color;
    private ArrayList<Territory> territories; // topraklar(ülkeler)
    private int totalTerritory; // sahip olunan toprak sayısı

    public Gamer(String id, String color) {
        this.gamerID = id;
        this.totalTroops = 0;
        this.color = color;
        this.territories = new ArrayList<>();
        this.totalTerritory = 0;
    }

    public void addTerritory(Territory takenTerritory) { // If we win the roll-a-dice session,
        this.getTerritories().add(takenTerritory);       // then add the territory to our territories
    }

    public void removeTerritory(Territory takenTerritory) { // If we lose the roll-a-dice session,
        this.getTerritories().remove(takenTerritory);       // then remove the territory from our territories
    }

    /**
     * @return the gamerID
     */
    public String getGamerID() {
        return gamerID;
    }

    /**
     * @return the totalTroops
     */
    public int getTotalTroops() {
        return totalTroops;
    }

    /**
     * @param totalTroops the totalTroops to set
     */
    public void setTotalTroops(int totalTroops) {
        this.totalTroops = totalTroops;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @return the territories
     */
    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    /**
     * @return the totalTerritory
     */
    public int getTotalTerritory() {
        return totalTerritory;
    }

    /**
     * @param totalTerritory the totalTerritory to set
     */
    public void setTotalTerritory(int totalTerritory) {
        this.totalTerritory = totalTerritory;
    }
    
    public void addTroops(int count){
        this.totalTroops += count;
    }

    public void removeTroops(int count){
        this.totalTroops -= count;
    }
    
}
