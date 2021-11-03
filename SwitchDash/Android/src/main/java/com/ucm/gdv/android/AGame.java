package com.ucm.gdv.android;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.SurfaceView;

import com.ucm.gdv.engineinterface.IGame;
import com.ucm.gdv.engineinterface.IGameState;
import com.ucm.gdv.engineinterface.IGraphics;
import com.ucm.gdv.engineinterface.IInput;

public class AGame extends SurfaceView implements IGame, Runnable {

    private AGraphics _graphics;

    private AInput _input;

    private AssetManager _assetManager;

    volatile boolean _running;

    private IGameState _currentState;

    private Thread _thread;

    private long _lastFrameTime ;
    private long _lastFPSInfo; // FPS' information
    int _frames;

    public AGame(Context context) {
        super(context);

        _assetManager = context.getAssets();


        _graphics = new AGraphics(_assetManager, this);

        _input = new AInput();


        // Establish the input object as listener of the screen touch
        setOnTouchListener(_input);

    }

    @Override
    public IGraphics getGraphics() {
        return _graphics;
    }

    @Override
    public IInput getInput() {
        return _input;
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
                //_graphics.setGraphics();
                _currentState.render();

                isRendered = _graphics.presentBuffer();

            }
        }
    }



    @Override
    public void setState(IGameState gameState) {

        _currentState = gameState;
    }

    @Override
    public void resume(){

        if(!_running){

            _running = true;

            _thread = new Thread(this);
            _thread.start();

        }
    }

    @Override
    public void pause(){

        if (_running) {

            _running = false;

            while (true) {
                try {
                    _thread.join();
                    _thread = null;
                    break;
                } catch (InterruptedException ie) {

                    System.out.print(ie.getMessage());
                }
            }
        }
    }

    @Override
    public void run() {

        if (_thread != Thread.currentThread()) {

            throw new RuntimeException("run() should not be called directly");
        }


        while(_running && getWidth() == 0)
            //Active waiting, is it better to sleep?
            ;

        _lastFrameTime = System.nanoTime();
        _lastFPSInfo = _lastFrameTime;
        _frames = 0;

        while(_running){
            _currentState.handleInput();
            update();
            render();
        }
    }




}