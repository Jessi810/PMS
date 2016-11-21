package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jessi.pms.adapters.MonitorPatientAdapter;
import com.jessi.pms.adapters.PatientListAdapter;
import com.jessi.pms.models.Monitor;
import com.jessi.pms.models.Patient;

import java.util.ArrayList;

/**
 * Created by Jessi on 11/21/2016.
 */

public class MonitorPatient extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ListView patientsListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_patient);

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
        final MonitorPatientAdapter adapter = new MonitorPatientAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.monitorpatient_listview);
        listView.setAdapter(adapter);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.list_medicines);
        final TextView medItem = (TextView) findViewById(R.id.list_medItem);

        database.child("Monitoring").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int ctr = 0;
                adapter.clear();
                String uid;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    android.util.Log.v("Monitorsz", "monitor");
                    Monitor monitor = postSnapshot.getValue(Monitor.class);
                    adapter.add(monitor);
                    uid = postSnapshot.getKey().toString();
                    android.util.Log.v("Monitorsz", "UID: " + uid);

//                    for (DataSnapshot medSnapshot : dataSnapshot.child(uid).child("medicines").getChildren()) {
//                        monitor = medSnapshot.getValue(Monitor.class);
//                        medItem.setText(monitor.medicine);
//                        linearLayout.addView(medItem);
//                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        database.child("Monitoring").child("medicines").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int ctr = 0;
//                adapter.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    android.util.Log.v("Monitorsz", "medicines");
//                    Monitor monitor = postSnapshot.getValue(Monitor.class);
//                    medItem.setText(monitor.medicine);
//                    linearLayout.addView(medItem);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
