package gwt.com.basicapp;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by eguo on 12/12/17.
 */

public abstract class PermissionControl extends AppCompatActivity {
    public static final String[] INITIAL_PERMS={
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final int INITIAL_REQUEST=1337;

    private boolean hasPermission(String perm)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
        }
        else
        {
            return PermissionChecker.checkSelfPermission(this.getApplicationContext(), perm) == PermissionChecker.PERMISSION_GRANTED;

        }
    }


    private boolean canAccessLocation() {
        return(hasPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) &&
                hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    protected void checkPermission() {
        if (!canAccessLocation()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PermissionControl.INITIAL_PERMS, INITIAL_REQUEST);
            }
        }
    }


}
