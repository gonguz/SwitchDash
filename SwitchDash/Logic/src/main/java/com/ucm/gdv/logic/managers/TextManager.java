package com.ucm.gdv.logic.managers;

import com.ucm.gdv.engineinterface.IGraphics;
import com.ucm.gdv.logic.Resources;
import com.ucm.gdv.logic.Sprite;


// IDEA: CREAR UN MAP <char, {x, y (posicion en el sprite sheet)}>;
//  el map se rellena al principio: las letras con
//             int asciiNum = text.charAt(i) - 48;
//            int posX = (asciiNum % _spriteCols);
//            int posY = (asciiNum / _spriteRows / 2);
//  los numeros con
//              int asciiNum = text.charAt(i) - 48;
//            int posX = (asciiNum % _spriteCols);
//            int posY = (asciiNum / _spriteRows);
//  y los demas simbolos a mano  despues pa dibujar un texto simplemente se pide al map el caracter para obtener sus coordenas en el spritesheet

/**
 *  Class used for drawing points and text
 */

public class TextManager {

    // Sprite that contains all the characters (letters, numbers, symbols,...)
    private Sprite _pointsImageFirst;
    // Properties of the characters image (cols and rows)
    private int[] _imageProperties;

    private int _spriteCols;
    private int _spriteRows;

    // Reference positions in the sprite sheet for the numbers
    private int _pointsCol;
    private int _pointsRow;

    private IGraphics _graphics;

    public TextManager(IGraphics graphics){

        _graphics = graphics;

        _pointsCol = 7;
        _pointsRow = 3;

        _imageProperties = Resources.getResources().getImageProperties("scoreFont");

        _spriteRows = _imageProperties[0];
        _spriteCols = _imageProperties[1];

        _pointsImageFirst = new Sprite(Resources.getResources().getImage("scoreFont"),
                _spriteRows, _spriteCols,0, 0);
    }

    /**
     * From a text (only numbers) given shows it on the screen as an image. Uses 48 ascii code
     *
     * @param text to be shown
     * @param x position on the window
     * @param y position on the window
     * @param scale factor
     */
    public void drawPoints(String text, int x, int y, float scale){

        for(int i = 0; i < text.length(); i++){
            int asciiNum = text.charAt(i) - 48;
            int posX = (asciiNum % _spriteCols);
            int posY = (asciiNum / _spriteRows);
            int newX = 0;
            if(posY > 0 && posX != (_spriteCols - _pointsCol - 1)){
                newX = _spriteCols - _pointsCol;
                posX -= newX;
                _pointsImageFirst.set_x(posX);
                _pointsImageFirst.set_y(posY + _pointsRow);
            }else {
                if(posX != _spriteCols - _pointsCol - 1) {
                    _pointsImageFirst.set_x(posX + _pointsCol);
                    //System.out.println(posX + " " + (_spriteCols - _pointsCol - 1));
                    _pointsImageFirst.set_y(posY + _pointsRow);
                }else{
                    _pointsImageFirst.set_x(posX + _pointsCol);
                    _pointsImageFirst.set_y(posY + _pointsRow - 1);
                }
            }



            float charSeparation = (int)(((_pointsImageFirst.get_width() * _graphics.getScale() / 2) * (-i)));
            float textHalf = ((text.length() * (_pointsImageFirst.get_width()  * _graphics.getScale()))) / 2;

            _graphics.drawImageScaled(_pointsImageFirst.getImage(), (int)(x - charSeparation - (textHalf / 2)), y,
                    (int)(_pointsImageFirst.get_width() * scale), (int)(_pointsImageFirst.get_height() * scale), _pointsImageFirst.get_x(), _pointsImageFirst.get_y(), _pointsImageFirst.get_width(), _pointsImageFirst.get_height(), 1.0f);
            //System.out.println("ASCII: " + asciiNum + " X: " + newX +" Y: " + _pointsImageFirst.get_y());

        }
    }


    /**
     * From a text (only letters) given shows it on the screen as an image. Uses 65 ascii code
     *
     * @param text to be shown
     * @param x position on the window
     * @param y position on the window
     * @param scale factor
     */
    public void drawText(String text, int x, int y, float scale){
        for(int i = 0; i < text.length(); i++){
            int asciiNum = text.charAt(i) - 65;
            int posX = (asciiNum % _spriteCols);
            int posY = (asciiNum / _spriteRows /2);
            //System.out.println(asciiNum + " X: " + posX +" Y: " + posY);
            if(posY == 1 && posX > 10){
                posY -= 1;
            }
            _pointsImageFirst.set_x(posX);
            _pointsImageFirst.set_y(posY);


            float charSeparation = (int)(((_pointsImageFirst.get_width() * _graphics.getScale() / 1.75) * (-i)));
            float textHalf = ((text.length() * (_pointsImageFirst.get_width()  * _graphics.getScale()))) / 2;


            _graphics.drawImageScaled(_pointsImageFirst.getImage(), (int)(x - charSeparation - (textHalf / 2)), y,
                    (int)(_pointsImageFirst.get_width() * scale), (int)(_pointsImageFirst.get_height() * scale), _pointsImageFirst.get_x(), _pointsImageFirst.get_y(), _pointsImageFirst.get_width(), _pointsImageFirst.get_height(), 1.0f);
        }
    }
}
