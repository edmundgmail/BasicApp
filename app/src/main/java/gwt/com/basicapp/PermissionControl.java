package gwt.com.basicapp;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;

/**
 * Created by eguo on 12/12/17.
 */

public abstract class PermissionControl extends FragmentActivity{
    public static final String[] INITIAL_PERMS={
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final int INITIAL_REQUEST=1337;

    private boolean hasPermission(String perm)
    {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }


    private boolean canAccessLocation() {
        return(hasPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    protected void checkPermission() {
        if (!canAccessLocation()) {
            requestPermissions(PermissionControl.INITIAL_PERMS, INITIAL_REQUEST);
        }
    }


}
