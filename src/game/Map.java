/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Harita
 *
 * @author iremayvaz
 */
public class Map {
    
    public int defaultPlayerTroop = 9;
    public int defaultOpponentTroop = 9;

    public static ArrayList<Territory> all_territories; // Haritadaki fethedilecek bölgeler
    public static Player player;
    public static Player opponent;

    public Map() {
        this.all_territories = new ArrayList<>();
    }

    public void addTerritory(Territory territory) {
        this.all_territories.add(territory);
    }

    /*public void isOpponentFound(ArrayList<SClient> gamers) {
        for (int i = 0; i < gamers.size(); i++) {
            if (gamers.get(i).getOyuncu().getGamerID() != this.player.getGamerID()) {
                this.setOpponent(gamers.get(i).getOyuncu());
                break;
            }
        }
    }*/

    public void defaultTerritories() { // Oyun başlamadan oyunculara yer dağıt.
        Collections.shuffle(all_territories); // Bölgeler listesini karıştır

        for (int i = 0; i < all_territories.size(); i++) { // karıştırılan listenin 
            if (i < 3) { // ilk 3'ü player'a
                all_territories.get(i).setGamer(player); // bölgenin oyuncusuna hem oyuncuyu ekler hem de oyuncunun bölge listesine ekler
            } else {
                all_territories.get(i).setGamer(opponent); 
            }

        }

    }
    
    public void defaultTroops(){ // Oyun başlamadan oyuncuların askerlerini dağıt
        for(int i = 0; i < 3; i++){
            player.territories.get(i).addTroops(1); // Her bölgede en az 1 asker olsun
            this.defaultPlayerTroop--; // olması gereken toplam asker sayısı azaltılır.
            opponent.territories.get(i).addTroops(1); // Her bölgede en az 1 asker olsun
            this.defaultOpponentTroop--;
        }
        
        Random rand = new Random();
        
        while (this.defaultPlayerTroop > 0) { // Player'ın askerlerini bölgelerine dağıt
            int i = rand.nextInt(3); // 3 bölgeye rastgele birer tane asker ekler
            player.territories.get(i).addTroops(1);
            this.defaultPlayerTroop--;
        }
        
        while (this.defaultOpponentTroop > 0) { // Opponent'in askerlerini bölgelerine dağıt
            int i = rand.nextInt(3); 
            opponent.territories.get(i).addTroops(1);
            this.defaultOpponentTroop--;
        }
    }

    public static Player getOpponent() {
        return opponent;
    }

    public static void setOpponent(Player newOpponent) {
        opponent = newOpponent;
    }
}
