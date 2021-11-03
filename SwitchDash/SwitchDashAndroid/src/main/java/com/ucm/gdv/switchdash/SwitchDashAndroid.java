package com.ucm.gdv.switchdash;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.ucm.gdv.android.AGame;
import com.ucm.gdv.logic.Resources;
import com.ucm.gdv.logic.states.StartState;

/**
 * App Activity that starts the game
 */
public class SwitchDashAndroid extends Activity {

    private AGame _game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        _game = new AGame(this);

        // Initializes the game resources
        Resources.getResources().loadResources(_game.getGraphics());

        _game.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(_game);

        // Creates the first state and its set in the game
        StartState startState = new StartState(_game);
        _game.setState(startState);

    }

    @Override
    protected void onResume(){
        super.onResume();

        _game.resume();

        _game.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    @Override
    protected  void onPause(){
        super.onPause();

        _game.pause();

    }
}
