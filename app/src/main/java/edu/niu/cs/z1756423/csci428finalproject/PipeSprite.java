package edu.niu.cs.z1756423.csci428finalproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Alex on 3/19/2018.
 */

public class PipeSprite {

    private Bitmap image1, image2;
    public int xCord, yCord, passed;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public PipeSprite (Bitmap bmp1, Bitmap bmp2, int x, int y)
    {
        image1 = bmp1;
        image2 = bmp2;
        xCord = x;
        yCord = y;
        passed = 0;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image1, xCord, -(GameView.GAP_HEIGHT / 2) + yCord, null);
        canvas.drawBitmap(image2, xCord, ((screenHeight / 2) + (GameView.GAP_HEIGHT / 2)) + yCord, null);
    }

    public void update()
    {
        xCord -= GameView.VELOCITY;
    }
}
