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
 * Server'dan gelen mesajları dinler
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

            case JOIN_SERVER:
            case DISCONNECT:
            case YOUR_ID:

            case OPPONENT_FOUND: // Rakip bulunduysa
                Game.game.lbl_otherClient.setText(incomingMessage.content.toString()); // Rakibin ismi yazılır.
                break;
            case SKIP_TURN:
            case DICE_ROLL:
            case DICE_RESULT:
            case GAME_OVER:
        }
    }
}
