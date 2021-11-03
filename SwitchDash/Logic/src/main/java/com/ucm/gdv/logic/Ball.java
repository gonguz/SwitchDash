package com.ucm.gdv.logic;
import com.ucm.gdv.engineinterface.IGraphics;
import com.ucm.gdv.engineinterface.IImage;
import com.ucm.gdv.logic.managers.GameManager;


public class Ball {

    private IGraphics _graphics;

    private float _x;
    private float _y;
    private float _initialY;


    private Sprite _wBall;
    private Sprite _bBall;
    private Sprite _ballToRender;

    private IImage _imageToUse;
    private int[] _imageProperties;

    private boolean _canReset;

    public enum COLORS{
        BLACK, WHITE
    }

    COLORS _currentColor;

    public Sprite giveMeRandomBall(){
        Sprite aux;
        int number = (int)(Math.random()*10);
        /*
        * El color de las bolas que caen es aleatorio, aunque ligeramente sesgado para favorecer
        * la aparición de secuencias del mismo color. Cada bola tiene un 70 % de probabilidades
        * de ser del mismo color que la que tiene debajo de ella.
        */
        if(number < 5){
            aux = _wBall;
            _currentColor = COLORS.WHITE;
        }else{
            aux = _bBall;
            _currentColor = COLORS.BLACK;
        }
        return aux;
    }

    public Ball(float x, float y, IGraphics graphics){


        _graphics = graphics;

        _imageToUse = Resources.getResources().getImage("balls");
        _imageProperties = Resources.getResources().getImageProperties("balls");


        _wBall = new Sprite(_imageToUse, _imageProperties[0], _imageProperties[1], 0, 0);
        _bBall = new Sprite(_imageToUse, _imageProperties[0], _imageProperties[1], 0, 1);

        _x = x - ((getWidth()  * _graphics.getScale()) / 2);

        _y = y + ((getHeight() * _graphics.getScale()) / 2);

        _ballToRender = giveMeRandomBall();

        _canReset = false;
    }


    public void drawBall(){
        _graphics.drawImageScaled(_ballToRender.getImage(), (int) _x,(int) _y, _ballToRender.get_width(), _ballToRender.get_height(),
                _ballToRender.get_x(), _ballToRender.get_y(), _ballToRender.get_width(), _ballToRender.get_height(), 1.0f);
    }

    public void drawBallAsParticle(float alpha, int widthFactor, int heightFactor, float x, float y){
        _graphics.drawImageScaled(_ballToRender.getImage(), (int) x,(int) y, _ballToRender.get_width()/widthFactor, _ballToRender.get_height()/heightFactor,
                _ballToRender.get_x(), _ballToRender.get_y(), _ballToRender.get_width(), _ballToRender.get_height(), alpha);
    }


    public void resetBallPosition(float newPos){

        _y = newPos;
    }

    public void updateBall(double deltaTime){

       _y += GameManager.getGameManager().getBallsVelocity() * _graphics.getScale() * deltaTime;
    }

    public int getWidth() {

        return _wBall.get_width();
    }

    public int getHeight(){

        return _wBall.get_height();
    }

    public int getY(){
        return (int)_y;
    }

    public int getX(){
        return (int)_x;
    }

    public void setX(int newX) {

        _x = newX - ((getWidth()  * _graphics.getScale()) / 2);
    }


    public void setY(int newY) {

        _y = newY + ((getHeight() * _graphics.getScale()) / 2);
    }


    public COLORS getCurrentColor() {

        return _currentColor;
    }

    public void setCurrentColor(COLORS newColor){

        _currentColor = newColor;

        if(_currentColor == COLORS.WHITE){
            _ballToRender = _wBall;
        }else{
            _ballToRender = _bBall;
        }
    }

    public boolean getCanReset(){

        return _canReset;
    }
}
