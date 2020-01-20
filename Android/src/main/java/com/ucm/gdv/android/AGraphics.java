package com.ucm.gdv.android;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.graphics.Rect;

import com.ucm.gdv.engineinterface.IGraphics;
import com.ucm.gdv.engineinterface.IImage;

import java.io.IOException;
import java.io.InputStream;

public class AGraphics implements IGraphics{

    private SurfaceView _surfaceView;
    private AssetManager _assetManager;
    private Canvas _canvas;

    // Object used for managing the images alpha
    private Paint _paint;

    private float _scale;

    private float [] _crop;

    public AGraphics(AssetManager assetManager, SurfaceView surfaceView){

        _surfaceView = surfaceView;
        _assetManager = assetManager;

        _paint = new Paint();

        _crop = new float[2];
        _crop[0] = 0.0f;
        _crop[1] = 0.0f;

        _scale = 0.5f;
    }

    // Creates a new AImage object that wraps a bitmap sprite
    @Override
    public IImage newImage(String name) {


        Bitmap sprite;

        try{
            InputStream inputStream = _assetManager.open(name);
            sprite = BitmapFactory.decodeStream(inputStream);
        }
        catch (IOException io){

            io.getStackTrace();
            return null;
        }

        return new AImage(sprite);
    }

    // Draw the canvas with the given color
    @Override
    public void clear(int color) {


        _canvas.drawRGB(Color.red(color), Color.green(color), Color.blue(color));
    }

    @Override
    public void clearCrop(int color, int x, int y, int w, int h) {

    }


    // Draw the the sprite in AImage in the canvas
    // x,y: screen position; width, height: size of the destination rect; xImage, yImage: source image positions
    @Override
    public void drawImage(IImage image, int x, int y, int width, int height, int xImage, int yImage, int widthImage, int heightImage, float alpha) {

        Rect src = new Rect(xImage, yImage, xImage + widthImage, yImage + heightImage);
        Rect dest  = new Rect(x , y , x + width , y + height );

        // Conversion to int due to the parameter in the interface method
        int intAlpha = (int)(alpha * 255);
        _paint.setAlpha(intAlpha);


        _canvas.drawBitmap(((AImage) image).getImage(), src, dest, _paint);

    }


    // Draw the the sprite in AImage in the canvas
    // x,y: screen position; width, height: size of the destination rect; xImage, yImage: source image positions
    @Override
    public void drawImageScaled(IImage image, int x, int y, int width, int height, int xImage, int yImage, int widthImage, int heightImage, float alpha) { // TODO: ALPHA---> PAINT:SETALPHA()

        Rect src = new Rect(xImage, yImage, xImage + widthImage, yImage + heightImage);
        Rect dest  = new Rect(x + (int)_crop[0], y + (int)_crop[1], x +  (int)_crop[0] + (int)((float)width * _scale), y +  (int)_crop[1] + (int)((float)height * _scale));

        // Conversion to int due to the parameter in the interface method
        int intAlpha = (int)(alpha * 255);
        _paint.setAlpha(intAlpha);


        _canvas.drawBitmap(((AImage) image).getImage(), src, dest, _paint);

    }


    @Override
    public int getWidth() {
        return _surfaceView.getWidth();
    }

    @Override
    public int getHeight() {
        return _surfaceView.getHeight();
    }

    // Post the canvas content
    @Override
    public boolean presentBuffer() {

        _surfaceView.getHolder().unlockCanvasAndPost(_canvas);

        return true;
    }

    // Prepare the canvas to draw after
    @Override
    public boolean prepareBuffer() {

        if( _surfaceView.getHolder().getSurface().isValid()) {

            _canvas =  _surfaceView.getHolder().lockHardwareCanvas(); //lockCanvas()
            _canvas.save();

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
}
