package com.n2me.androidtv.common.pref;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.n2me.androidtv.N2meApplication;
import com.n2me.androidtv.common.rest.model.Medium;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.n2me.androidtv.common.rest.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class N2MEPreference {

    public static String TAG = N2MEPreference.class.getSimpleName();

    public static final String RECENT_WATCHED = "RECENT_WATCHED";

    private static ArrayList<Medium> recentWatchedList;
    private static ArrayList<User> recentUsersList;

    public static void loadConfig() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(N2meApplication.getInstance().getAppContext());
        String json = sharedPreferences.getString(RECENT_WATCHED, "");
        Type type = new TypeToken<ArrayList<Medium>>(){}.getType();

        recentWatchedList = new Gson().fromJson(json, type);
        Log.v(TAG, "Loaded Recent Watched configuration information");

        if (recentWatchedList == null)
            recentWatchedList = new ArrayList<Medium>();
    }

    public static void writeConfig() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(N2meApplication.getInstance().getAppContext());
        if (recentWatchedList == null)
            recentWatchedList = new ArrayList<Medium>();

        String json = new Gson().toJson(recentWatchedList);

        Boolean bCommit = sharedPreferences.edit().putString(RECENT_WATCHED, json).commit();
        Log.v(TAG, "Wrote RecentWatched configuration information");
    }

    public static void addMedium(Medium medium) {
        if (recentWatchedList == null)
            recentWatchedList = new ArrayList<Medium>();

        for (int i=0; i<recentWatchedList.size(); i++) {
            Medium saved = recentWatchedList.get(i);
            if (medium.getId() == saved.getId())
                return;
        }

        if (recentWatchedList.size() >= 10)
            recentWatchedList.remove(0);

        recentWatchedList.add(medium);
        writeConfig();
    }

    public static ArrayList<Medium> getRecentWatchedList() {
        return recentWatchedList;
    }

    public static void addUser(User user) {
        if (recentUsersList == null)
            recentUsersList = new ArrayList<>();

        for (int i=0; i<recentUsersList.size(); i++) {
            User saved = recentUsersList.get(i);
            if (saved.getId() == user.getId())
                return;
        }

        if (recentUsersList.size() >= 3)
            recentUsersList.remove(0);

        recentUsersList.add(user);
        writeConfig();
    }

    public static ArrayList<User> getRecentUsersList() {
        return recentUsersList;
    }



}