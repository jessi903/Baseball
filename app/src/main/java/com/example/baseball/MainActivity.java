package com.example.baseball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Activate the game
    public void buttonClick1(View v){
        Intent game = new Intent(getApplicationContext(),GameActivity.class);
        startActivity(game);
    }

    //Activate the instruction
    public void buttonClick2(View v){
        Intent help = new Intent(getApplicationContext(),HelpActivity.class);
        startActivity(help);
    }
}