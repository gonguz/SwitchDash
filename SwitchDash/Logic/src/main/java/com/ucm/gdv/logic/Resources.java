package com.ucm.gdv.logic;


import com.ucm.gdv.engineinterface.IGraphics;
import com.ucm.gdv.engineinterface.IImage;

import java.util.HashMap;
import java.util.Map;

public class Resources {

    private static Resources _resourcesManager;

    private Map<String, ImageProperties> _resources;

    private int[] _backgroundColors;

    private Resources(){

        _backgroundColors = new int[]{0x41a85f, 0x00a885, 0x3d8eb9, 0x2969b0, 0x553982, 0x28324e, 0xf37934, 0xd14b41 ,0x75706b};
    }

    public static Resources getResources(){

        if(_resourcesManager == null){
            _resourcesManager = new Resources();
        }

        return _resourcesManager;
    }


    class ImageProperties{

        public ImageProperties(int rows, int cols){

            _rows = rows;
            _cols = cols;


        }

        public int _rows;

        public int _cols;

        private IImage _image;

        public IImage get_image(){
            return _image;
        }

        public void set_image(IImage image){
            _image = image;
        }

    }




    public void loadResources(IGraphics graphics){

        _resources = new HashMap<String,ImageProperties>();

        String[] images = {"arrowsBackground","backgrounds", "balls", "buttons", "gameOver", "howToPlay", "instructions", "playAgain", "players", "scoreFont",
                "switchDashLogo","tapToPlay","white"};

        ImageProperties[] imagesProperties = {new ImageProperties(1, 1), new ImageProperties(1, 9), new ImageProperties(2, 10),
                new ImageProperties(1, 10), new ImageProperties(1, 1), new ImageProperties(1, 1), new ImageProperties(1, 1),
                new ImageProperties(1, 1), new ImageProperties(2, 1), new ImageProperties(7, 15),
                new ImageProperties(1, 1), new ImageProperties(1, 1), new ImageProperties(1, 1)};

        int i = 0;
        for (String image : images) {

            String name = image + ".png";

            IImage img = graphics.newImage(name);

            imagesProperties[i].set_image(img);

            if(img != null) {
                _resources.put(image, imagesProperties[i]);
                i++;
            }
            else{
                //throw new IncorrectFileNameException(name);
            }

        }
    }

    public IImage getImage(String name){

        return _resources.get(name).get_image();
    }

    public int[] getImageProperties(String name){

        ImageProperties ip = _resources.get(name);

        int[] rect = {ip._rows, ip._cols};

        return rect;
    }

    public int getBackgroundColor(int color) {

        if(color < _backgroundColors.length &&  color > -1)
            return _backgroundColors[color];
        else
            return _backgroundColors[0]; // Default color value if the parameter is out of the arrays' range
    }



}
