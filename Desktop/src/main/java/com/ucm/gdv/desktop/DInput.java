package com.ucm.gdv.desktop;
import com.ucm.gdv.engineinterface.IInput;


//import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class DInput implements IInput, MouseListener, MouseMotionListener/*, KeyListener*/ {


    public DInput(){

        _touchEventList = new ArrayList<>();
    }


    @Override
    public List<IInput.TouchEvent> getTouchEvents() {

      List<TouchEvent> aux = (ArrayList)_touchEventList.clone();

        if(!_touchEventList.isEmpty())
            _touchEventList.clear();

        return aux;

    }

    private ArrayList<IInput.TouchEvent> _touchEventList;


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        TouchEvent touchEvent = new TouchEvent(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getID(), TouchEvent.ACTION.PRESSED);
        _touchEventList.add(touchEvent);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        TouchEvent touchEvent = new TouchEvent(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getID(), TouchEvent.ACTION.RELEASED);
        _touchEventList.add(touchEvent);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
