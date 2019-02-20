package com.greenfox.aze.huntress.create.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.greenfox.aze.huntress.MapsActivity;
import com.greenfox.aze.huntress.R;
import com.greenfox.aze.huntress.model.Challenge;
import com.greenfox.aze.huntress.model.Game;

public class EditGameActivity extends AppCompatActivity implements GameEditPresenter.View {

    public static final int PLACE_PICKER_REQUEST = 1;
    public static final String LOCATION_EXTRA = "location";
    public static final String GAME_KEY = "game_key";

    EditText name, location;
    Button create;
    RecyclerView challenges;

    GameEditPresenter presenter;
    ChallengeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.game_details));
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null && getIntent().getStringExtra(GAME_KEY) != null) {
            presenter = new GameEditPresenter(this, getIntent().getStringExtra(GAME_KEY));
        } else {
            presenter = new GameEditPresenter(this);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        });

        name = findViewById(R.id.input_name);
        location = findViewById(R.id.input_location);
        location.setFocusable(false);
        create = findViewById(R.id.button_create);
        challenges = findViewById(R.id.challenge_list);
        challenges.setNestedScrollingEnabled(false);
        challenges.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        adapter = new ChallengeAdapter();
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        adapter.add(new Challenge("asd"));
        SimpleTouchCallback callback = new SimpleTouchCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(challenges);
        challenges.setLayoutManager(new LinearLayoutManager(this));
        challenges.setAdapter(adapter);

        location.setOnClickListener((view) -> {
            Intent i = new Intent(EditGameActivity.this, MapsActivity.class);
            if (presenter.getStart() != null) {
                i.putExtra(LOCATION_EXTRA, presenter.getStart());
            }
            startActivityForResult(i, PLACE_PICKER_REQUEST);
        });

        create.setOnClickListener(v -> {
            presenter.saveGame(name.getText().toString());
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            presenter.updateStart(data.getParcelableExtra(LOCATION_EXTRA));
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void gameUpdated(Game game, LatLng start) {
        location.setText(start.latitude + ", " + start.longitude);
        name.setText(game.name);
    }
}
