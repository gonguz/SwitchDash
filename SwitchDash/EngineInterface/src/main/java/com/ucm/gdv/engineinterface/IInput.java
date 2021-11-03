package com.ucm.gdv.engineinterface;

import java.util.List;

/**
 *
 * Grants the basic input functionalities
 */
public interface IInput {

    List<TouchEvent> getTouchEvents();

    /**
     *
     * Class that represents the touch information on the screen or mouse event
     */
    class TouchEvent{

        public TouchEvent(int x, int y, int id, ACTION event){

            _x = x;
            _y = y;
            _buttonId = id;
            _type = event;

        }


        /**
         *
         * @return the touch type event (pressed, released or dragged)
         */
        public ACTION getType(){

            return _type;
        }

        public int getId()
        {
            return _buttonId;
        }

        public int getX() {

            return _x;
        }

        public int getY() {

            return _y;
        }

        private int _x;
        private int _y;

        /**
         *  Touches actions types
         */
        public enum ACTION{
            PRESSED,
            RELEASED,
            DRAGGED
        }

        ACTION _type;

        int _buttonId;

    }
}

