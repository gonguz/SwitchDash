package com.ucm.gdv.android;
import android.view.MotionEvent;
import android.view.View;

import com.ucm.gdv.engineinterface.IInput;


import java.util.ArrayList;
import java.util.List;

public class AInput implements IInput, View.OnTouchListener {

    private ArrayList<IInput.TouchEvent> _touchEventList;

    public AInput(){

        _touchEventList = new ArrayList<>();
    }


    @Override
    public synchronized List<IInput.TouchEvent> getTouchEvents() {

        List<TouchEvent> aux = (ArrayList)_touchEventList.clone();

        if(!_touchEventList.isEmpty())
            _touchEventList.clear();

        return aux;

    }

    // When a touch event happen
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                _touchEventList.add(new TouchEvent((int)event.getX(), (int)event.getY(), 0, TouchEvent.ACTION.PRESSED));
                return true;
            case MotionEvent.ACTION_UP:

                _touchEventList.add(new TouchEvent((int)event.getX(), (int)event.getY(), 0, TouchEvent.ACTION.RELEASED));

                break;
            case MotionEvent.ACTION_MOVE:

                _touchEventList.add(new TouchEvent((int)event.getX(), (int)event.getY(), 0, TouchEvent.ACTION.DRAGGED));
                break;
        }

        return false;
    }
}
