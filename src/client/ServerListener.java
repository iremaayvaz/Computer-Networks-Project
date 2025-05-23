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
                System.out.println("=== CLIENT ATTACK MESAJI ALDI ===");

                ArrayList<Territory> updatedMap = (ArrayList<Territory>) incomingMessage.content;

                // Update game data first (outside SwingUtilities)
                for (Territory updatedTerritory : updatedMap) {
                    for (Territory localTerritory : Game.map.all_territories) {
                        if (localTerritory.name.equals(updatedTerritory.name)) {
                            localTerritory.totalTroop = updatedTerritory.totalTroop;
                            localTerritory.playerID = updatedTerritory.playerID;
                            localTerritory.owner = updatedTerritory.owner;
                            localTerritory.attacker = false;
                            localTerritory.defender = false;
                            break;
                        }
                    }
                }

                // Only UI updates go inside SwingUtilities
                SwingUtilities.invokeLater(() -> {
                    Game.game.haritaYerlestir(Game.map.all_territories);

                    // Debug output (optional)
                    for (Territory t : Game.map.all_territories) {
                        System.out.println(t.name + ": " + t.totalTroop + " asker, sahip: " + t.playerID);
                    }
                });

                System.out.println("=== CLIENT GÜNCELLEME TAMAMLANDI ===");
                break;
            // DURUM LABEL'INI GÜNCELLE
            case YOUR_TURN:
                //Game.secilenBolgeler.clear();
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
