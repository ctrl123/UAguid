package com.ctrl123.uaguid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Carte extends AppCompatActivity {

    public ImageView carte_ua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);

        carte_ua = (ImageView) findViewById(R.id.imageView6);
    }
}
