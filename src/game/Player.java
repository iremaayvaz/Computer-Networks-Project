/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import static game.Dice.*;
import static game.Map.*;
import java.util.ArrayList;

/**
 * Oyuncu
 *
 * @author iremayvaz
 */
public class Player {

    /**
     * Oyuncu adını girdi bağlandı 
     * JTextField Login_page server ID verdi 
     * Server - Sclient 
     * rastgele bölge ataması (MAP CLASS TARAFINDAN) defaultTerritories 
     * rastgele asker yerleşimi (oyun başlangıcında) defaultTroops 
     * saldıran bölge ve saldırılacak bölge seçilsin 
     * JButtonlarla saldır attack 
     * saldırılan bölgedeki asker sayılarına göre zar at 
     * zar sonuclarina bak 
     * sonuclara göre asker sayılarını güncelle 
     * güncellenen sayılarla 0'a ulasmadiysa bir taraf
     * güncel sayılara göre yeniden zar at hangi taraf 0 olduysa kaybetti
     * kazanan tarafın askeri 2 ye bölünür oyuncu skip yapar karşı oyuncuya
     * geçer total 9 asker olacak her taraf için
     *
     */
    public int id;
    public String name;
    public int totalTroops; // birlik sayısı
    public ArrayList<Territory> territories; // topraklar(ülkeler)
    public ArrayList<Integer> zarSonuclari; // her zar atımı sonrası sonuçlar tutulur

    public boolean willAttack; // saldırılacak mı
    public boolean isYourTurn; // sıra benim mi

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.totalTroops = 0;
        this.territories = new ArrayList<>();
        this.willAttack = false;
        this.isYourTurn = false;
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

    public boolean isMapTaken() { // Check the finishing condition
        if (this.totalTroops == all_territories.size()) { // If total number of all_territories in map equals with
            return true;                           // gamer's total number of all_territories
        } else {
            return false;
        }
    }

    public boolean moveTroops(Territory from, Territory to, int count) { // askerleri bölgelere dağıt
        if (from.totalTroop > 1
                && // Savunma için 1 kişiyi bölgesinde bırakmalı
                from.player == to.player
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
                from.player != to.player
                && // Saldırı farklı oyuncular arasında yapılır
                from.isNeighbour(to)) { // Saldırı ancak komşu bölgeler arasında yapılabilir.
            // Saldıran
            from.player.willAttack = true;

            while (from.totalTroop != 0 && to.totalTroop != 0) { // 2 taraftan birinin o bölgedeki asker sayısı 0 olana kadar saldır!
                int diceCount_p = from.howManyDices();
                from.player.zarSonuclari = rollMultiple(diceCount_p);

                // Savunma
                int diceCount_op = to.howManyDices();
                to.player.zarSonuclari = rollMultiple(diceCount_op);

                // Sonuçları karşılaştır
                ArrayList<Boolean> sonuc = compareDiceResults(from.player.zarSonuclari, to.player.zarSonuclari);

                // Karşılaştırmalara göre asker sayısı güncellenir
                updateTroops(sonuc);

                // zarSonuclari'nı bir sonraki zar atımı için clear et
                from.player.zarSonuclari.clear();
                to.player.zarSonuclari.clear();
            }
            return true;
        } else {
            return false;
        }
    }
}
/**
 * butonlar 1 askere sahipse oralara basılmamalı
 * ilk basılan butonun sahibine gore komsu
 * 
 */
