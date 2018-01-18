package gwt.com.basicapp;

import org.junit.Test;

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
        String s = "{40.1,23.2}";
        SimpleLocation expected = new SimpleLocation(40.1, 23.2);
        SimpleLocation actual = SimpleLocation.fromString(s);
        assertEquals(expected, actual);
    }
}