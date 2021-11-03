package com.ucm.gdv.logic;
import com.ucm.gdv.engineinterface.IGraphics;
import com.ucm.gdv.engineinterface.IImage;


public class Player {

    private IGraphics _graphics;

    private float _x;
    private float _y;

    private Sprite _playerToRender;
    private Sprite _wPlayer;
    private Sprite _bPlayer;


    private IImage _imageToUse;
    private int[] _imageProperties;

    private boolean _canRender;


    public enum COLORS{
        BLACK, WHITE
    }

    COLORS _currentColor;


    public Sprite giveMeRandomPlayer(){
        Sprite aux;
        int number = (int)(Math.random()*2);
        /*
         * El color de las bolas que caen es aleatorio, aunque ligeramente sesgado para favorecer
         * la aparición de secuencias del mismo color. Cada bola tiene un 70 % de probabilidades
         * de ser del mismo color que la que tiene debajo de ella.
         */
        if(number < 1){
            aux = _wPlayer;
            _currentColor = COLORS.WHITE;
        }else{
            aux = _bPlayer;
            _currentColor = COLORS.BLACK;
        }
        return aux;
    }

    public Player(float x, float y, IGraphics graphics){

        _graphics = graphics;

        _imageToUse = Resources.getResources().getImage("players");
        _imageProperties = Resources.getResources().getImageProperties("players");



        _wPlayer = new Sprite(_imageToUse, _imageProperties[0], _imageProperties[1], 0, 0);
        _bPlayer = new Sprite(_imageToUse, _imageProperties[0], _imageProperties[1], 0, 1);

        _playerToRender = giveMeRandomPlayer();

        //_x = x - getWidth()/4;
        _x = x - ((getWidth()  * _graphics.getScale()) / 2);

        //_y = y + getHeight();
        _y = y + ((getHeight() * _graphics.getScale()) / 2);

        _canRender = true;
    }


    public void drawPlayer(){
        if(_canRender) {
            _graphics.drawImageScaled(_playerToRender.getImage(), (int) _x, (int) _y, _playerToRender.get_width(), _playerToRender.get_height(),
                    _playerToRender.get_x(), _playerToRender.get_y(), _playerToRender.get_width(), _playerToRender.get_height(), 1.0f);
        }
    }


    public void updatePlayer(){
        if(_canRender) {
            if (_playerToRender == _wPlayer) {
                _playerToRender = _bPlayer;
                _currentColor = COLORS.BLACK;
            } else {
                _playerToRender = _wPlayer;
                _currentColor = COLORS.WHITE;
            }
        }
    }


    public int getWidth(){

        return _playerToRender.get_width();
    }

    public int getHeight(){

        return _playerToRender.get_height();
    }

    public int getX(){ return (int)_x; }

    public int getY(){

        return (int)_y;
    }


    public void setX(int newX) {

        _x = newX - ((getWidth()  * _graphics.getScale()) / 2);
    }


    public void setY(int newY) {

        _y = newY + ((getHeight() * _graphics.getScale()) / 2);
    }

    public COLORS getCurrentColor(){

        return _currentColor;
    }

    public boolean getCanRender(){

        return _canRender;
    }

    public void setCanRender(boolean value){

        _canRender = value;
    }
}
