package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jessi.pms.adapters.PatientListAdapter;
import com.jessi.pms.adapters.PatientMonitorAdapter;
import com.jessi.pms.models.Monitor;
import com.jessi.pms.models.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jessi on 11/20/2016.
 */

public class PatientMonitor extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ListView patientsListView;

    String idSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_monitor);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();

        if (user == null) {
            // Not logged in, launch the Log In activity
            loadLoginView();
        }

        // Construct the data source
        ArrayList<Monitor> arrayOfUsers = new ArrayList<>();
        // Create the adapter to convert the array to views
        final PatientMonitorAdapter adapter = new PatientMonitorAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.patient_monitor_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idSelected = ((TextView)view.findViewById(R.id.list_id)).getText().toString();
                String fullName = ((TextView)view.findViewById(R.id.list_fullname)).getText().toString();
                String physician = ((TextView)view.findViewById(R.id.list_physician)).getText().toString();
                String room = ((TextView)view.findViewById(R.id.list_room)).getText().toString();
                String medicine = ((TextView)view.findViewById(R.id.list_medicine)).getText().toString();

                Log.v("Test", "Id: " + idSelected);
                Log.v("Test", "FullName: " + fullName);
                Log.v("Test", "Physician: " + physician);
                Log.v("Test", "Room: " + room);
                Log.v("Test", "Medicine: " + medicine);
                Log.v("Test", String.valueOf(position));
                Log.v("Test", String.valueOf(id));

                PopupMenu popupMenu = new PopupMenu(PatientMonitor.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_patient_monitor, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.items_removefrommonitor:
                                Toast.makeText(getApplicationContext(), "Patient removed to monitoring.", Toast.LENGTH_SHORT).show();

                                // Removes patient to monitoring
                                Map<String, Object> newValues = new HashMap<>();
                                newValues.put("monitoring", false);
                                database.child("Patients").child(idSelected).updateChildren(newValues);

                                return true;
                            case R.id.items_delete:
                                database.child("Patients").child(idSelected).setValue(null);
                                return true;
                            case R.id.items_cancel:
                                Toast.makeText(getApplicationContext(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
                                return true;
                        }

                        return false;
                    }
                });
            }
        });

        database.child("Patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                Monitor monitor;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    monitor = postSnapshot.getValue(Monitor.class);

                    // Add patient to the list if it is monitoring
                    if(monitor.monitoring) {
                        adapter.add(monitor);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.items_removefrommonitor:
                return true;
            case R.id.items_delete:
                return true;
            case R.id.items_cancel:
                return true;
        }

        return false;
    }
}
