package com.ucm.gdv.desktop;
import com.ucm.gdv.engineinterface.IGame;
import com.ucm.gdv.engineinterface.IGameState;
import com.ucm.gdv.engineinterface.IGraphics;
import com.ucm.gdv.engineinterface.IInput;

import javax.swing.JFrame;



public class DGame implements IGame {


    private class Window extends JFrame /*implements ComponentListener*/ {

        public Window(String title){
            super(title);


        }


    }
    private DGraphics _graphics;

    private DInput _input;

    private IGameState _currentState;

    private volatile boolean _active;

    private long _lastFrameTime ;
    private long _lastFPSInfo; // FPS' information
    int _frames;

    public DGame(){

        // Create window and set properties
        Window window = new Window("SwitchDash");
        window.setSize(720,1080);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setIgnoreRepaint(true);
        window.setVisible(true);

        _graphics = new DGraphics(window);

        _input = new DInput();
        // Add input object as mouse to get the desired events
        window.addMouseListener(_input);

        _active = true;

        _frames = 0;

        _graphics.setGraphics();

    }

    // Retrieve the graphics object
    @Override
    public IGraphics getGraphics() {
        return _graphics;
    }

    // Retrieve the input object
    @Override
    public IInput getInput() {
        return _input;
    }

    @Override
    public void pause() {

        _active = false;
    }

    @Override
    public void resume() {

        _active = true;
    }

    @Override
    public void setState(IGameState gameState){

        _currentState = gameState;
    }

    @Override
     public void update() {

       /* long lastFrameTime = System.nanoTime();
        long lastFPSInfo = lastFrameTime; // FPS' information
        int frames = 0;*/

        //final int TARGET_FPS = 60;
        //final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        long currentTime = System.nanoTime();
        long nanoElapsedTime = currentTime - _lastFrameTime;
        _lastFrameTime = currentTime;
        double elapsedTime = (double) (nanoElapsedTime / 1.0E9);

        _currentState.update(elapsedTime);

        if (currentTime - _lastFPSInfo > 1000000000l) {
            //long fps = _frames * 1000000000l / (currentTime - _lastFPSInfo) % 60;
            //System.out.println("" + fps + " fps");
            _frames = 0;
            _lastFPSInfo = currentTime;
        }
        ++_frames;




    }

    @Override
    public void render() {
        boolean isRendered = false;

        while(!isRendered) {
            //_graphics.clear(0);
            if(!_graphics.prepareBuffer())
                isRendered = true;
            else {
                _graphics.setGraphics();
                _currentState.render();

                isRendered = _graphics.presentBuffer();

            }
        }
    }


    public void run() {

       _lastFrameTime = System.nanoTime();
       _lastFPSInfo = _lastFrameTime;
       _frames = 0;

        while (_active){
            _currentState.handleInput();
            update();
            render();
        }
    }
}
