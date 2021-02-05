package edu.niu.cs.z1756423.csci428finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex on 3/19/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private CharacterSprite characterSprite;
    public BackgroundSprite backgroundSprite1, backgroundSprite2;
    public PipeSprite pipe1, pipe2, pipe3, pipe4, pipe5;
    public static int pipesPassed;
    public static double gravity;
    public static int GAP_HEIGHT = 500;
    public static int VELOCITY = 15;
    private int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;


    public GameView(Context context)
    {
        super(context);

        getHolder().addCallback(this);


        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        makeLevel();

        //create thread here to sync up activity and thread when returning from newGame
        thread = new MainThread(getHolder(), this);
        gravity = 0;
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        boolean retry = true;
        while(retry)
        {
            try
            {
                thread.setRunning(false);
                thread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update()
    {
        logic();
        //terminal velocity for the game
        if (gravity < 3)
            gravity += .2;
        characterSprite.update();
        backgroundSprite1.update();
        backgroundSprite2.update();
        pipe1.update();
        pipe2.update();
        pipe3.update();
        pipe4.update();
        pipe5.update();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int touchAction = event.getActionMasked();
        switch (touchAction)
        {
            case MotionEvent.ACTION_DOWN:
                if (SkinSelect.skin == 0)  //if koopa skin change the image to make it look like hes flapping his wings
                    characterSprite.updateImage(getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.flying_koopa_flap), 200, 160));
                gravity = -3; // set gravity to -3 so it going up on its parabolic arc
                return true;
            case MotionEvent.ACTION_UP:
                if (SkinSelect.skin == 0) //on release change the koopa skin back to regular
                    characterSprite.updateImage(getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.flying_koopa), 200, 160));
                break;
        }//end switch

        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas)
    {
        //code to display the counter for pipes passed
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(150);

        super.draw(canvas);
        if(canvas!=null)
        {
            backgroundSprite1.draw(canvas);
            backgroundSprite2.draw(canvas);
            characterSprite.draw(canvas);
            pipe1.draw(canvas);
            pipe2.draw(canvas);
            pipe3.draw(canvas);
            pipe4.draw(canvas);
            pipe5.draw(canvas);
            canvas.drawText(Integer.toString(pipesPassed), SCREEN_WIDTH/2, 200, paint);

            //draw outline and interior in two separate steps to create border
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(Color.BLACK);
            canvas.drawText(Integer.toString(pipesPassed), SCREEN_WIDTH/2, 200, paint);
        }
    }

    private void makeLevel() {
        if (SkinSelect.skin == 1) //if mario selected
            characterSprite = new CharacterSprite(getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.flying_mario), 200, 160));
        else
            characterSprite = new CharacterSprite(getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.flying_koopa), 200, 160));

        Bitmap bmp1, bmp2;
        Bitmap backgroundBmp;

        bmp1 = getResizedBitmap //resize to height/9 and height/2 due to l/w ratio of pipe images
                (BitmapFactory.decodeResource(getResources(), R.drawable.mario_pipe_down), SCREEN_HEIGHT/9, SCREEN_HEIGHT / 2);
        bmp2 = getResizedBitmap
                (BitmapFactory.decodeResource(getResources(), R.drawable.mario_pipe_up), SCREEN_HEIGHT/9, SCREEN_HEIGHT / 2);

        backgroundBmp = getResizedBitmap
                (BitmapFactory.decodeResource(getResources(), R.drawable.mario_background), SCREEN_HEIGHT * 2,
                        SCREEN_HEIGHT);
        //the background ratio is 2:1 for width/height so double the height of the screen to get the width

        backgroundSprite1 = new BackgroundSprite(backgroundBmp, 0, 0);
        backgroundSprite2 = new BackgroundSprite(backgroundBmp, SCREEN_HEIGHT*2, 0);

        pipesPassed = 0;

        Random r = new Random();
        int value1 = r.nextInt(500);
        int value2 = r.nextInt(500);
        int value3 = r.nextInt(500);
        int value4 = r.nextInt(500);
        int value5 = r.nextInt(500);
        pipe1 = new PipeSprite(bmp1, bmp2, 2000, value1-250);
        pipe2 = new PipeSprite(bmp1, bmp2, 2800, value2-250);
        pipe3 = new PipeSprite(bmp1, bmp2, 3600, value3-250);
        pipe4 = new PipeSprite(bmp1, bmp2, 4400, value4-250);
        pipe5 = new PipeSprite(bmp1, bmp2, 5200, value5-250);

    }

    public void logic() {

        List<PipeSprite> pipes = new ArrayList<>();
        pipes.add(pipe1);
        pipes.add(pipe2);
        pipes.add(pipe3);
        pipes.add(pipe4);
        pipes.add(pipe5);
        Random r = new Random();

        for (int i = 0; i < pipes.size(); i++) {
            //Detect if the character is touching one of the pipes
            if (characterSprite.y < pipes.get(i).yCord + (SCREEN_HEIGHT / 2) - (GAP_HEIGHT / 2)
                    && characterSprite.x + 200 > pipes.get(i).xCord
                    && characterSprite.x < pipes.get(i).xCord + 500)
            {
                gameOver();
            }
            else if (characterSprite.y + 160 > (SCREEN_HEIGHT / 2) + (GAP_HEIGHT / 2) + pipes.get(i).yCord
                    && characterSprite.x + 200 > pipes.get(i).xCord
                    && characterSprite.x < pipes.get(i).xCord + 500)
            {
                gameOver();
            }

            //count the number of pipes passed
            if (characterSprite.x >= pipes.get(i).xCord && pipes.get(i).passed == 0)
            {
                pipesPassed++;
                pipes.get(i).passed = 1;
            }

            //Detect if the pipe has gone off the left of the screen and regenerate further ahead
            if (pipes.get(i).xCord + 200 < 0)
            {

                int value1 = r.nextInt(200);
                int value2 = r.nextInt(500);
                pipes.get(i).xCord = SCREEN_WIDTH + 2800 + value1;
                pipes.get(i).yCord = value2 - 250;
                pipes.get(i).passed = 0;
            }


        }

        //Detect if the character has gone off the bottom or top of the screen
        if (characterSprite.y + 240 < 0) {
            gameOver(); }
        if (characterSprite.y > SCREEN_HEIGHT) {
            gameOver(); }

        if (backgroundSprite1.xCord + SCREEN_HEIGHT*2 < 0)
        {
            backgroundSprite1.xCord += SCREEN_HEIGHT*3; //use *3 to place it after the previous background image ends since its 2 wide
        }
        if (backgroundSprite2.xCord + SCREEN_HEIGHT*2 < 0)
        {
            backgroundSprite2.xCord += SCREEN_HEIGHT*3;
        }
    }

    public void gameOver() {
        thread.setRunning(false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("Score", 0);

        //store new high score if so
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(pipesPassed > highScore) {
            editor.putInt("Score", pipesPassed);
            editor.apply();
        }

        Context context = getContext();

        Intent intent = new Intent(context, NewGame.class);

        context.startActivity(intent);
        }

        //code used to reset the level on failure before the title screen was put in
    /*
   public void resetLevel() {
        gravity = 0;
        pipesPassed = 0;
        characterSprite.y = 100;
        backgroundSprite1.xCord = 0;
        backgroundSprite2.xCord = SCREEN_HEIGHT*2;

        pipe1.xCord = 2000;
        pipe1.yCord = 0;
        pipe1.passed = 0;

        pipe2.xCord = 2800;
        pipe2.yCord = 200;
        pipe2.passed = 0;

        pipe3.xCord = 3600;
        pipe3.yCord = 250;
        pipe3.passed = 0;

        pipe4.xCord = 4400;
        pipe4.yCord = 225;
        pipe4.passed = 0;

        pipe5.xCord = 5200;
        pipe5.yCord = 175;
        pipe5.passed = 0;

    } */
}
