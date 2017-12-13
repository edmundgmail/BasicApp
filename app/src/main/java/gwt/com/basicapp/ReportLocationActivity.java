package gwt.com.basicapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

import java.security.Permission;

import static gwt.com.basicapp.PermissionControl.INITIAL_REQUEST;

public class ReportLocationActivity extends PermissionControl {
    private String mbusId;

    private void startService(){
        Intent intent = new Intent(this, ReportLocationService.class);
        intent.putExtra("BUS_ID", mbusId);
        this.startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent(this, ReportLocationService.class);
        intent.putExtra("BUS_ID", mbusId);
        this.stopService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();
        setContentView(R.layout.activity_report_location);

        mbusId = getIntent().getStringExtra("BUS_ID");


        startService();
        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(toggleButton.isChecked()){
                    startService();
                }
                else{
                    stopService();
                }
            }
        });

    }

}
