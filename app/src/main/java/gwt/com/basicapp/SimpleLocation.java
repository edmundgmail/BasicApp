package gwt.com.basicapp;

import android.location.Location;

/**
 * Created by eguo on 12/14/17.
 */

public class SimpleLocation {
    private double latitude;
    private double longitude;
    private float bearing;

    public SimpleLocation(double latitude, double longitude, float bearing){
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearing = bearing;
    }

    public SimpleLocation(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.bearing = location.getBearing();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }
}
