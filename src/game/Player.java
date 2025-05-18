/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import static game.Dice.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Oyuncu
 *
 * @author iremayvaz
 */
public class Player implements java.io.Serializable {
    
    public int id;
    public String name;
    public int totalTroops; // birlik sayısı
    public ArrayList<Territory> territories; // topraklar(ülkeler)
    public ArrayList<Integer> zarSonuclari; // her zar atımı sonrası sonuçlar tutulur

    public boolean willAttack; // saldırılacak mı
    
    public Player() {
        this.totalTroops = 0;
        this.territories = new ArrayList<>();
        this.zarSonuclari = new ArrayList<>();
        this.willAttack = false;
    }
    
    public Player(String name, int id) {
        this.id = id;
        this.name = name;
        this.totalTroops = 0;
        this.territories = new ArrayList<>();
        this.zarSonuclari = new ArrayList<>();
        this.willAttack = false;
    }

    public void addTerritory(Territory takenTerritory) { // If we win the roll-a-dice session,
        this.territories.add(takenTerritory);       // then add the territory to our all_territories
    }

    public void removeTerritory(Territory takenTerritory) { // If we lose the roll-a-dice session,
        this.territories.remove(takenTerritory);       // then remove the territory from our all_territories
    }

    public void addTroops(int count) { // Zar sonucunu göre birlik ekle
        this.totalTroops += count;
    }

    public void removeTroops(int count) { // Zar sonucunu göre birlik çıkar
        this.totalTroops -= count;
    }

    /*public boolean isMapTaken() { // Check the finishing condition
        if (this.totalTroops == all_territories.size()) { // If total number of all_territories in map equals with
            return true;                           // gamer's total number of all_territories
        } else {
            return false;
        }
    }*/
}