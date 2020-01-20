package com.ucm.gdv.android;
import android.graphics.Bitmap;

import com.ucm.gdv.engineinterface.IImage;

public class AImage implements IImage{

    private Bitmap _image;

    public AImage(Bitmap image){

        _image = image;
    }

    @Override
    public int getWidth() {
        return _image.getWidth();
    }

    @Override
    public int getHeight() {
        return _image.getHeight();
    }

    public Bitmap getImage(){
        return _image;
    }
}
