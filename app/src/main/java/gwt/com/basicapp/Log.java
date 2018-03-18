package gwt.com.basicapp;

/**
 * Created by eguo on 1/24/18.
 */

public class Log {
    public static int d(String tag, String msg) {
        return android.util.Log.d(tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        return android.util.Log.d(tag, msg, tr);
    }
}
