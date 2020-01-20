package com.ucm.gdv.logic;

import com.ucm.gdv.engineinterface.IGraphics;
import com.ucm.gdv.logic.managers.GameManager;

import java.util.*;

public class Screen {


    private static Screen _screen;

    private int _width;
    private int _height;

    private IGraphics _graphics;

    private Sprite _background;
    private Sprite _arrows;


    private int _randomInt;

    private int _backgroundColor;

    private float _posYArrows;
    private float _iniPos;

    //private final int _ARROWS_VELOCITY = 384;


    public Screen(IGraphics graphics, int width, int height){

        _graphics = graphics;

        //_width = _graphics.getWidth();
        //_height = _graphics.getHeight();

        _width = width;
        _height = height;

        initResources();

        Random randomGenerator = new Random();
        _randomInt = randomGenerator.nextInt(9);

    }



    /*public static Screen getScreen(IGraphics graphics){

        if(_screen == null) {
            _screen = new Screen(graphics);
        }

        return _screen;
    }*/


    public void initResources(){

        _background = new Sprite(Resources.getResources().getImage("backgrounds"),
                Resources.getResources().getImageProperties("backgrounds")[0], Resources.getResources().getImageProperties("backgrounds")[1], _randomInt, 0);

        _arrows = new Sprite(Resources.getResources().getImage("arrowsBackground"),
                Resources.getResources().getImageProperties("arrowsBackground")[0],  Resources.getResources().getImageProperties("arrowsBackground")[1], 0, 0);


        _posYArrows = _graphics.getCrop()[1] - ((_arrows.get_height() * _graphics.getScale()) / 5);
        //System.out.println("Init posYArrows: " + _posYArrows + " ");

        updateArrowsInitialPos();

    }

   public void updateArrowsInitialPos() {

        _iniPos = _graphics.getCrop()[1] - ((_arrows.get_height() * _graphics.getScale()) / 5);
        _posYArrows += _graphics.getCrop()[1];
    }


    public void drawArrows(){

        //System.out.println("Draw posYArrows: " + _posYArrows + " ");
        //System.out.println("Crop y: " + _graphics.getCrop()[1] + " ");
         _graphics.drawImageScaled(_arrows.getImage(), (int)(_width - (_arrows.get_width() *_graphics.getScale())) / 2 ,
                 (int) (_posYArrows - _graphics.getCrop()[1]), _arrows.get_width(), _arrows.get_height(), _arrows.get_x(), _arrows.get_y(), _arrows.get_width(), _arrows.get_height(), 0.2f);

    }

    public void drawBackground() {

        _backgroundColor = Resources.getResources().getBackgroundColor(GameManager.getGameManager().getBackGroundColor());
        _graphics.clear(_backgroundColor);


        _graphics.drawImageScaled(_background.getImage(), (int)(_width - (_arrows.get_width() *_graphics.getScale())) / 2, 0,
                _arrows.get_width(), (int)(_height / _graphics.getScale()), _background.get_x(), _background.get_y(), _background.get_width(), _background.get_height(), 0.2f);

    }

    public void update(double elapsedTime){

        _posYArrows += GameManager.getGameManager().getArrowsVelocity() * _graphics.getScale() * elapsedTime;
        //System.out.println("Update posYArrows: " + _posYArrows + " ");

        if(_posYArrows >= _graphics.getCrop()[1]){
            _posYArrows = _iniPos;
            //System.out.println("Reset posYArrows: " + _posYArrows + " ");
        }

    }

    public void render(){
        drawBackground();
        drawArrows();
    }

    public int getWidth() {

        return _width;
    }

    public int getHeight() {

       return _height;
    }

    public void setWidth(int width) {
        _width = width;
    }

    public void setHeight(int height) {
        _height = height;
    }
}
