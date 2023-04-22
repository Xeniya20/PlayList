package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the click listener for the whole screen
        findViewById(android.R.id.content).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Create a new intent to start the new activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}