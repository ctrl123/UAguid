package com.ctrl123.uaguid;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

public class Carte extends AppCompatActivity {

    public ImageView carte_ua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        photoView.setImageResource(R.drawable.carte_ua);
    }
}
