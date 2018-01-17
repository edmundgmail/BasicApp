package gwt.com.basicapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eguo on 1/17/18.
 */

public class SimpleLocation {
    protected double latitude;
    protected double longitude;


    private static final String regex = "\\{\\s(\\d*[.]\\d)\\s,\\s(\\d*[.]\\d)\\s}";
    private static final Pattern pattern = Pattern.compile(regex);

    @Override
    public String toString() {
        return "{" +
                 latitude +
                "," + longitude +
                "}";
    }

    public static SimpleLocation fromString(String s) {
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            double lat = Double.parseDouble(matcher.group(1));
            double lon = Double.parseDouble(matcher.group(2));

            return new SimpleLocation(lat, lon);
        }
        return null;
    }

    public SimpleLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public SimpleLocation(){

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


}
