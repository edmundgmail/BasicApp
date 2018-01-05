package gwt.com.basicapp;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class MyPreferencesActivity extends PreferenceActivity {
    private static final String TAG = "MyPreferencesActivity";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("mysettings", 0);
        Boolean s = sharedPreferences.getBoolean("isDriver", true);
        Log.i(TAG, "isDriver = " + s);
        ActionBar actionBar = this.getActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("mysettings", 0);
        Boolean s = sharedPreferences.getBoolean("isDriver", true);
        Log.i(TAG, "isDriver1 = " + s);

    }
}
