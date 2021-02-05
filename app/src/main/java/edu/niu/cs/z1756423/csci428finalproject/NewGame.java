package edu.niu.cs.z1756423.csci428finalproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by Alex on 4/25/2018.
 */

public class NewGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //same code as MainActivity because you cannot branch to it from an intent from gameView

        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("Score", 0);

        TextView score = findViewById(R.id.actualScoreTV);
        TextView highScoreTextView = findViewById(R.id.highScoreTV);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/SuperMario256.ttf");
        highScoreTextView.setTypeface(typeface);
        score.setTypeface(typeface);

        score.setText(String.valueOf(highScore));

        ImageButton playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageButton skinButton = findViewById(R.id.skinButton);

        skinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SkinSelect.class);

                startActivity(intent);
            }
        });
    }

}
