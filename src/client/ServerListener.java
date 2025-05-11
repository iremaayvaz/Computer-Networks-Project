/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import game.Game;
import game.Message;
import static game.Message.Type.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                Game.map.player.id = Integer.parseInt(incomingMessage.content.toString()); // oyuncunun id'si client id'si ile eşitlenir.
            case OPPONENT_FOUND: // Rakip bulunduysa
                Game.game.lbl_otherClient.setText(incomingMessage.content.toString()); // Rakibin ismi yazılır.
                break;
                
            // DURUM LABEL'INI GÜNCELLE
            case YOUR_TURN: 
                Game.game.lbl_state.setText(incomingMessage.content.toString());

            case OPPONENTS_TURN:
                Game.game.lbl_state.setText(incomingMessage.content.toString());
            case DICE_ROLL:
            case DICE_RESULT:
            case GAME_OVER:
        }
    }
}
