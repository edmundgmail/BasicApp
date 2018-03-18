package gwt.com.basicapp;

import android.location.Location;

/**
 * Created by eguo on 12/14/17.
 */

public class SimpleGeoLocation extends  SimpleLocation {
    private float bearing;


    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public SimpleGeoLocation(double latitude, double longitude, float bearing){
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearing = bearing;
    }

    public SimpleGeoLocation(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.bearing = location.getBearing();
    }

}
