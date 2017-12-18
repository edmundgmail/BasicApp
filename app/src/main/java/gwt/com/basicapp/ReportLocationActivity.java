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
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Permission;

import static gwt.com.basicapp.PermissionControl.INITIAL_REQUEST;

public class ReportLocationActivity extends PermissionControl {
    private Spinner busSpinner = (Spinner) findViewById(R.id.busSpinner);
    final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("drivers");

    private void startService(){
        Intent intent = new Intent(this, ReportLocationService.class);
        String busId = (String) busSpinner.getSelectedItem();
        intent.putExtra("busId", busId);
        this.startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent(this, ReportLocationService.class);
        this.stopService(intent);
    }

    private void populateSpinner(){
        String email = this.getIntent().getStringExtra("email");
        DatabaseReference driverProfile = dbRef.child(email);
        driverProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();

        populateSpinner();
        setContentView(R.layout.activity_report_location);

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
