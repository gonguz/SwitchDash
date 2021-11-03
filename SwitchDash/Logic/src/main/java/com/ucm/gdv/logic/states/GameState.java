package com.ucm.gdv.logic.states;

import com.ucm.gdv.engineinterface.IGame;
import com.ucm.gdv.engineinterface.IGameState;
import com.ucm.gdv.engineinterface.IGraphics;
import com.ucm.gdv.engineinterface.IInput;
import com.ucm.gdv.logic.Button;
import com.ucm.gdv.logic.Resources;
import com.ucm.gdv.logic.Screen;
import com.ucm.gdv.logic.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for the states that implements common methods for the states
 */

public abstract class GameState implements IGameState {


    protected IGame _game;
    protected IGraphics _graphics;
    protected IInput _input;

    protected Screen _screen;


    protected float _alphaTapToPlay;
    protected float _alphaBackground;

    private enum FADE_STATE {FADE_OUT, FADE_IN};

    protected FADE_STATE _fade_tapToPlay_state;
    protected FADE_STATE _fade_background_state;


    protected enum STATES {NONE, START, HELP, PLAY, GAME_OVER};
    protected STATES _state;

    public class Alpha {

        public Alpha(FADE_STATE s, float a){
            state = s;
            alpha = a;
        }

        FADE_STATE state;
        float alpha;
    }

    protected Sprite _tapToPlay;
    private Sprite _whiteFlash;

    protected boolean _stateChange;
    protected boolean _flashBackgroundFinished;
    protected boolean _initialized;

    protected List<Button> _buttons;


    // Variables for scaling the game to the resolution, these are the ideal dimensions for the game
    // and will adjust it to the ideal aspect ratio
    private static final int VIRTUAL_WIDTH = 1080;
    private static final int VIRTUAL_HEIGHT = 1920;
    private static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;


    // Sprites constants for the positions
    protected final int _BUTTONS = 30;


    private int _lastWidth;
    private int _lastHeight;




    public GameState(IGame game){
        _game = game;
        //init();

    }

    @Override
    public void init() {

        _graphics = _game.getGraphics();
        _input = _game.getInput();

        _buttons = new ArrayList<>();

        _tapToPlay = new Sprite(Resources.getResources().getImage("tapToPlay"),
                Resources.getResources().getImageProperties("tapToPlay")[0], Resources.getResources().getImageProperties("tapToPlay")[1], 0, 0);


        _fade_tapToPlay_state = FADE_STATE.FADE_OUT;

        _fade_background_state = FADE_STATE.FADE_IN;

        _alphaTapToPlay = 1.0f;
        _alphaBackground = 0.0f;

        _whiteFlash = new Sprite(Resources.getResources().getImage("white"),
                Resources.getResources().getImageProperties("white")[0], Resources.getResources().getImageProperties("white")[1], 0, 0);

        _stateChange = false;
        _flashBackgroundFinished = false;
        _initialized = false;

        _state = STATES.NONE;

        _screen = new Screen(_graphics, _graphics.getWidth(), _graphics.getHeight());

       resize(_graphics.getWidth(), _graphics.getHeight());

        //Screen.getScreen(_graphics);

        _lastWidth = 0;
        _lastHeight = 0;
    }


    @Override
    public void update(double elapsedTime) {


        if(_lastWidth != _graphics.getWidth() || _lastHeight != _graphics.getHeight()) {

            resize(_graphics.getWidth(), _graphics.getHeight());

            _lastWidth = _graphics.getWidth();
            _lastHeight = _graphics.getHeight();

            _screen.updateArrowsInitialPos();

            clearButtonsList();
            _initialized = false;
       }

        Alpha a1 = changeAlpha((float) elapsedTime, _fade_tapToPlay_state, 1.0f, _alphaTapToPlay);

        _alphaTapToPlay = a1.alpha;
        _fade_tapToPlay_state = a1.state;

        if(_stateChange) {

            Alpha a2 = changeAlpha((float) elapsedTime, _fade_background_state, 15.0f, _alphaBackground);

            _alphaBackground = a2.alpha;
            _fade_background_state = a2.state;

            if(_alphaBackground >= 1.0f){
                _flashBackgroundFinished = true;
            }
        }

        //Screen.getScreen(_graphics).update(elapsedTime);
        _screen.update(elapsedTime);
    }

