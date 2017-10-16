package com.ctrl123.uaguid;

import android.content.Intent;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button bouton_sport;
    public Button bouton_crous;
    public Button bouton_scolaire;
    public Button bouton_carte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bouton_sport = (Button) findViewById(R.id.button_sport);
        bouton_scolaire = (Button) findViewById(R.id.button_scolaire);
        bouton_crous = (Button) findViewById(R.id.button_crous);
        bouton_carte = (Button) findViewById(R.id.button_carte);

        bouton_sport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent page_sport = new Intent(MainActivity.this, Sport.class);
                startActivity(page_sport);
            }

        });

        bouton_scolaire.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent page_scolaire = new Intent(MainActivity.this, Scolaire.class);
                startActivity(page_scolaire);
            }

        });

        bouton_crous.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent page_crous = new Intent(MainActivity.this, Crous.class);
                startActivity(page_crous);
            }

        });

        bouton_carte.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent page_carte = new Intent(MainActivity.this, Carte.class);
                startActivity(page_carte);
            }

        });

    }
}
