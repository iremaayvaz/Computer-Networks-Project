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
public class Map {

    private ArrayList<Territory> territories;
    private static int totalTerritory = 0;

    public Map() {
        this.territories = new ArrayList<>();
    }

    public void addTerritory(Territory territory) {
        this.territories.add(territory);
    }

    public boolean isMapTaken(Gamer gamer) { // Check the finishing condition
        if (gamer.getTotalTerritory() == this.totalTerritory) { // If total number of territories in map equals with
            return true;                                        // gamer's total number of territories
        } else {
            return false;
        }
    }
}