    @Override
    public void render() {

        if(_buttons.size() > 0)
            drawButtons(_buttons);

        //Screen.getScreen(_graphics).render();
        _screen.render();

        _graphics.clearCrop(0, 0,  0, _graphics.getWidth(), (int)_graphics.getCrop()[1]);
        _graphics.clearCrop(0, 0,  (int)((float) _screen.getHeight() + _graphics.getCrop()[1]), _graphics.getWidth(),
                _graphics.getHeight() - (int)((float) _screen.getHeight() + _graphics.getCrop()[1]));
    }



    @Override
    public void resize(int width, int height) {

        // calculate new viewport
        float aspectRatio = (float)width/(float)height;

        // It will change depending on the new dimensions
        float scale;

        float [] crop = new float[2];

        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)VIRTUAL_HEIGHT;
            crop[0] = (width - VIRTUAL_WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
            crop[1] = (height - VIRTUAL_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }

        float w = (float)VIRTUAL_WIDTH * scale;
        float h = (float)VIRTUAL_HEIGHT * scale;

        //Screen.getScreen(_graphics).setWidth((int)w);
        //Screen.getScreen(_graphics).setHeight((int)h);

        _screen.setWidth((int)w);
        _screen.setHeight((int)h);

        _graphics.setCrop(crop);
        _graphics.setScale(scale);





    }


    /**
     * Changes alpha value during the time depending on the current fade state
     *
     * @param newValue
     * @param fade_state
     * @param velocity
     * @param alpha
     * @return
     */
    protected Alpha changeAlpha(float newValue, FADE_STATE fade_state, float velocity, float alpha){



        switch (fade_state) {
            case FADE_IN:

                alpha += velocity * newValue;

                if(alpha >= 1.0f) {
                    fade_state = FADE_STATE.FADE_OUT;
                    alpha = 1;
                }

                break;
            case FADE_OUT:

                alpha -= velocity * newValue;

                if(alpha <= 0) {
                    fade_state = FADE_STATE.FADE_IN;
                    alpha = 0;
                }

                break;

            default:
                break;
        }

        Alpha aux = new Alpha(fade_state, alpha);

        return aux;
    }



    protected void drawTapToPlay(int x, int y){

        _graphics.drawImageScaled(_tapToPlay.getImage(), x, y, _tapToPlay.get_width(), _tapToPlay.get_height(),
                _tapToPlay.get_x(), _tapToPlay.get_y(), _tapToPlay.get_width(), _tapToPlay.get_height(), _alphaTapToPlay);
    }



    // Draws a white flash effect over the window
    protected void flashBackground() {

        if(_stateChange) {

            _graphics.drawImage(_whiteFlash.getImage(), 0, 0, _graphics.getWidth(), _graphics.getHeight(),
                    _whiteFlash.get_x(), _whiteFlash.get_y(), _whiteFlash.get_width(), _whiteFlash.get_height(), _alphaBackground);


        }
    }

    private void clearButtonsList() {

        _buttons.clear();
    }

    // Draws the buttons
    protected void drawButtons(List<Button> buttons) {

        for(int i = 0; i < buttons.size(); i++){
            _graphics.drawImageScaled(buttons.get(i).getSprite().getImage(), buttons.get(i).getX(),buttons.get(i).getY(), buttons.get(i).getWidth(), buttons.get(i).getHeight(),
                    buttons.get(i).getSprite().get_x(), buttons.get(i).getSprite().get_y(), buttons.get(i).getSprite().get_width(), buttons.get(i).getSprite().get_height(), 1.0f);
        }

    }

}
