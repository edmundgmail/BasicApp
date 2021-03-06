package gwt.com.basicapp;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void regex_isCorrect() throws Exception{

        String s = "{40.1, 23.2}    ";
        SimpleLocation expected = new SimpleLocation(40.1, 23.2);
        SimpleLocation actual = SimpleLocation.fromString(s);
        assertEquals(expected, actual);
    }

    @Test
    public void regex_testArray() throws Exception{
        String stops = "{40.1,-80.2}|{-40.4, -80.3}  ";
        List<LatLng> latlongs = new ArrayList<>();

        String []ss = stops.split("\\|");
        for(String s : ss){
            SimpleLocation l = SimpleLocation.fromString(s);
            latlongs.add(new LatLng(l.getLatitude(), l.getLongitude()));
        }

        assertEquals(latlongs.get(0), new LatLng(40.1, -80.2));
        assertEquals(latlongs.get(1), new LatLng(-40.4, -80.3));
    }
}