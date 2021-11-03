package com.ucm.gdv.logic.states;

import com.ucm.gdv.engineinterface.IGame;
import com.ucm.gdv.engineinterface.IInput;
import com.ucm.gdv.logic.Button;
import com.ucm.gdv.logic.Resources;
import com.ucm.gdv.logic.Sprite;
import com.ucm.gdv.logic.managers.GameManager;


import java.util.List;

public class StartState extends GameState {


    private Sprite _switchDash;
    private Sprite _soundOn;
    private Sprite _soundOff;
    Sprite _help;

    private Button _helpButton;
    private Button _soundButton;

    private boolean _soundClicked;
    private boolean _buttonClicked;


    // Spites constants positions
    private final int _SWITCH_DASH = 356;
    private final int _TAP_TO_PLAY = 950;



    public StartState(IGame game){
        super(game);
        this.init();
    }


    @Override
    public void init() {
        super.init();

        _soundClicked = true;
        _buttonClicked = false;
        _initialized = false;

        _switchDash = new Sprite(Resources.getResources().getImage("switchDashLogo"),
                Resources.getResources().getImageProperties("switchDashLogo")[0], Resources.getResources().getImageProperties("switchDashLogo")[1], 0, 0);

        _help = new Sprite(Resources.getResources().getImage("buttons"),
                Resources.getResources().getImageProperties("buttons")[0],  Resources.getResources().getImageProperties("buttons")[1], 0, 0);

        _soundOn = new Sprite(Resources.getResources().getImage("buttons"),
                Resources.getResources().getImageProperties("buttons")[0],  Resources.getResources().getImageProperties("buttons")[1], 2, 0);

        _soundOff = new Sprite(Resources.getResources().getImage("buttons"),
                Resources.getResources().getImageProperties("buttons")[0],  Resources.getResources().getImageProperties("buttons")[1], 3, 0);


        //fillStartStateButtons();

    }

    @Override
    public void update(double elapsedTime) {
        super.update(elapsedTime);

        fillStartStateButtons();

       if(_flashBackgroundFinished) {
           HelpState helpState;

           switch (_state) {

               case HELP:

                   helpState = new HelpState(_game);
                   _game.setState(helpState);

                   break;
               case PLAY:

                   // Check if it is the first time playing
                   if (GameManager.getGameManager().isFirstTimePlaying()) {

                       // First time playing so change to already played in order to not going anymore to the HELP STATE
                       GameManager.getGameManager().hasPlayed();
                       // It is the first time playing so go to the HELP STATE
                       helpState = new HelpState(_game);
                       _game.setState(helpState);
                   } else {
                       // It is NOT the first time playing so go directly to the PLAY STATE
                       PlayState playState = new PlayState(_game);
                       _game.setState(playState);
                   }

                   break;
           }
       }

    }

    @Override
    public void render() {
        super.render();


        drawSwitchDashLogo();
        drawTapToPlay((int)(( _screen.getWidth() / 2) - ((_tapToPlay.get_width() * _graphics.getScale()) / 2)), (int)(_TAP_TO_PLAY * _graphics.getScale()));
        drawButtons(_buttons);
        flashBackground();

    }


    @Override
    public void handleInput() {

        _buttonClicked = false;

        List<IInput.TouchEvent> events = _input.getTouchEvents();

        for(IInput.TouchEvent event : events){

            switch (event.getType()) {
                case PRESSED:
                    break;
                case DRAGGED:
                    break;
                case RELEASED:

                    for(int i = 0; i < _buttons.size(); i++){

                        if(_buttons.get(i).isClicked((int)(event.getX() - _graphics.getCrop()[0]) , (int)(event.getY() - _graphics.getCrop()[1]), _graphics.getScale())){

                            switch (_buttons.get(i).getType()){

                                case HELP:

                                    _state = STATES.HELP;
                                    _stateChange = true;

                                    break;
                                case SOUND:

                                    if(_soundClicked) {
                                        _soundButton.setSprite(_soundOff);
                                        _soundClicked = false;
                                    }
                                    else {
                                        _soundButton.setSprite(_soundOn);
                                        _soundClicked = true;
                                    }


                                    break;
                                default:
                                    break;
                            }

                            _buttonClicked = true;
                        }

                    }

                     if(!_buttonClicked) {
                         _state = STATES.PLAY;
                         _stateChange = true;
                     }

                    break;
                default:
                    break;
            }

        }


    }


    public void drawSwitchDashLogo(){

        _graphics.drawImageScaled(_switchDash.getImage(), (int)(( _screen.getWidth() / 2) - ((_switchDash.get_width() * _graphics.getScale()) / 2)), (int)(_SWITCH_DASH * _graphics.getScale()), _switchDash.get_width(), _switchDash.get_height(),
                _switchDash.get_x(), _switchDash.get_y(), _switchDash.get_width(), _switchDash.get_height(), 1.0f);

    }

    public void fillStartStateButtons() {

        if(!_initialized) {

          _helpButton = new Button(_help, (int)( _screen.getWidth() - (100 * _graphics.getScale()) - ((_help.get_width() * _graphics.getScale()) / 2)), (int)(_BUTTONS * _graphics.getScale()), _help.get_width(), _help.get_height(), Button.TYPE.HELP);
         _buttons.add(_helpButton);

          _soundButton = new Button(_soundOn, (int)((_BUTTONS * _graphics.getScale())), (int)(_BUTTONS * _graphics.getScale()), _soundOn.get_width(), _soundOn.get_height(), Button.TYPE.SOUND);
         _buttons.add(_soundButton);

          _initialized = true;
        }
    }

}
