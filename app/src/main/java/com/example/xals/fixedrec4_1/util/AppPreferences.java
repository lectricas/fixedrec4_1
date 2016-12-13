package com.example.xals.fixedrec4_1.util;


import proxypref.annotation.DefaultString;
import proxypref.annotation.Preference;

public interface AppPreferences {
    String NAME = "app_preferences";

    String PREF_TOKEN = "token";

    String PREF_CURRENT_TRACK_UUID = "currentTrackUUID";

    @Preference(PREF_TOKEN)
    String getToken();

    @Preference(PREF_TOKEN)
    void setToken(String value);

    @DefaultString("")
    @Preference(PREF_CURRENT_TRACK_UUID)
    String getCurrentTrackUUID();

    @Preference(PREF_CURRENT_TRACK_UUID)
    void setCurrentTrackUUID(String UUID);
}
