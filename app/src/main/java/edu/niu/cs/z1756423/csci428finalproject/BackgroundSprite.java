package edu.niu.cs.z1756423.csci428finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Alex on 4/23/2018.
 */

public class BackgroundSprite {

    private Bitmap image;
    public int xCord, yCord;

    public BackgroundSprite (Bitmap bmp, int x, int y)
    {
        image = bmp;
        xCord = x;
        yCord = y;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, xCord, yCord, null);
    }

    public void update()
    {
        xCord -= GameView.VELOCITY;
    }
}
