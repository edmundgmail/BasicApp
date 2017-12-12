package gwt.com.basicapp;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TrackLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    protected boolean hasPermission(String perm)
    {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }

    private moveCar() {
        final LatLng SomePos = new LatLng(12.7796354, 77.4159606);

        try {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(false);
            googleMap.setIndoorEnabled(false);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(SomePos));
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(googleMap.getCameraPosition().target)
                    .zoom(17)
                    .bearing(30)
                    .tilt(45)
                    .build()));

            myMarker = googleMap.addMarker(new MarkerOptions()
                    .position(SomePos)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                    .title("Hello world"));


            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker arg0) {

                    final LatLng startPosition = myMarker.getPosition();
                    final LatLng finalPosition = new LatLng(12.7801569, 77.4148528);
                    final Handler handler = new Handler();
                    final long start = SystemClock.uptimeMillis();
                    final Interpolator interpolator = new AccelerateDecelerateInterpolator();
                    final float durationInMs = 3000;
                    final boolean hideMarker = false;

                    handler.post(new Runnable() {
                        long elapsed;
                        float t;
                        float v;

                        @Override
                        public void run() {
                            // Calculate progress using interpolator
                            elapsed = SystemClock.uptimeMillis() - start;
                            t = elapsed / durationInMs;

                            LatLng currentPosition = new LatLng(
                                    startPosition.latitude * (1 - t) + finalPosition.latitude * t,
                                    startPosition.longitude * (1 - t) + finalPosition.longitude * t);

                            myMarker.setPosition(currentPosition);

                            // Repeat till progress is complete.
                            if (t < 1) {
                                // Post again 16ms later.
                                handler.postDelayed(this, 16);
                            } else {
                                if (hideMarker) {
                                    myMarker.setVisible(false);
                                } else {
                                    myMarker.setVisible(true);
                                }
                            }
                        }
                    });

                    return true;

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
