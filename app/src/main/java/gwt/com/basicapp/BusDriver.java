package gwt.com.basicapp;

import java.util.List;

/**
 * Created by eguo on 12/18/17.
 */

public class BusDriver {
    private List<String> buses;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getBuses() {
        return buses;
    }

    public void setBuses(List<String> buses) {
        this.buses = buses;
    }

    public void addBus(String bus){
        this.buses.add(bus);
    }

    public void removeBus(String bus){
        this.buses.remove(bus);
    }
}
