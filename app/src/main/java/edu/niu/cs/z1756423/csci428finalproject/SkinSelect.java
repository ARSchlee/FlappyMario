package edu.niu.cs.z1756423.csci428finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Alex on 4/30/2018.
 */

public class SkinSelect extends Activity{

    public static int skin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.skin_selector);

    ImageButton koopaButton = findViewById(R.id.koopaButton);

    koopaButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            skin = 0;
            finish();
        }
    });

    ImageButton marioButton = findViewById(R.id.marioButton);

    marioButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            skin = 1;
            finish();
        }
    });
}
}
