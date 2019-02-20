package com.greenfox.aze.huntress.create.game;

import android.support.annotation.NonNull;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.LocationCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greenfox.aze.huntress.model.Game;

public class GameEditPresenter {

    View view;

    LatLng start;
    DatabaseReference database;
    GeoFire geoFire;
    Game game;
    String key;

    public GameEditPresenter(View view) {
        this(view, null);
    }

    public GameEditPresenter(View view, String key) {
        this.view = view;
        this.key = key;

        database = FirebaseDatabase.getInstance().getReference();
        geoFire = new GeoFire(FirebaseDatabase.getInstance().getReference("locations"));
        if (key != null) {
            database.child("games/" + key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    game = dataSnapshot.getValue(Game.class);
                    geoFire.getLocation(key, new LocationCallback() {
                        @Override
                        public void onLocationResult(String key, GeoLocation location) {
                            start = new LatLng(location.latitude, location.longitude);
                            update();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {

        }
    }

    public LatLng getStart() {
        return start;
    }

    public void saveGame(String name) {
        game.name = name;
        if (key == null) {
            key = database.child("games").push().getKey();
        }
        database.child("games/"+key).setValue(game);
        geoFire.setLocation(key, new GeoLocation(start.latitude, start.longitude), (key1, error) -> {});
    }

    public void updateStart(LatLng start) {
        this.start = start;
        update();
    }

    private void update() {
        if (view != null) {
            view.gameUpdated(game, start);
        }
    }

    public interface View {
        void gameUpdated(Game game, LatLng start);
    }
}
