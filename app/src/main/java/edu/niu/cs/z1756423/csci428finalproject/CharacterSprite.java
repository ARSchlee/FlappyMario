package edu.niu.cs.z1756423.csci428finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Alex on 3/19/2018.
 */

public class CharacterSprite {

    private Bitmap image;
    public float x,y;
    public float yVelocity = 3;


    public CharacterSprite(Bitmap bmp)
    {
        image = bmp;
        x = 100;
        y = 100;
    }

    public void updateImage(Bitmap bmp)
    {
        image = bmp;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update()
    {
        if (GameView.gravity < 0) //if character it meant to be going up square the velocity to keep it going up
            y += yVelocity*-(GameView.gravity*GameView.gravity); //based off of the speed of gravity... -9.8m/s^2
        else
            y += yVelocity*(GameView.gravity*GameView.gravity);
    }
}
