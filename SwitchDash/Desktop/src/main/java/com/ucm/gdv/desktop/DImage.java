package com.ucm.gdv.desktop;

import com.ucm.gdv.engineinterface.IImage;
import java.awt.Image;

public class DImage implements IImage{

    private Image _image;

    public DImage(Image image){

        _image = image;
    }

    @Override
    public int getWidth() {

        return _image.getWidth(null);
    }

    @Override
    public int getHeight() {

        return _image.getHeight(null);
    }

    public Image getImage(){

        return _image;
    }
}
