package com.greenfox.aze.huntress;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class HuntressApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new FontAwesomeModule());
    }
}
