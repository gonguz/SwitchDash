package com.ucm.gdv.engineinterface;

/**
 * Interface for implementing the game's logic in order to separate the different parts of the game
 */
public interface IGameState {

    /**
     * Initializes the state
     */
    void init();

    /**
     * Loop for the game state within the logic will be updated
     *
     * @param elapsedTime time elapsed between last time
     */
    void update(double elapsedTime);

    /**
     * Renders the current frame of the game
     */
    void render();

    /**
     * Used for managing the game input
     */
    void handleInput();

    /**
     * Resize the logic space
     *
     * @param width new window width
     * @param height new window height
     */
    void resize(int width, int height);
}
