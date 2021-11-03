package com.ucm.gdv.desktop;
import com.ucm.gdv.engineinterface.IGraphics;
import com.ucm.gdv.engineinterface.IImage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;




public class DGraphics implements IGraphics{

    private JFrame _window;
    private BufferStrategy _bufferStrategy;

    private Graphics2D _graphics2D;

    private float _scale;

    private float [] _crop;


    public DGraphics(JFrame window){

        _window = window;

        _crop = new float[2];
        _crop[0] = 0.0f;
        _crop[1] = 0.0f;

        _scale = 1.0f;

        int tries = 100;

        while(tries-- >0)
        {
            try
            {
                _window.createBufferStrategy(2);
                break;
            }
            catch (Exception e)
            {
                System.out.print("Couldn't built a buffer strategy");
                e.getCause();
            }
        }

        _bufferStrategy = _window.getBufferStrategy();


    }

    // Creates a new DImage object that wraps a java.awt.Image
    @Override
    public IImage newImage(String name) {

        Image sprite;

        try{

            sprite = ImageIO.read(new File("Assets/" + name));

        }catch(IOException io){

            io.getStackTrace();
            return null;
        }

        return new DImage(sprite);
    }

    @Override
    public void clear(int color) {
        _graphics2D.setColor( new Color(color));

        _graphics2D.fillRect(0, 0, getWidth(), getHeight());
    }



    @Override
    public void clearCrop(int color, int x, int y, int w, int h) {
        try{
            Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1.0f);
            _graphics2D.setComposite(alphaComp);
        }
        catch(Exception e){
            System.err.println("Alpha couldn't be applied");
        }
        _graphics2D.setColor( Color.BLACK);
        _graphics2D.fillRect(x, y, w, h);
    }

    @Override
    public void drawImage(IImage image, int x, int y, int width, int height, int xImage, int yImage, int widthImage, int heightImage, float alpha) {

        // x, y, width, height ->  used for calculating the destination image (position of the 4 corners)
        // xImage, yImage -> used for calculating the source image from the sprite sheet

        if(alpha < 0) alpha = 0;
        else if (alpha > 1) alpha = 1;

        try{
            Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , alpha);
            _graphics2D.setComposite(alphaComp);
        }
        catch(Exception e){
            System.err.println("Alpha couldn't be applied");
        }

        _graphics2D.drawImage(((DImage) image).getImage(), x , y ,  x + width, y + height,
                xImage, yImage, xImage + widthImage, yImage + heightImage, null);
    }

    @Override
    public void drawImageScaled(IImage image, int x, int y, int width, int height, int xImage, int yImage, int widthImage, int heightImage, float alpha) {

        // x, y, width, height ->  used for calculating the destination image (position of the 4 corners)
        // xImage, yImage -> used for calculating the source image from the sprite sheet

        if(alpha < 0) alpha = 0;
        else if (alpha > 1) alpha = 1;

        try{
            Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , alpha);
            _graphics2D.setComposite(alphaComp);
        }
        catch(Exception e){
            System.err.println("Alpha couldn't be applied");
        }
        _graphics2D.drawImage(((DImage) image).getImage(), x + (int)_crop[0], y + (int)_crop[1],  x + (int)_crop[0] + (int)((float)width * _scale), y + (int)_crop[1] + (int)((float)height * _scale),
                xImage, yImage, xImage + widthImage, yImage + heightImage, null);
    }



    @Override
    public int getWidth() {
        return _window.getWidth();
    }

    @Override
    public int getHeight() {
        return _window.getHeight();
    }


    // Makes the next available buffer visible
    @Override
    public boolean presentBuffer(){

        _bufferStrategy.getDrawGraphics().dispose();

        if(_bufferStrategy.contentsRestored()){
            return false;
        }
        else{

            _bufferStrategy.show();

            if(_bufferStrategy.contentsLost()){
                return false;
            }
            else
                return true;
        }
    }

    // Check if the drawing buffer is ready to post its content
    @Override
    public boolean prepareBuffer(){
        if( _bufferStrategy.getDrawGraphics() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void setScale(float scale) {

        _scale = scale;
    }

    @Override
    public float getScale() {

        return _scale;
    }

    @Override
    public void setCrop(float[] crop) {

        _crop[0] = crop[0];
        _crop[1] = crop[1];

    }

    @Override
    public float[] getCrop() {

        return _crop;
    }


    public void setGraphics(){

        _graphics2D =  (Graphics2D) _bufferStrategy.getDrawGraphics();
    }
}
