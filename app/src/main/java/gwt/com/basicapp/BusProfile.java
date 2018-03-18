package gwt.com.basicapp;

import java.util.List;

/**
 * Created by eguo on 12/18/17.
 */

public class BusProfile {
    String id;
    List<SimpleLocation> stops;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SimpleLocation> getStops() {
        return stops;
    }

    public void setStops(List<SimpleLocation> stops) {
        this.stops = stops;
    }
}
