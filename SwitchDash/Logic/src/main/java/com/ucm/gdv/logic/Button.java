package com.ucm.gdv.logic;

public class Button {


    public Button(Sprite button, int x, int y, int width, int height, TYPE type) {

        _button = button;

        _x = x;
        _y = y;

        _width = width;
        _height = height;

        _type = type;

    }

    public enum TYPE {PLAY, EXIT, HELP, SOUND, HOME};

    private TYPE _type;

    private Sprite _button;

    private int _x;
    private int _y;

    private int _width;
    private int _height;

    public boolean isClicked(int xClicked, int yClicked, float scale) {

        boolean clicked = false;

        //System.out.println("x: " + _x + " xClicked: "+ xClicked + " width :" + (_width * scale) + " y: " + _y +  " yClicked: " + yClicked + " height: " + (_height * scale) );

        if((xClicked >= _x && xClicked <= (_x + (_width * scale)) && (yClicked >= _y && yClicked <= _y + (_height * scale)))){

            clicked = true;
        }

        return clicked;

    }

    public int getX(){
        return _x;
    }

    public int getY() {
        return _y;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public Sprite getSprite() {

        return _button;
    }

    public void setSprite(Sprite newSprite) {

       _button = newSprite;
    }

    public TYPE getType() {
        return _type;
    }
}
