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
public class Territory {

    private String name;
    private Gamer gamer;
    private int totalTroop;
    private ArrayList<Territory> neighbours;

    public Territory(String name) {
        this.name = name;
        this.totalTroop = 0;
        this.neighbours = new ArrayList<>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the gamer
     */
    public Gamer getGamer() {
        return gamer;
    }
    
    /**
     * @param gamer the gamer to set
     */
    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
        gamer.addTerritory(this); // Territory is added to new owner's territory list.
    }

    /**
     * @return the totalTroop
     */
    public int getTotalTroop() {
        return totalTroop;
    }

    /**
     * @param totalTroop the totalTroop to set
     */
    public void setTotalTroop(int totalTroop) {
        this.totalTroop = totalTroop;
    }

    /**
     * @return the neighbours
     */
    public ArrayList<Territory> getNeighbours() {
        return neighbours;
    }

    
    public void addNeighbours(Territory newNeighbour){
        this.neighbours.add(newNeighbour);
    }
    
    public void increaseTroops(int count){ // Different territories brings different number of troops
        this.totalTroop += count;
    }
    
    public void decreaseTroops(int count){ // Different territories loses different number of troops
        this.totalTroop -= count;
    }
    
}
