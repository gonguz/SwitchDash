package com.ucm.gdv.logic.states;

import com.ucm.gdv.engineinterface.IGame;
import com.ucm.gdv.engineinterface.IInput;
import com.ucm.gdv.logic.Button;
import com.ucm.gdv.logic.Resources;
import com.ucm.gdv.logic.Sprite;

import java.util.List;

public class HelpState extends GameState{

    // Sprites
    private Sprite _howToPlay;
    private Sprite _instructions;
    private Sprite _exit;


    private Button _exitButton;


    // Sprites constants for the positions
    private final int _TAP_TO_PLAY = 1464;
    private final int _HOW_TO_PLAY = 290;
    private final int _INSTRUCTIONS = 768;

    public HelpState(IGame game){
        super(game);
        init();
    }


    @Override
    public void init() {
        super.init();

        _initialized = false;

        _howToPlay = new Sprite(Resources.getResources().getImage("howToPlay"),
                Resources.getResources().getImageProperties("howToPlay")[0], Resources.getResources().getImageProperties("howToPlay")[1], 0, 0);

        _instructions = new Sprite(Resources.getResources().getImage("instructions"),
                Resources.getResources().getImageProperties("instructions")[0], Resources.getResources().getImageProperties("instructions")[1], 0, 0);

        _exit = new Sprite(Resources.getResources().getImage("buttons"),
                Resources.getResources().getImageProperties("buttons")[0], Resources.getResources().getImageProperties("buttons")[1], 1, 0);





    }


    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);


        fillStartStateButtons();

        if(_flashBackgroundFinished) {

            switch (_state) {
                case PLAY:
                    PlayState playState = new PlayState(_game);
                    _game.setState(playState);
                    break;
            }
        }

    }

    @Override
    public void render() {
        super.render();

        drawButtons(_buttons);
        drawTapToPlay((int)(( _screen.getWidth() / 2) - ((_tapToPlay.get_width() * _graphics.getScale()) / 2)), (int)(_TAP_TO_PLAY * _graphics.getScale()));
        drawHowToPlay();
        drawInstructions();
        flashBackground();
    }

    @Override
    public void handleInput() {
        List<IInput.TouchEvent> events = _input.getTouchEvents();

        for(IInput.TouchEvent event : events){

            switch (event.getType()) {
                case PRESSED:


                    break;
                case DRAGGED:
                    break;
                case RELEASED:

                    if(_exitButton.isClicked((int)(event.getX() - _graphics.getCrop()[0]) , (int)(event.getY() - _graphics.getCrop()[1]), _graphics.getScale())){

                        _stateChange= true;
                        _state = STATES.PLAY;
                    }
                   else {

                        _stateChange = true;
                        _state = STATES.PLAY;
                    }

                    break;
                default:
                    break;
            }

        }
    }

    public void drawHowToPlay(){


        _graphics.drawImageScaled(_howToPlay.getImage(), (int)((_screen.getWidth() / 2) - ((_howToPlay.get_width() * _graphics.getScale()) / 2)),  (int)(_HOW_TO_PLAY * _graphics.getScale()), _howToPlay.get_width(), _howToPlay.get_height(),
                _howToPlay.get_x(), _howToPlay.get_y(), _howToPlay.get_width(), _howToPlay.get_height(), 1.0f);

    }

    public void drawInstructions(){

        _graphics.drawImageScaled(_instructions.getImage(), (int)(( _screen.getWidth() / 2) - ((_instructions.get_width() * _graphics.getScale()) / 2)), (int)(_INSTRUCTIONS * _graphics.getScale()),
                _instructions.get_width(), _instructions.get_height(),
                _instructions.get_x(), _instructions.get_y(), _instructions.get_width(), _instructions.get_height(), 1.0f);
    }

    // Fill the buttons list
    private void fillStartStateButtons() {


        if(!_initialized) {

            _exitButton = new Button(_exit, (int) (_screen.getWidth() - (100 * _graphics.getScale()) - ((_exit.get_width()* _graphics.getScale()) / 2)), (int) (_BUTTONS * _graphics.getScale()),
                    _exit.get_width(), _exit.get_height(), Button.TYPE.EXIT);

            _buttons.add(_exitButton);

            _initialized = true;
        }
    }



}
