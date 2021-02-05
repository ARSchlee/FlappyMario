package edu.niu.cs.z1756423.csci428finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Type;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //retrieve the high score using sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("Score", 0);

        TextView score = findViewById(R.id.actualScoreTV);
        TextView highScoreTextView = findViewById(R.id.highScoreTV);

        //change the font to custom mario type
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/SuperMario256.ttf");
        highScoreTextView.setTypeface(typeface);
        score.setTypeface(typeface);

        score.setText(String.valueOf(highScore));

        ImageButton playButton = findViewById(R.id.playButton);
        ImageButton skinButton = findViewById(R.id.skinButton);

        skinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SkinSelect.class);
                //intent to go to the skin selector menu
                startActivity(intent);
            }
        });

        final GameView gameView = new GameView(this);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(gameView);
            }
        });

        //setContentView(new GameView(this));
    }

}
