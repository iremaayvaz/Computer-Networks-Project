/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import game.Game;
import game.Login;
import game.Message;
import static game.Message.Type.*;
import game.Player;
import game.Map;
import game.Territory;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * Server'dan gelen mesajları dinler Client tarafı
 *
 * @author iremayvaz
 */
public class ServerListener extends Thread {

    public void run() {
        while (!Client.csocket.isClosed()) {
            try {
                Message in_message = (Message) Client.cinput.readObject();
                this.handleIncomingMessage(in_message);

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void handleIncomingMessage(Message incomingMessage) throws IOException {
        switch (incomingMessage.message_type) {
            case YOUR_ID:
                // oyuncunun id'si client id'si ile eşitlenir.
                Game.oyuncu = new Player();
                Game.oyuncu.id = (int) incomingMessage.content;
                Game.oyuncu.name = Login.login.txt_gamer_name.getText();
                Client.SendMessageToServer(new Message(Message.Type.NAME, "")); // Server'dan client id'si istiyoruz.
                break;
            case OPPONENT_FOUND: // Rakip bulunduysa
                System.out.println("Rakip bulundu, harita oluşturuluyor...");
                String[] veri = incomingMessage.content.toString().split("#");
                Game.rakip = new Player();
                Game.rakip.id = Integer.parseInt(veri[0]);
                Game.rakip.name = veri[1];

                Login.opponentFound = true; // Login'den Game'e
                break;
            case MAP:
                Map mapInfo = (Map) incomingMessage.content;
                Game game = new Game(mapInfo);
                game.setVisible(true);
                Game.game.lbl_localClient.setText(Game.oyuncu.name);
                Game.game.lbl_otherClient.setText(Game.rakip.name);
                Login.login.dispose();
                break;
            case ATTACK:
                ArrayList<Territory> gelenSaldiriBilgileri = (ArrayList<Territory>) incomingMessage.content;
                Territory saldiran = gelenSaldiriBilgileri.get(0);
                Territory savunan = gelenSaldiriBilgileri.get(1);

                Territory from = Game.map.all_territories.stream()
                        .filter(t -> t.name.equals(saldiran.name))
                        .findFirst()
                        .get();

                Territory to = Game.map.all_territories.stream()
                        .filter(t -> t.name.equals(savunan.name))
                        .findFirst()
                        .get();

                // Asker sayılarını güncelle
                from.totalTroop = saldiran.totalTroop;
                to.totalTroop = savunan.totalTroop;

                // Sahiplik bilgisini de güncelle (yeni eklenen kod)
                to.playerID = savunan.playerID;
                to.owner = savunan.playerID == Game.oyuncu.id ? Game.oyuncu : Game.rakip;

                JButton saldiranButon = Game.game.getTerritoryByButton(saldiran.name);
                JButton savunanButon = Game.game.getTerritoryByButton(savunan.name);

                SwingUtilities.invokeLater(() -> {
                    // Asker sayılarını güncelle
                    saldiranButon.setText(String.valueOf(from.totalTroop));
                    savunanButon.setText(String.valueOf(to.totalTroop));

                    // Renkleri güncelle
                    if (from.playerID == Game.oyuncu.id) {
                        saldiranButon.setForeground(Color.GREEN);
                    } else {
                        saldiranButon.setForeground(Color.RED);
                    }

                    // Burada savunan bölgenin sahipliği değişmiş olabilir, ona göre renk ver
                    if (to.playerID == Game.oyuncu.id) {
                        savunanButon.setForeground(Color.GREEN);
                    } else {
                        savunanButon.setForeground(Color.RED);
                    }
                });
                break;
            // DURUM LABEL'INI GÜNCELLE
            case YOUR_TURN:
                Game.secilenBolgeler.clear();
                Game.isYourTurn = true;
                Game.oyuncu.willAttack = true;
                Game.rakip.willAttack = false;
                Game.game.lbl_state.setText(incomingMessage.content.toString());
                break;

            case OPPONENTS_TURN:
                Game.isYourTurn = false;
                Game.oyuncu.willAttack = false;
                Game.rakip.willAttack = true;
                Game.game.lbl_state.setText(incomingMessage.content.toString());
                break;
            case DISCONNECT:
                break;
            case GAME_OVER:
            //endGame

        }
    }
}
