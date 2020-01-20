package com.ucm.gdv.logic;


import com.ucm.gdv.engineinterface.IImage;

/**
 * Class that wraps an IImage and calculates the wanted image in the sprite sheet (the big image that contains various sprites, source image).
 * If the image we want to use is the whole original image then x=y=0 and rows=cols=1.
 *
 */
public class Sprite{

    /**
     * Sprite constructor
     *
     * @param image is the source image
     * @param rows is the number of rows inside the source image, used to divide the sprite sheet and get the number of sprites
     * @param cols is the number of cols inside the source image, used to divide the sprite sheet and get the number of sprites
     * @param x
     * @param y
     */

    public Sprite(IImage image, int rows, int cols, int x, int y){

        _image = image;

        _rows = rows;
        _cols = cols;

        // Calculate the width of each sprite inside the original image
        _width = _image.getWidth() / _cols;
        _height = _image.getHeight() / _rows;

        // Calculate the top left corner in the original image where the desired sprite start
        _x = x * _width;
        _y = y * _height;

    }

    /**
     *
     * @return the IImage object that contains an image (source image) that will be used to get the destiny image
     */
    public IImage getImage(){
        return _image;
    }


    /**
     *
     * @return the sprite width
     */
   public int get_width(){
        return _width;
   }

    /**
     *
     * @return the sprite height
     */
    public int get_height(){
        return _height;
    }

    /**
     *
     * @return the sprite x coordinate in the sprite sheet (source image)
     */
    public int get_x() {
        return _x;
    }

    /**
     *
     * @return the sprite y coordinate in the sprite sheet (source image)
     */
    public int get_y() {
        return _y;
    }

    public void set_x(int newX) {_x = newX * _width;}
    public void set_y(int newY) {_y = newY * _height;}




    /**
     * Private attributes
     */
    private int _x;
    private int _y;

    private int _width;
    private int _height;

    private int _rows;
    private int _cols;

    private IImage _image;

}