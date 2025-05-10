/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import static game.Map.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Zar
 *
 * @author iremayvaz
 */
public class Dice {

    /**
     * Zarı at rollMultiple 
     * Attığın zarların sonuçlarını tut 
     * Rakiple zar sonuclarini karşılaştır compareDiceResults 
     * Zar sonuclarina gore askerler azalsın updateTroops 
     * Eğer sonuçtan sonra asker sayısı 0a ulaşmadıysa
     * if(player.totalTroops != 0 && opponent.totalTroops != 0) 
     * Yeni zar sayıları kadar howManyDices 
     * zar at rollMultiple
     */
    public static int roll() { // Zarı at
        return new Random().nextInt(6) + 1;
    }

    public static ArrayList<Integer> rollMultiple(int troopsCount) { // Birlik sayısına göre zar at
        ArrayList<Integer> results = new ArrayList<>(); // zar sonuçlarını tutmak için arrayList

        for (int i = 0; i < troopsCount; i++) { 
            results.add(roll());
        }

        Collections.sort(results); // arrayList'i küçükten büyüğe sıralar
        
        // arrayList'i büyükten küçüğe çevir
        for (int i = 0; i < results.size() / 2; i++) {
            int temp = results.get(i);
            results.set(i, results.get(results.size() - 1 - i));
            results.set(results.size() - 1 - i, temp);
        }

        return results;
    }

    public static ArrayList<Boolean> compareDiceResults(ArrayList<Integer> gamer_results, ArrayList<Integer> opponent_results) { // Zar sonuçlarını karşılaştır
        ArrayList<Boolean> diceResults = new ArrayList<>();

        if (gamer_results.size() >= opponent_results.size()) { // gamer eşit veya daha fazla zar attıysa

            for (int i = 0; i < opponent_results.size(); i++) { // sonuçları sırayla karşılaştır
                if (opponent_results.get(i) >= gamer_results.get(i)) { // opponent'in sonucu daha fazlaysa veya eşitse
                    diceResults.set(i, false); // kaybettik
                } else {
                    diceResults.set(i, true); // kazandik
                }
            }

            return diceResults;

        } else { // opponent daha fazla zar attıysa

            for (int i = 0; i < gamer_results.size(); i++) { // sonuçları sırayla karşılaştır
                if (opponent_results.get(i) < gamer_results.get(i)) { // gamer'ın sonucu daha fazlaysa
                    diceResults.set(i, true);  // kazandik
                } else {
                    diceResults.set(i, false); // kaybettik
                }
            }

            return diceResults;

        }
    }

    public static void updateTroops(ArrayList<Boolean> dice_results) { // Zar sonuçlarının karşılaştırmalarına göre asker sayılarını güncelle
        for (int i = 0; i < dice_results.size(); i++) {
            if (player.totalTroops != 0 && opponent.totalTroops != 0) {
                switch (dice_results.get(i)) {
                    case true:
                        opponent.totalTroops--;
                        break;
                    case false:
                        player.totalTroops--;
                        break;
                }
            }
        }
    }

}
