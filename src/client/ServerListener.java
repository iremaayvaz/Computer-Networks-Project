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
import game.Territory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Server'dan gelen mesajları dinler 
 * Client tarafı
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
                String[] ids = incomingMessage.content.toString().split("#");
                Game.oyuncu.id = Integer.parseInt(ids[0]);
                Game.rakip.id = Integer.parseInt(ids[1]);
                Client.SendMessageToServer(new Message(Message.Type.NAME, "")); // Server'dan client id'si istiyoruz.
                break;
            case OPPONENT_FOUND: // Rakip bulunduysa
                System.out.println("Rakip bulundu, harita oluşturuluyor...");
                Login.opponentFound = true; // Login'den Game'e
                break;
            case MAP:
                ArrayList<Territory> mapInfo = (ArrayList<Territory>) incomingMessage.content;
                Game game = new Game(mapInfo);
                game.setVisible(true);
                Login.login.dispose();
                
                Client.SendMessageToServer(new Message(Message.Type.YOUR_ID, "")); // Server'dan client id'si istiyoruz.
                
            // DURUM LABEL'INI GÜNCELLE
            case YOUR_TURN:
                Game.game.lbl_state.setText(incomingMessage.content.toString());
                break;

            case OPPONENTS_TURN:
                Game.game.lbl_state.setText(incomingMessage.content.toString());
                break;
            case DISCONNECT:
                break;
            case GAME_OVER:
            //endGame

        }
    }
}
