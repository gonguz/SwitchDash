package com.ucm.gdv.logic.states;

import com.ucm.gdv.engineinterface.IGame;
import com.ucm.gdv.engineinterface.IInput;
import com.ucm.gdv.logic.Button;
import com.ucm.gdv.logic.managers.GameManager;
import com.ucm.gdv.logic.Resources;
import com.ucm.gdv.logic.Sprite;
import com.ucm.gdv.logic.managers.TextManager;

import java.util.List;

public class GameOverState extends GameState {

    // Sprites
    private Sprite _gameOver;
    private Sprite _help;
    private Sprite _soundOn;
    private Sprite _soundOff;
    private Sprite _home;
    private Sprite _playAgain;

    // Buttons
    private Button _helpButton;
    private Button _soundButton;
    private Button _homeButton;

    private boolean _soundClicked;
    private boolean _buttonClicked;

    private TextManager _textManager;


    // Sprites constants for the positions
    private final int _GAME_OVER = 364;
    private final int _PLAY_AGAIN = 1396;
    private final int _HOME = 1200;
    private final int _POINTS = 700;
    private final int _POINTS_TEXT = 850;
    private final int _BEST = 1050;



    public GameOverState(IGame game){
        super(game);
        init();
    }


    @Override
    public void init() {
        super.init();

        _soundClicked = true;
        _buttonClicked =  false;

        _gameOver = new Sprite(Resources.getResources().getImage("gameOver"),
                Resources.getResources().getImageProperties("gameOver")[0], Resources.getResources().getImageProperties("gameOver")[1], 0, 0);

        _playAgain = new Sprite(Resources.getResources().getImage("playAgain"),
                Resources.getResources().getImageProperties("playAgain")[0], Resources.getResources().getImageProperties("playAgain")[1], 0, 0);

        _help = new Sprite(Resources.getResources().getImage("buttons"),
                Resources.getResources().getImageProperties("buttons")[0],  Resources.getResources().getImageProperties("buttons")[1], 0, 0);

        _soundOn = new Sprite(Resources.getResources().getImage("buttons"),
                Resources.getResources().getImageProperties("buttons")[0],  Resources.getResources().getImageProperties("buttons")[1], 2, 0);
        _soundOff = new Sprite(Resources.getResources().getImage("buttons"),
                Resources.getResources().getImageProperties("buttons")[0],  Resources.getResources().getImageProperties("buttons")[1], 3, 0);

        _home = new Sprite(Resources.getResources().getImage("buttons"),
                Resources.getResources().getImageProperties("buttons")[0],  Resources.getResources().getImageProperties("buttons")[1], 4, 0);




        _textManager = new TextManager(_graphics);

    }

    @Override
    public void update(double elapsedTime) {
        super.update(elapsedTime);

        fillStartStateButtons();

        if(_flashBackgroundFinished) {

            GameManager.getGameManager().endGame();

            // Check the state to change
            switch (_state) {

                case START:
                    GameManager.getGameManager().resetBackGroundColor();
                    StartState startState = new StartState(_game);
                    _game.setState(startState);
                    break;

                case HELP:
                    GameManager.getGameManager().resetBackGroundColor();
                    HelpState helpState = new HelpState(_game);
                    _game.setState(helpState);
                    break;

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

        drawGameOver();
        drawPlayAgain((int)((_screen.getWidth() / 2) - ((_playAgain.get_width() * _graphics.getScale()) / 2)), (int)(_PLAY_AGAIN * _graphics.getScale()));
        drawButtons(_buttons);


        _textManager.drawPoints(Integer.toString(GameManager.getGameManager().getPoints()), (int)(( _screen.getWidth() / 2) - (20 * _graphics.getScale())), (int)(_POINTS * _graphics.getScale()), 1.0f);
        _textManager.drawText("POINTS", (int)((_screen.getWidth() / 2) - (20 * _graphics.getScale())), (int)(_POINTS_TEXT * _graphics.getScale()), 1.0f);

        // If the new score is the new best score
        if(GameManager.getGameManager().isNewBest()) {
            _textManager.drawText("NEW BEST: ", ( _screen.getWidth() / 2), (int)(_BEST * _graphics.getScale()), 0.6f);
            _textManager.drawPoints(Integer.toString(GameManager.getGameManager().getPoints()), (_screen.getWidth() / 2) + (int)(320 * _graphics.getScale()), (int)(_BEST * _graphics.getScale()), 0.6f);
        }
        // If a the new score isn't higher than the best score
        else {
            _textManager.drawText("BEST: ", ( _screen.getWidth() / 2), (int)(_BEST * _graphics.getScale()), 0.6f);
            _textManager.drawPoints(Integer.toString(GameManager.getGameManager().getBestScore()), (_screen.getWidth() / 2) + (int)(200 * _graphics.getScale())  , (int)(_BEST * _graphics.getScale()), 0.6f);

        }

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

                                    _stateChange = true;
                                    _state = STATES.HELP;

                                    break;

                                    // Switch sound sprites
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

                                case HOME:

                                    _stateChange = true;
                                    _state = STATES.START;

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

    public void drawPlayAgain(int x, int y){

        _graphics.drawImageScaled(_playAgain.getImage(), x, y, _playAgain.get_width(), _playAgain.get_height(),
                _playAgain.get_x(), _playAgain.get_y(), _playAgain.get_width(), _playAgain.get_height(), _alphaTapToPlay);
    }

    public void drawGameOver(){

        _graphics.drawImageScaled(_gameOver.getImage(), (int)(( _screen.getWidth() / 2) - ((_gameOver.get_width() * _graphics.getScale()) / 2)), (int)(_GAME_OVER * _graphics.getScale()),
                _gameOver.get_width(), _gameOver.get_height(), _gameOver.get_x(), _gameOver.get_y(), _gameOver.get_width(), _gameOver.get_height(), 1.0f);
    }

    // Fill the buttons list
    private void fillStartStateButtons() {

        if(!_initialized) {


            // Help button
            _helpButton = new Button(_help, (int)( _screen.getWidth() - (100 * _graphics.getScale()) - ((_help.get_width() * _graphics.getScale()) / 2)), (int)(_BUTTONS * _graphics.getScale()),
                    _help.get_width(), _help.get_height(), Button.TYPE.HELP);
            _buttons.add(_helpButton);

            // Sound button
            _soundButton = new Button(_soundOn, (int)((_BUTTONS * _graphics.getScale())), (int)(_BUTTONS * _graphics.getScale()), _soundOn.get_width(), _soundOn.get_height(), Button.TYPE.SOUND);
            _buttons.add(_soundButton);

            // Home button
            _homeButton = new Button(_home, (int)(( _screen.getWidth() / 2) - ((_home.get_width() * _graphics.getScale()) / 2)), (int)(_HOME * _graphics.getScale()), _home.get_width(), _home.get_height(), Button.TYPE.HOME);
            _buttons.add(_homeButton);

            _initialized = true;
        }
    }
}
