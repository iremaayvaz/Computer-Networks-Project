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

    public boolean moveTroops(Territory from, Territory to, int count) { // askerleri bölgelere dağıt
        if (from.totalTroop > 1
                && // Savunma için 1 kişiyi bölgesinde bırakmalı
                from.playerID == to.playerID
                && // Asker konuşlandırma, aynı bölgelerde yapılır. Rakibin bölgesini fethetmeden oraya asker konuşlandıramam.
                from.isNeighbour(to)) { // Asker konuşlandırma komşu bölgeler arasında yapılabilir.
            from.addTroops(count);
            to.removeTroops(count);
            return true;
        } else {
            return false;
        }
    }

    public boolean attack(Territory from, Territory to) { // SALDIR!
    if (from.totalTroop > 1
            && // Savunma için 1 kişiyi bölgesinde bırakmalı
            from.playerID != to.playerID
            && // Saldırı farklı oyuncular arasında yapılır
            from.isNeighbour(to)) { // Saldırı ancak komşu bölgeler arasında yapılabilir.

        // zarSonuclari null kontrolü
        if (from.owner.zarSonuclari == null) {
            from.owner.zarSonuclari = new ArrayList<>();
        }
        if (to.owner.zarSonuclari == null) {
            to.owner.zarSonuclari = new ArrayList<>();
        }

        while (from.totalTroop > 1 && to.totalTroop != 0) { // 2 taraftan birinin o bölgedeki asker sayısı 0 olana kadar saldır!
            int diceCount_p = from.howManyDices(this.willAttack);
            from.owner.zarSonuclari = Dice.rollMultiple(diceCount_p);

            // Savunma
            int diceCount_op = to.howManyDices(this.willAttack);
            to.owner.zarSonuclari = Dice.rollMultiple(diceCount_op);

            // Sonuçları karşılaştır
            ArrayList<Boolean> sonuc = Dice.compareDiceResults(from.owner.zarSonuclari, to.owner.zarSonuclari);

            // Sonuç boş değilse güncelle
            if (sonuc != null && !sonuc.isEmpty()) {
                // Karşılaştırmalara göre asker sayısı güncellenir
                Dice.updateTroops(sonuc, from.owner, to.owner);
            }

            // zarSonuclari'nı bir sonraki zar atımı için clear et
            from.owner.zarSonuclari.clear();
            to.owner.zarSonuclari.clear();
        }
        
        if(to.totalTroop == 0){ // bölge ele geçirildi mi?
            to.owner.territories.remove(to);
            from.owner.territories.add(to);
            to.owner = from.owner;
            to.playerID = from.playerID;
            to.totalTroop = from.totalTroop / 2;
            from.totalTroop = from.totalTroop / 2;
        }
        
        return true;
    } else {
        return false;
    }
}
}
