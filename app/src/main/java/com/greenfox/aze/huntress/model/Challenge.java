package com.greenfox.aze.huntress.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;

public class Challenge {
    public String name;

    @Exclude
    public LatLng location;

    public Challenge() {
    }

    public Challenge(String name) {
        this.name = name;
    }

    public Challenge(String name, LatLng location) {
        this.name = name;
        this.location = location;
    }
}
