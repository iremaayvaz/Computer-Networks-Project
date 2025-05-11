/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 * 
 * Neden Serializable tabanlı da String tabanlı değil?
 * Çünkü içerik her şey olabilir. Projemiz 2 kişilik bir "oyun" projesi
 * Oyun strateji oyunu ve çok veri iletimi gerekebilir.
 *
 * @author iremayvaz
 */
public class Message implements java.io.Serializable {
    
    public enum Type // Mesaj türleri
    {
        /**
         * Login yapınca isimle server'a bilgi verilebilir
         * 2. login olunca 2 tarafa da opponent found mesajı verilebilir?
         */
        JOIN_SERVER, // Sunucuya katılma isteği
        DISCONNECT, // Bağlantı koptu
        YOUR_ID,
        OPPONENT_FOUND, // Rakip bulundu, oyun başlasın
        
        SKIP_TURN, // Sıra pas geçme
        YOUR_TURN,
        OPPONENTS_TURN,
        DICE_ROLL, // Zar atma 
        DICE_RESULT, // Zar atış sonucu
        GAME_OVER // Oyun sonu
    }
    
    public Type message_type;
    public Object content;
    
    public Message(Type tur, Object mesaj){
        this.message_type = tur;
        this.content = mesaj;
    }
}
