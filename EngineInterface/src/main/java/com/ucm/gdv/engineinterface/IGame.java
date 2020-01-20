package com.ucm.gdv.engineinterface;

/**
 *
 * Interface that wraps everythig else. Stores the input and graphic objects
 */
public interface IGame {

    /**
     *
     * @return the graphics object
     */
    IGraphics getGraphics();

    /**
     *
     * @return the input object
     */
    IInput getInput();

    /**
     * Loop for the game
     */
    void update();

    /**
     * Render loop
     */
    void render();

    /**
     * Pauses the game
     */
    void pause();

    /**
     * Resumes the game
     */
    void resume();

    /**
     * Establish the current state of the game
     *
     * @param gameState that will be showed
     */
    void setState(IGameState gameState);
}