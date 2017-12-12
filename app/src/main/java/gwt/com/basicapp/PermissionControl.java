package gwt.com.basicapp;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

/**
 * Created by eguo on 12/12/17.
 */

public class PermissionControl extends Activity{
    public static final String[] INITIAL_PERMS={
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final int INITIAL_REQUEST=1337;

}
