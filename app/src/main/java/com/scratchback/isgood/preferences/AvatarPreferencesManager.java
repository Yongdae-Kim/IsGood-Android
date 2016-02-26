package com.scratchback.isgood.preferences;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.scratchback.isgood.vo.Avatar;

/**
 * Created by useruser on 16. 2. 17..
 */
public class AvatarPreferencesManager {
    private static final String PREF_AVATAR = "PREF_AVATAR";
    private static AvatarPreferencesManager avatarPreference;
    private SharedPreferences prefs;
    private Gson gson;

    private AvatarPreferencesManager(Activity activity) {
        prefs = activity.getPreferences(activity.MODE_PRIVATE);
        gson = new Gson();
    }

    public static AvatarPreferencesManager getInstacne(Activity activity) {
        if (avatarPreference == null) {
            avatarPreference = new AvatarPreferencesManager(activity);
        }
        return avatarPreference;
    }

    public void save(Avatar avatar) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String json = gson.toJson(avatar);
        prefsEditor.putString(PREF_AVATAR, json);
        prefsEditor.commit();
    }

    public Avatar get() {
        String json = prefs.getString(PREF_AVATAR, "");
        return gson.fromJson(json, Avatar.class);
    }

    public boolean isPreferencesExisted() {
        return prefs.contains(PREF_AVATAR);
    }

    public void remove() {
        prefs.edit().remove(PREF_AVATAR).commit();
    }
}
