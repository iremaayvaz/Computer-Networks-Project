/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 * Haritadaki bölgeler
 *
 * @author iremayvaz
 */
public class Territory implements java.io.Serializable {

    public String name; // bolge ismi
    public int totalTroop; // bolgedeki asker sayısı
    public ArrayList<Territory> neighbours; // komsulari

    public JButton bolge_butonu;
    public Player owner; // zar sonuclarina ulasalim
    public int playerID;
    
    public boolean attacker; // saldıran
    public boolean defender; // savunan

    public Territory(String name) {
        this.name = name;
        this.totalTroop = 0;
        this.neighbours = new ArrayList<>();
        this.attacker = false;
        this.defender = false;
    }

    // JToggleButton set et
    public void setButton(JButton btn) {
        this.bolge_butonu = btn;
    }

    // JFrame'deki asker sayıları güncellemesi için
    public JButton getBolge_butonu() {
        return bolge_butonu;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
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
    }

    public void removeTroops(int count) { // Different territories loses different number of troops
        this.totalTroop -= count;
    }

    public int howManyDices(boolean willAttack) { // O bölgedeki total asker sayısına göre zar sayısı belirle
        int diceCount = 0;

        if (!willAttack) { // saldırmıyoruz, savunuyoruz

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

    public boolean attack(Territory to) { // SALDIR!
        int turSayisi = 0;
        if (this.totalTroop > 1
                && // Savunma için 1 kişiyi bölgesinde bırakmalı
                this.playerID != to.playerID
                && // Saldırı farklı oyuncular arasında yapılır
                this.isNeighbour(to)) {  // Saldırı ancak komşu bölgeler arasında yapılabilir.

            // zarSonuclari null kontrolü
            if (this.owner.zarSonuclari == null) {
                this.owner.zarSonuclari = new ArrayList<>();
                System.out.println("sal icin zar sonuclari listesi");
            }
            if (to.owner.zarSonuclari == null) {
                to.owner.zarSonuclari = new ArrayList<>();
                System.out.println("sav icin zar sonuclari listesi");
            }

            //while (this.totalTroop != 0 && to.totalTroop != 0) { // 2 taraftan birinin o bölgedeki asker sayısı 0 olana kadar saldır!
            // saldıran
            int diceCount_p = this.howManyDices(this.owner.willAttack);
            this.owner.zarSonuclari = Dice.rollMultiple(diceCount_p);
            System.out.println("sal " + this.name + " : " + this.totalTroop);
            System.out.println();
            for (int i : this.owner.zarSonuclari) {
                System.out.print(i + " :");
            }

            // Savunma
            int diceCount_op = to.howManyDices(to.owner.willAttack);
            to.owner.zarSonuclari = Dice.rollMultiple(diceCount_op);
            System.out.println("sav " + to.name + " : " + to.totalTroop);
            System.out.println();
            for (int i : to.owner.zarSonuclari) {
                System.out.print(i + " :");
            }

            // Sonuçları karşılaştır
            ArrayList<Boolean> sonuc = Dice.compareDiceResults(this.owner.zarSonuclari, to.owner.zarSonuclari);
            System.out.println();
            for (boolean i : sonuc) {
                System.out.print(i + " :");
            }
            System.out.println();
            System.out.println("zar karşılaştıralım");

            // Sonuç boş değilse güncelle
            if (sonuc != null && !sonuc.isEmpty()) {
                // Karşılaştırmalara göre asker sayısı güncellenir
                Dice.updateTroops(sonuc, this, to);
                System.out.println("asker sayılarını guncelledim");
                System.out.println("guncel sal " + this.name + " : " + this.totalTroop);
                System.out.println("guncel sav " + to.name + " : " + to.totalTroop);
            } else {
                System.out.println("sonuc bos");
            }

            // zarSonuclari'nı bir sonraki zar atımı için clear et
            this.owner.zarSonuclari.clear();
            to.owner.zarSonuclari.clear();
            turSayisi++;
            //}

            System.out.println("tur sayısı: " + turSayisi);
            if (to.totalTroop == 0) { // bölge ele geçirildi mi?
                to.owner.territories.remove(to);
                this.owner.territories.add(to);
                to.owner = this.owner;
                to.playerID = this.playerID;
                to.totalTroop = this.totalTroop / 2;
                this.totalTroop = this.totalTroop - to.totalTroop;
            }

            return true;
        } else if (this.totalTroop < 1) {
            System.out.println("asker sayısı 1'den az");
            return false;
        } else if (this.playerID == to.playerID) {
            System.out.println("kendime mi saldırıcam?");
            return false;
        } else if (!this.isNeighbour(to)) {
            System.out.println("komşum değil");
            return false;
        } else {
            System.out.println("saldırılamadı!");
            return false;
        }
    }
}
