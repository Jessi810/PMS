package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.*;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jessi.pms.adapters.PatientListAdapter;
import com.jessi.pms.models.Patient;

import java.util.ArrayList;

/**
 * Created by Jessi on 11/20/2016.
 */

public class PatientList extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ListView patientsListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();

        if (user == null) {
            // Not logged in, launch the Log In activity
            loadLoginView();
        }

        // Set up ListView
//        final ListView patientsListView = (ListView) findViewById(R.id.patients_listview);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
//        patientsListView.setAdapter(adapter);

        // Construct the data source
        ArrayList<Patient> arrayOfUsers = new ArrayList<>();
        // Create the adapter to convert the array to views
        final PatientListAdapter adapter = new PatientListAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.patients_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(PatientList.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_patient_list, popupMenu.getMenu());
                popupMenu.show();

                android.util.Log.v("Test", String.valueOf(parent));
                android.util.Log.v("Test", String.valueOf(view));
                android.util.Log.v("Test", String.valueOf(position));
                android.util.Log.v("Test", String.valueOf(id));
            }
        });

        database.child("Patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Patient patient = postSnapshot.getValue(Patient.class);
                    adapter.add(patient);
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
            case R.id.items_addtomonitor:
                Toast.makeText(this, "Add to Monitor Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.items_delete:
                Toast.makeText(this, "Delete Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.items_cancel:
                Toast.makeText(this, "Cancel Clicked", Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }
}
