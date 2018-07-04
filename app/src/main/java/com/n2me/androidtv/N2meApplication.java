package com.n2me.androidtv;

import android.app.Application;
import android.content.Context;

public class N2meApplication extends Application {

    private static N2meApplication instance = null;
    private Context appContext = null;

    public static N2meApplication getInstance() {
        if (instance == null)
            instance = new N2meApplication();

        return instance;
    }

    public static Context getAppContext() {
        return instance.appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();
        instance = this;
    }

}