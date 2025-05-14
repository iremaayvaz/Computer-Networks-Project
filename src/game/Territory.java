/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JToggleButton;

/**
 * Haritadaki bölgeler
 *
 * @author iremayvaz
 */
public class Territory implements java.io.Serializable {

    public String name;
    public int playerID;
    public int totalTroop;
    public ArrayList<Territory> neighbours;
    public JToggleButton bolge_butonu;

    public Territory(String name) {
        this.name = name;
        this.totalTroop = 0;
        this.neighbours = new ArrayList<>();
    }
    
    // JToggleButton set et
    public void setButton(JToggleButton btn){
        this.bolge_butonu = btn;
    }
    
    // Bölgenin oyuncusunu ekleme aynı anda oyuncunun da bölgesini ekleme.
    public void setPlayerID(int id) {
        this.playerID = id;
        //oyuncu.addTerritory(this); // Territory is also added to new owner's territory list.
    }

    // Komşuluk ilişkileri
    public void addNeighbours(Territory newNeighbour) {
        this.neighbours.add(newNeighbour);
    }

    public boolean isNeighbour(Territory other) {
        return this.neighbours.contains(other);
    }

    // Yeniden asker konuşlandırma için
    public void addTroops(int count) { // Different territories brings different number of troops
        this.totalTroop += count; // Bölgedeki toplam asker sayısı güncellenir
        //this.player.totalTroops += count; // Bölgenin oyuncusunun asker sayısı güncellenir.
    }

    public void removeTroops(int count) { // Different territories loses different number of troops
        this.totalTroop -= count;
        //this.player.totalTroops -= count;
    }
    
    public int howManyDices(boolean willAttack) { // O bölgedeki total asker sayısına göre zar sayısı belirle
        int diceCount = 0;

        if (willAttack) { // saldırmıyoruz, savunuyoruz

            if (this.totalTroop == 1) {
                diceCount = 1;
            } else {
                diceCount = 2;
            }

        } else { // saldırıyoruz
            if (this.totalTroop == 1) {
                diceCount = 0;
            } else if (this.totalTroop == 2) {
                diceCount = 1;
            } else if (this.totalTroop == 3) {
                diceCount = 2;
            } else {
                diceCount = 3;
            }
        }
        return diceCount;
    }
}
