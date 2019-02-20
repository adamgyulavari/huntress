package com.greenfox.aze.huntress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.greenfox.aze.huntress.create.game.EditGameActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_create).setOnClickListener((view) -> {
            Intent i = new Intent(MainActivity.this, EditGameActivity.class);
            startActivity(i);
        });
    }
}
