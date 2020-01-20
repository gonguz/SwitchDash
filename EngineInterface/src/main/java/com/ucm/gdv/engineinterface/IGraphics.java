package com.ucm.gdv.engineinterface;

/**
 * Grant the basic graphics functionalities within the app window
 */
public interface IGraphics {

    /**
     * Loads an image, stored in the app resources container, using its name
     *
     * @param name of the image to load
     * @return the IImage object that wraps the bitmap loaded
     */
    IImage newImage(String name);

    /**
     * Clears the window content filling it with the given color
     * @param color to draw the window with
     */
    void clear(int color);

    /**
     * Clears the window content within the specified rect, filling it with the given color
     * @param color to draw the window with
     * @param x left coordinate of the rect to draw
     * @param y top coordinate of the rect to draw
     * @param w right coordinate of the rect to draw
     * @param h bottom coordinate of the rect to draw
     */
    void clearCrop(int color, int x, int y, int w, int h);

    /**
     * Receive an image and shows it on the screen.
     *
     * @param image to draw on the screen
     * @param x left coordinate on the window to draw the destiny image
     * @param y top coordinate on the window to draw the destiny image
     * @param width right coordinate on the window to draw the destiny image
     * @param height bottom coordinate on the window to draw the destiny image
     * @param xImage left coordinate on the source image to get the image to draw
     * @param yImage top coordinate on the source image to get the image to draw
     * @param widthImage right coordinate on the source image to get the image to draw
     * @param heightImage bottom coordinate on the source image to get the image to draw
     * @param alpha represents the opacity of the image to draw
     */
    void drawImage(IImage image, int x, int y, int width, int height, int xImage, int yImage, int widthImage, int heightImage, float alpha);

    /**
     * Receive an image and shows it on the screen being scaled to the current resolution.
     *
     * @param image to draw on the screen
     * @param x left coordinate on the window to draw the destiny image
     * @param y top coordinate on the window to draw the destiny image
     * @param width right coordinate on the window to draw the destiny image
     * @param height bottom coordinate on the window to draw the destiny image
     * @param xImage left coordinate on the source image to get the image to draw
     * @param yImage top coordinate on the source image to get the image to draw
     * @param widthImage right coordinate on the source image to get the image to draw
     * @param heightImage bottom coordinate on the source image to get the image to draw
     * @param alpha represents the opacity of the image to draw
     */
    void drawImageScaled(IImage image, int x, int y, int width, int height, int xImage, int yImage, int widthImage, int heightImage, float alpha);

    /**
     * Retrieves the window width
     *
     * @return the window width
     */
    int getWidth();

    /**
     * Retrieves the window height
     *
     * @return the window height
     */
    int getHeight();

    /**
     * Makes the next available buffer visible
     *
     * @return if the the content has been showed
     */
    boolean presentBuffer();

    /**
     *  Check if the drawing buffer is ready to post its content
     *
     * @return if the buffer is
     */
    boolean prepareBuffer();

    /**
     * Establish the scale for drawing in the current resolution
     * @param scale desired scale
     */
    void setScale(float scale);

    /**
     *
     * @return the scale for drawing in the current resolution
     */
    float getScale();

    /**
     * Establish the coordinates where the logic space starts, these coordinates are used for drawing the images scaled
     * @param crop coordinates where the logic space starts
     */
    void setCrop(float [] crop);

    /**
     *
     * @return coordinates where the logic space starts
     */
    float[] getCrop();
}
