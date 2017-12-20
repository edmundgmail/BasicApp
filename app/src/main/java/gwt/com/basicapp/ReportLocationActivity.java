package gwt.com.basicapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import static gwt.com.basicapp.PermissionControl.INITIAL_REQUEST;

public class ReportLocationActivity extends PermissionControl {
    private Spinner busSpinner;
    private BusDriver busDriver;

    private void showError(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Write your message here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void startService(){
        Intent intent = new Intent(this, ReportLocationService.class);
        String busId = (String) busSpinner.getSelectedItem();

        if(busId==null || busId.equals("")){
            showError("There is no bus selected, can't continue");
            System.exit(1);
        }
        intent.putExtra("busId", busId);
        this.startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent(this, ReportLocationService.class);
        this.stopService(intent);
    }

    public void setupSpinner(final List<String> buses) {

        busSpinner = (Spinner) findViewById(R.id.busSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner,buses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        busSpinner.setAdapter(dataAdapter);
    }

    private void populateSpinner(){
        String userid = this.getIntent().getStringExtra("userid");
        DatabaseReference driverProfile = FirebaseDatabase.getInstance().getReference("drivers").child(userid);
        driverProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                busDriver = dataSnapshot.getValue(BusDriver.class);
                if(busDriver!=null && busDriver.getBuses() != null){
                    setupSpinner(busDriver.getBuses());
                }

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
        setContentView(R.layout.activity_report_location);
        populateSpinner();
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
