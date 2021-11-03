package com.ucm.gdv.logic;

import com.ucm.gdv.engineinterface.IGraphics;
import java.util.Random;

public class Particle {

    private Ball _ball;

    private float _x;
    private float _y;
    private int _width;
    private int _height;

    private int dirX;
    private int angle;

    private float _alpha;
    private float _velocityX;
    private float _velocityY;

    private boolean _canRender;

    public Particle(IGraphics graphics, float x, float y, int widthFactor, int heightFactor){

        _x = x;
        _y = y;
        _width = widthFactor;
        _height = heightFactor;

        _ball = new Ball(_x, _y, graphics);
        _ball.setX((int)_x);

        _alpha = (float)(Math.random());
        if(_alpha <= 0.1f){
            _alpha = 0.2f;
        }

        if(_alpha >= 0.5f){
            _alpha = 0.5f;
        }

        _canRender = true;
        _velocityX = (int)(150 + (Math.random() * (300 - 150)));
        _velocityY = (int)(350 + (Math.random() * (600 - 350)));
        angle = 45;
        dirX = new Random().nextInt(1 + 1) - 1;
       /* if(dirX == 0){
            dirX = 1;
        }*/

    }


    public void drawParticle(){
        if(_canRender){
            _ball.drawBallAsParticle(_alpha, _width, _height, _x, _y);
        }
    }


    public void updateParticle(double elapsedTime, float scale){
        _x += ((_velocityX * Math.sin(45)) *elapsedTime*dirX) * scale ;
        _y += ((_velocityY * Math.cos(45))*elapsedTime) * scale;
        _alpha -= (0.7f * elapsedTime);

        if(_alpha <= 0){
            _canRender = false;
        }
    }

    public void setColor(Ball.COLORS color){
        _ball.setCurrentColor(color);
    }

    public void setX(int newX){
        _x = newX;
    }

    public void setY(int newY){
        _y = newY;
    }

    public void setProportion(int factor){
        _width = factor;
        _height = factor;
    }

    public void resetAlpha(){
        _alpha = (float)(Math.random());
        if(_alpha <= 0.1f){
            _alpha = 0.2f;
        }

        if(_alpha >= 0.5f){
            _alpha = 0.5f;
        }
    }

    public void setParticleProperties(int minX, int maxX, int minY, int maxY, Ball.COLORS color){
        int proportion = (int)(3 + (Math.random() * (8 - 3)));
        int xP = (int)(minX + (Math.random() * (maxX - minX)));
        int yP = (int)(minY + (Math.random() * (maxY - minY)));

        setX(xP);
        setY(yP);
        setProportion(proportion);
        resetAlpha();
        setCanRender(true);
        setColor(color);
    }


    public boolean getCanRender(){
        return _canRender;
    }
    public void setCanRender(boolean can){
        _canRender = can;
    }


}
