/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project1.game_model;

import java.util.List;

/**
 *
 * @author iremayvaz
 */
public class Gamer {
    public int gamerID; // oyuncu ID'si
    public List<Kart> cards; // oyuncunun bonus kazanabilmek için sahip olduğu kartlar
    public Bonus kazanilanBonus; // oyuncunun bonusu
    public int birlik_Sayisi; // oyuncunun asker sayısı
    
    
    public Gamer(int id){
        this.gamerID = id;
        // bonus birlik kazandıracak kartlar
        
        // bonus birlikler
        this.kazanilanBonus = new Bonus(this.gamerID, this.cards);
        this.birlik_Sayisi = 0; // DEFAULT BİRLİK SAYISI VERİLMELİ
    }
    
}
