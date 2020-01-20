package com.ucm.gdv.logic.managers;

import java.util.Random;

/**
 * Class that manage the game: points, best scores,...
 */


public class GameManager {

    // Instance for the GameManager
    private static GameManager _gameManager;

    // Current points
    private int _points;

    // Variables used for managing the maximum score achieved
    private int _bestScore;
    private boolean _newBest;

    // Used for checking if it is the first time going to the HELP STATE
    private boolean _firstTime;

    // Used for managing the balls' velocity
    private int _ballsVelocity;

    // Used for managing the arrows' velocity
    private int _arrowsVelocity;

    // Constants for the balls and arrows velocity
    private final int INITIAL_BALLS_VELOCITY = 430;
    private final int INITIAL_ARROWS_VELOCITY = 384;

    // Used for retrieving a random color for the background
    private int _backgroundColor;

    private GameManager() {

        _points = 0;
        _firstTime = true;
        _ballsVelocity = INITIAL_BALLS_VELOCITY;
        _arrowsVelocity = INITIAL_ARROWS_VELOCITY;
        _newBest = true;
        Random randomGenerator = new Random();
        _backgroundColor = randomGenerator.nextInt(9);

    }

    public static GameManager getGameManager(){

        if(_gameManager == null){
            _gameManager = new GameManager();
        }

        return _gameManager;
    }

    public void addPoints(){

        _points++;
    }

    public int getPoints() {

        return _points;
    }

    public int getBestScore() {

        return _bestScore;
    }

    public void resetPoints() {

        _points = 0;
    }

    public void checkNewBest() {

        if(_points > _bestScore) {
            _bestScore = _points;
            _newBest = true;
        }
        else {
            _newBest = false;
        }
    }

    public boolean isNewBest() {

        return _newBest;
    }

    public void hasPlayed() {

        _firstTime = false;
    }

    public boolean isFirstTimePlaying() {

        return _firstTime;
    }


    public void endGame() {

        resetPoints();
        resetBallsVelocity();

    }

    public int getBallsVelocity() {

        return _ballsVelocity;
    }

    public void increaseBallsVelocity() {

        _ballsVelocity += 90;
        //System.out.println("INCREASE BALLS VELOCITY " +  GameManager.getGameManager().getBallsVelocity());
    }

    private void resetBallsVelocity() {

        _ballsVelocity = INITIAL_BALLS_VELOCITY;
    }

    public int getArrowsVelocity() {

        return _arrowsVelocity;
    }

    public void increaseArrowsVelocity() {

        if(_arrowsVelocity <= 1000)
            _arrowsVelocity += 5;

    }

    public void resetArrowsVelocity() {

        _arrowsVelocity = INITIAL_ARROWS_VELOCITY;
    }


    public void resetBackGroundColor(){
        Random randomGenerator = new Random();
        _backgroundColor = randomGenerator.nextInt(9);
    }

    public int getBackGroundColor(){
        return _backgroundColor;
    }


}
