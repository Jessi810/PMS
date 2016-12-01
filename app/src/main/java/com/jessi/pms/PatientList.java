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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jessi.pms.adapters.PatientListAdapter;
import com.jessi.pms.models.Patient.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jessi on 11/20/2016.
 */

public class PatientList extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ListView patientsListView;

    //FloatingActionMenu fabMenu;
    FloatingActionButton addPatientFab;
    ProgressBar loadingProgressBar;

    String idSelected;

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

        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);

        //fabMenu = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        addPatientFab = (FloatingActionButton) findViewById(R.id.addpatient_fab);

        addPatientFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPatientIntent = new Intent(getApplicationContext(), AddPatient.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(addPatientIntent);
            }
        });

        // Construct the data source
        ArrayList<Patient> arrayOfUsers = new ArrayList<>();
        // Create the adapter to convert the array to views
        final PatientListAdapter adapter = new PatientListAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) findViewById(R.id.patients_listview);
        listView.setAdapter(adapter);

        database.child("Patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                Patient patient;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    patient = postSnapshot.getValue(Patient.class);
                    adapter.add(patient);
                    Log.v("Test", dataSnapshot.toString());
                    if(listView.getCount() != 0) {
                        loadingProgressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                idSelected = ((TextView)view.findViewById(R.id.list_id)).getText().toString();
                final String caseNumber = ((TextView)view.findViewById(R.id.list_casenumber)).getText().toString();
                String dateTime = ((TextView)view.findViewById(R.id.list_datetime)).getText().toString();
                String fullName = ((TextView)view.findViewById(R.id.list_fullname)).getText().toString();
                String sex = ((TextView)view.findViewById(R.id.list_sex)).getText().toString();
                String physician = ((TextView)view.findViewById(R.id.list_physician)).getText().toString();
                String room = ((TextView)view.findViewById(R.id.list_room)).getText().toString();

                android.util.Log.v("Test", "Id: " + idSelected);
                android.util.Log.v("Test", "Case#: " + caseNumber);
                android.util.Log.v("Test", "DateTime: " + dateTime);
                android.util.Log.v("Test", "FullName: " + fullName);
                android.util.Log.v("Test", "Sex: " + sex);
                android.util.Log.v("Test", "Physician: " + physician);
                android.util.Log.v("Test", "Room: " + room);

                android.util.Log.v("Test", String.valueOf(position));
                android.util.Log.v("Test", String.valueOf(id));

                PopupMenu popupMenu = new PopupMenu(PatientList.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_patient_list, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.items_addtomonitor:
                                Toast.makeText(getApplicationContext(), "Patient added to monitoring.", Toast.LENGTH_SHORT).show();

                                // Adds patient to monitoring
                                Map<String, Object> newValues = new HashMap<>();
                                newValues.put("monitoring", true);
                                database.child("Patients").child(idSelected).updateChildren(newValues);

                                return true;
                            case R.id.items_delete:
                                Toast.makeText(getApplicationContext(), "Patient deleted.", Toast.LENGTH_SHORT).show();
                                database.child("Patients").child(idSelected).setValue(null);
                                return true;
                            case R.id.items_cancel:
                                return true;
                        }

                        return false;
                    }
                });
            }
        });
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
