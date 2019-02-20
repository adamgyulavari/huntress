package com.greenfox.aze.huntress;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateActivity extends AppCompatActivity {

    public static final int PLACE_PICKER_REQUEST = 1;
    public static final String LOCATION_EXTRA = "location";

    EditText name, location;
    Button create;
    LatLng start;
    DatabaseReference database;
    GeoFire geoFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance().getReference();
        geoFire = new GeoFire(FirebaseDatabase.getInstance().getReference("locations"));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        });

        name = findViewById(R.id.input_name);
        location = findViewById(R.id.input_location);
        location.setFocusable(false);
        create = findViewById(R.id.button_create);

        location.setOnClickListener((view) -> {
            Intent i = new Intent(CreateActivity.this, MapsActivity.class);
            if (start != null) {
                i.putExtra(LOCATION_EXTRA, start);
            }
            startActivityForResult(i, PLACE_PICKER_REQUEST);
        });

        create.setOnClickListener(v -> {
            String key = database.child("games").push().getKey();
            database.child("games/"+key).setValue(new Game(name.getText().toString()));
            geoFire.setLocation(key, new GeoLocation(start.latitude, start.longitude), (key1, error) -> {});
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        start = data.getParcelableExtra(LOCATION_EXTRA);
        location.setText(start.latitude + ", " + start.longitude);
    }
}
