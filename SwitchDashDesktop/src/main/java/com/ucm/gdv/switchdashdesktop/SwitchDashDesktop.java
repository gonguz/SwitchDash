package com.ucm.gdv.switchdashdesktop;

import com.ucm.gdv.desktop.DGame;
import com.ucm.gdv.logic.Resources;
import com.ucm.gdv.logic.states.StartState;

/**
 * main() that starts the game
 */
public class SwitchDashDesktop {


    public static void main(String[] args) {

        DGame game = new DGame();

        // Initializes the game resources
        Resources.getResources().loadResources(game.getGraphics());

        // Creates the first state and its set in the game
        StartState gameState = new StartState(game);
        game.setState(gameState);

        // Start the game loop
        game.run();

    }
}
