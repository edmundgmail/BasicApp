package gwt.com.basicapp;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TrackLocationActivity extends PermissionControl implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final int REQUEST_LOCATION = 0;
    private SimpleGeoLocation mLastLocation;
    private boolean mRequestingLocationUpdates = false;
    //private LocationRequest mLocationRequest;
    private static final String TAG = "";
    private GoogleMap mMap;
    private int mMarkerCount;

    private Marker mMarker = null;

    private void restartService() {
        stopService();
        startService();
    }

    private void startService() {
            Intent intent = new Intent(this, TrackLocationService.class);
            this.startService(intent);
            this.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
    }

    private void stopService() {
            Intent intent = new Intent(this, TrackLocationService.class);
            this.unregisterReceiver(mMessageReceiver);
            this.stopService(intent);
            mMarkerCount = 0; //false a refresh
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            double lat = intent.getDoubleExtra("latitude", 0);
            double lon = intent.getDoubleExtra("longitude", 0);
            float bear = intent.getFloatExtra("bearing", 0);

            Log.d(TAG, "lat=" + lat + "lon=" + lon + "bear=" + bear);
            onLocationChanged(new SimpleGeoLocation(lat, lon, bear));
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miSettings:
                Intent i = new Intent(this, MyPreferencesActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.track_location_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_main);
        Log.d(TAG, "onCreate called");
        mMarkerCount =0;

        //Check If Google Services Is Available
        if (getServicesAvailable()) {
            // Building the GoogleApi client
            Toast.makeText(this, "Google Service Is Available!!", Toast.LENGTH_SHORT).show();
        }

        //Create The MapView Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * GOOGLE MAPS AND MAPS OBJECTS
     *
     * */

    // After Creating the Map Set Initial Location
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }


    @Nullable
    private List<LatLng> getBusStops(){
        SharedPreferences sharedPreferences = getSharedPreferences("mysettings", 0);
        String busId = sharedPreferences.getString("busId", "");
        Log.i(TAG, "busId = " + busId);
        List<LatLng> latlongs = new ArrayList<>();

        if(!busId.isEmpty()){

            String stops = sharedPreferences.getString(busId,"");
            Log.i(TAG, "stops = " + stops);
            if(!stops.isEmpty()){
                String []ss = stops.split("\\|");
                for(String s : ss){
                    SimpleLocation l = SimpleLocation.fromString(s);
                    if(l != null)latlongs.add(new LatLng(l.getLatitude(), l.getLongitude()));
                }
                return latlongs;
            }
        }

        return null;
    }

    private void generateBusStopMarker(){
        int height = 128;
        int width = 128;

        List<LatLng> latlongs = getBusStops();
        BitmapDrawable bitmapBusstopDrawable = (BitmapDrawable) getResources().getDrawable(R.mipmap.busstop);
        Bitmap bitmapBusstop = bitmapBusstopDrawable.getBitmap();
        Bitmap smallMarkerBusstop = Bitmap.createScaledBitmap(bitmapBusstop, width, height, false);

        for(LatLng latlong: latlongs){
            mMap.addMarker(new MarkerOptions().position(latlong)
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin3))
                    .icon(BitmapDescriptorFactory.fromBitmap((smallMarkerBusstop))));
        }

    }

    private void generateBusMarker(LatLng latlong) {
        int height = 128;
        int width = 128;
        BitmapDrawable bitmapSchoolbusDrawable = (BitmapDrawable) getResources().getDrawable(R.mipmap.schoolbus);
        Bitmap bitmapSchoolbus = bitmapSchoolbusDrawable.getBitmap();
        Bitmap smallMarkerSchoolbus = Bitmap.createScaledBitmap(bitmapSchoolbus, width, height, false);


        mMarker = mMap.addMarker(new MarkerOptions().position(latlong)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin3))
                .icon(BitmapDescriptorFactory.fromBitmap((smallMarkerSchoolbus))));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15.5f));

    }

    // Add A Map Pointer To The MAp
    public void addMarker(GoogleMap googleMap, double lat, double lon) {

        if(mMarkerCount>0){
            animateMarker(mLastLocation, mMarker);
        }
        else{
            //Set Custom BitMap for Pointer
            LatLng latlong = new LatLng(lat, lon);
            generateBusMarker(latlong);

            generateBusStopMarker();
            //Set Marker Count to 1 after first marker is created
            mMarkerCount =1;

            checkPermission();
            //mMap.setMyLocationEnabled(true);
            startLocationUpdates();
        }
    }


    @Override
    public void onInfoWindowClick (Marker marker){
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
    }


    private boolean getServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {

            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cannot Connect To Play Services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    /**
     * LOCATION LISTENER EVENTS
     *
     * */

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume called");
        getServicesAvailable();
        startService();

    }


    @Override
    protected void onPause() {
        super.onPause();
        stopService();
    }

    //Method to display the location on UI
    private void displayLocation() {

            checkPermission();

            if (mLastLocation != null) {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();
                String loc = "" + latitude + " ," + longitude + " ";
                Toast.makeText(this,loc, Toast.LENGTH_SHORT).show();

                //Add pointer to the map at location
                addMarker(mMap,latitude,longitude);


            } else {

                Toast.makeText(this, "Couldn't get the location. Make sure location is enabled on the device",
                        Toast.LENGTH_SHORT).show();
            }
    }


    //Starting the location updates
    protected void startLocationUpdates() {
            checkPermission();
    }

    //Stopping location updates
    protected void stopLocationUpdates() {
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {

    }


    private void onLocationChanged(SimpleGeoLocation location) {
        // Assign the new location
        mLastLocation = location;

        Toast.makeText(getApplicationContext(), "Location changed!",
                Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
        displayLocation();
    }


    private void animateMarker(final SimpleGeoLocation destination, final Marker marker) {
        if (marker != null) {
            final LatLng startPosition = marker.getPosition();
            final LatLng endPosition = new LatLng(destination.getLatitude(), destination.getLongitude());

            final float startRotation = marker.getRotation();

            final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(5000); // duration 1 second
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                        marker.setPosition(newPosition);
                        //marker.setRotation(computeRotation(v, startRotation, destination.getBearing()));
                        marker.setRotation(getBearing(startPosition, newPosition));

                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 15.5f));
                        mMap.moveCamera(CameraUpdateFactory
                                .newCameraPosition
                                        (new CameraPosition.Builder()
                                                .target(newPosition)
                                                .zoom(15.5f)
                                                .build()));
                    } catch (Exception ex) {
                        // I don't care atm..
                    }
                }
            });

            valueAnimator.start();
        }
    }



    private static float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }


    private static float computeRotation(float fraction, float start, float end) {
        float normalizeEnd = end - start; // rotate start to 0
        float normalizedEndAbs = (normalizeEnd + 360) % 360;

        float direction = (normalizedEndAbs > 180) ? -1 : 1; // -1 = anticlockwise, 1 = clockwise
        float rotation;
        if (direction > 0) {
            rotation = normalizedEndAbs;
        } else {
            rotation = normalizedEndAbs - 360;
        }

        float result = fraction * rotation + start;
        return (result + 360) % 360;
    }

}

interface LatLngInterpolator {
    LatLng interpolate(float fraction, LatLng a, LatLng b);

    class LinearFixed implements LatLngInterpolator {
        @Override
        public LatLng interpolate(float fraction, LatLng a, LatLng b) {
            double lat = (b.latitude - a.latitude) * fraction + a.latitude;
            double lngDelta = b.longitude - a.longitude;
            // Take the shortest path across the 180th meridian.
            if (Math.abs(lngDelta) > 180) {
                lngDelta -= Math.signum(lngDelta) * 360;
            }
            double lng = lngDelta * fraction + a.longitude;
            return new LatLng(lat, lng);
        }
    }
}
