package gwt.com.basicapp;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    private AppCompatDelegate mDelegate;
    private SharedPreferences mSharedPreferences;

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }


    public void setupSpinner(final List<String> buses) {

        Spinner busTrackingSpinner = (Spinner) findViewById(R.id.busTrackingSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner,buses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        busTrackingSpinner.setAdapter(dataAdapter);
        busTrackingSpinner.setSelection(1);
        busTrackingSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    private void populateSpinner(){
                    List<String> buses = new ArrayList<>();
                    buses.add("bus1234");
                    buses.add("bus5678");
                    setupSpinner(buses);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }
    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);

        mSharedPreferences = getSharedPreferences("mysettings", 0);
        Boolean s = mSharedPreferences.getBoolean("isDriver", true);
        Log.i(TAG, "isDriver = " + s);

        setContentView(R.layout.activity_settings);
        populateSpinner();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    /*@Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }
    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }
    */

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }
    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        getDelegate().setTitle(title);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }
    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }
    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
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
