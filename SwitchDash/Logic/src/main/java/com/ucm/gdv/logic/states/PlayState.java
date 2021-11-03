package com.ucm.gdv.logic.states;

import com.ucm.gdv.engineinterface.IGame;
import com.ucm.gdv.engineinterface.IInput;
import com.ucm.gdv.logic.Ball;
import com.ucm.gdv.logic.ParticleSystem;
import com.ucm.gdv.logic.Player;
import com.ucm.gdv.logic.managers.GameManager;
import com.ucm.gdv.logic.managers.TextManager;

import java.util.LinkedList;
import java.util.List;


public class PlayState extends GameState {

    // Initial position where the balls are placed
    private float _posBallY;

    private final int NUM_BALLS = 4;
    private LinkedList<Ball> _ballList;

    private Player _player;
    // Player position
    private int _posPlayer;

    private int _ballsFailed;


    // Booleans for controlling the play state
    private boolean _gameLost;
    private  boolean _destroyed;

    private TextManager _textManager;

    private ParticleSystem _pSystem;
    private boolean _systemInit;


    private final int _PLAYER = 1200;
    private final int _BALLS = 395;


    public PlayState(IGame game){
        super(game);
        init();
    }


    @Override
    public void init() {
        super.init();

        _posBallY = 0;
        _ballList = new LinkedList<>();
        fillBallList();

        _player = new Player((_screen.getWidth() / 2), (int)(_PLAYER * _graphics.getScale()), _graphics);
        _posPlayer = _player.getY();

        _gameLost = false;
        _systemInit = false;

        _textManager = new TextManager(_graphics);

        //GameManager.getGameManager().resetPoints();

        _pSystem = new ParticleSystem(_graphics);
        _pSystem.initParticleSystem();

        _destroyed = false;

        _ballsFailed = 0;

        GameManager.getGameManager().resetBackGroundColor();
        GameManager.getGameManager().resetArrowsVelocity();
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);


        if(!_gameLost) {

            // Update player positions due to the resize
            _player.setX(_screen.getWidth() / 2);
            _player.setY((int)(_PLAYER * _graphics.getScale()));
            _posPlayer = _player.getY();

            updateBallList(deltaTime);

            // Check if a ball collides with the player
            if (_ballList.peek().getY() + (_ballList.peek().getHeight() * _graphics.getScale()) >= _posPlayer) {
                // Check the player and ball collided colors
                if (!_destroyed && _ballList.peek().getCurrentColor().toString() == _player.getCurrentColor().toString()) {

                    GameManager.getGameManager().increaseArrowsVelocity();
                    GameManager.getGameManager().addPoints();

                    resetBall();

                    if(GameManager.getGameManager().getPoints() % 10 == 0 && GameManager.getGameManager().getPoints() != 0){

                        GameManager.getGameManager().increaseBallsVelocity();
                    }



                } else { // Wrong color, destroy player

                    _destroyed = true;
                    _player.setCanRender(false);
                    _ballsFailed ++;

                    resetBall();

                    if(_ballsFailed > 1){

                        _gameLost = true;
                        _stateChange = true;

                        _state = STATES.GAME_OVER;
                    }

                }
            }

            if(_pSystem != null) {
                _pSystem.updateParticleSystem(deltaTime, _graphics.getScale());
            }
        } else{ // Game is lost


            if(_flashBackgroundFinished) {

                // Check if the points at the current game are the new best score
                GameManager.getGameManager().checkNewBest();

                switch(_state) {

                    case GAME_OVER:
                        // Change the state to the GAME OVER one
                        GameOverState startState = new GameOverState(_game);
                        _game.setState(startState);
                        break;
                }
            }
        }
    }

    @Override
    public void render() {
        super.render();

        _player.drawPlayer();

        if(_pSystem != null && _systemInit) {
            _pSystem.renderParticleSystem();
        }


        _textManager.drawPoints(Integer.toString(GameManager.getGameManager().getPoints()),
                (int)( _screen.getWidth() - (130  * _graphics.getScale())) , (int)(_BUTTONS * _graphics.getScale()), 1.0f);

        flashBackground();

        drawBallList();

        _graphics.clearCrop(0, 0,  0, _graphics.getWidth(), (int)_graphics.getCrop()[1]);
        _graphics.clearCrop(0, 0,  (int)((float) _screen.getHeight() + _graphics.getCrop()[1]), _graphics.getWidth(),
                _graphics.getHeight() - (int)((float) _screen.getHeight() + _graphics.getCrop()[1]));

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
                    _player.updatePlayer();
                    break;
                default:
                    break;
            }

        }
    }

    // ---------------- METHODS FOR CONTROLLING THE BALLS ----------------

    public void fillBallList(){

        for(int i = 0; i < NUM_BALLS; i++){
            _ballList.add(new Ball((_screen.getWidth() / 2), _posBallY, _graphics));

            _posBallY -= (_BALLS * _graphics.getScale());
        }
    }

    public void drawBallList(){
        for(int i = 0; i < NUM_BALLS; i++){
            _ballList.get(i).drawBall();
        }
    }

    public void updateBallList(double deltaTime){
        for(int i = 0; i < NUM_BALLS; i++){
            _ballList.get(i).setX((_screen.getWidth() / 2));
            //_ballList.get(i).setY((int)_posBallY);
            _ballList.get(i).updateBall(deltaTime);
        }
    }

    public Ball.COLORS setRandomBall(){

        Ball.COLORS aux = _ballList.getFirst().getCurrentColor();
        Ball.COLORS auxToReturn = aux;
        int number = (int)(Math.random()*10);
        /*
         * El color de las bolas que caen es aleatorio, aunque ligeramente sesgado para favorecer
         * la aparición de secuencias del mismo color. Cada bola tiene un 70 % de probabilidades
         * de ser del mismo color que la que tiene debajo de ella.
         */
        if(number < 7){
            aux = _ballList.getLast().getCurrentColor();
        }else{
            if(aux == Ball.COLORS.BLACK){
                aux = Ball.COLORS.WHITE;
            }else{
                aux = Ball.COLORS.BLACK;
            }
        }
        _ballList.peek().setCurrentColor(aux);
        return auxToReturn;
    }

    public void resetBall(){

        if(_ballsFailed <= 1) {

            Ball.COLORS pColor = setRandomBall();
            Ball aux = _ballList.peek();

            _pSystem.setParticleProperties((_screen.getWidth() / 2) - (int)(20 * _graphics.getScale())/* - (aux.getWidth()/4)*/, (_screen.getWidth() / 2) + (int)(20 * _graphics.getScale()), _player.getY(),
                    _player.getY() + (int)(_player.getHeight() * _graphics.getScale()), pColor);
            _systemInit = true;

            //_p = new Particle(_graphics, aux.getX(), aux.getY(), 6, 6);
            //_p.setColor(pColor);
            //System.out.println(aux.getCurrentColor());

            _ballList.pop();
            aux.resetBallPosition(_ballList.getLast().getY() - (_BALLS * _graphics.getScale()));
            _ballList.addLast(aux);
        }
    }
}
