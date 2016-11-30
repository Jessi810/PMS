package com.jessi.pms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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

import com.github.clans.fab.FloatingActionMenu;
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
import com.jessi.pms.receivers.MedicineAlarmReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jessi on 11/20/2016.
 */

public class PatientMonitor extends AppCompatActivity {

    com.github.clans.fab.FloatingActionMenu menuFam;
    com.github.clans.fab.FloatingActionButton archiveFab, refreshFab;

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

        menuFam = (com.github.clans.fab.FloatingActionMenu) findViewById(R.id.menu_fam);
        archiveFab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.archive_fab);
        refreshFab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.refresh_fab);

        archiveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFam.close(true);
            }
        });

        refreshFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFam.close(true);
            }
        });

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

                Log.v("Test", "Id: " + idSelected);

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

        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final SimpleDateFormat sdtf = new SimpleDateFormat("yyyyMMddH:mm");
        final String currentDateAndTime = sdf.format(new Date());
        Log.v("timelog", cal.getTime().toString());
        Log.v("timelog", currentDateAndTime);

        final Intent intent = new Intent(getApplicationContext(), MedicineAlarmReceiver.class);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        database.child("Patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                Monitor monitor;
                int ctr = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    monitor = postSnapshot.getValue(Monitor.class);
                    Date mDate = new Date();

                    // Add patient to the list if it is monitoring
                    if(monitor.monitoring) {
                        adapter.add(monitor);
                        try {
                            if(monitor.time1a != null) {
                                mDate = sdtf.parse(currentDateAndTime + monitor.time1a);
                                cal.setTime(mDate);
                                intent.putExtra("rc", ctr++); ctr--;
                                intent.putExtra("title", monitor.fullname + " needs medicine");
                                intent.putExtra("content", monitor.drug1 + " in room " + monitor.room);
                                PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), ctr++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.setExact(AlarmManager.RTC, cal.getTimeInMillis(), alarmIntent);
                                Log.v("timelog", cal.getTime().toString());
                            }
                            if(monitor.time1b != null) {
                                mDate = sdtf.parse(currentDateAndTime + monitor.time1b);
                                cal.setTime(mDate);
                                intent.putExtra("rc", ctr++); ctr--;
                                intent.putExtra("title", monitor.fullname + " needs medicine");
                                intent.putExtra("content", monitor.drug1 + " in room " + monitor.room);
                                PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), ctr++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.setExact(AlarmManager.RTC, cal.getTimeInMillis(), alarmIntent);
                                Log.v("timelog", cal.getTime().toString());
                            }
                            if(monitor.time1c != null) {
                                mDate = sdtf.parse(currentDateAndTime + monitor.time1c);
                                cal.setTime(mDate);
                                intent.putExtra("rc", ctr++); ctr--;
                                intent.putExtra("title", monitor.fullname + " needs medicine");
                                intent.putExtra("content", monitor.drug1 + " in room " + monitor.room);
                                PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), ctr++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.setExact(AlarmManager.RTC, cal.getTimeInMillis(), alarmIntent);
                                Log.v("timelog", cal.getTime().toString());
                            }
                            if(monitor.time2a != null) {
                                mDate = sdtf.parse(currentDateAndTime + monitor.time2a);
                                cal.setTime(mDate);
                                intent.putExtra("rc", ctr++); ctr--;
                                intent.putExtra("title", monitor.fullname + " needs medicine");
                                intent.putExtra("content", monitor.drug2 + " in room " + monitor.room);
                                PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), ctr++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.setExact(AlarmManager.RTC, cal.getTimeInMillis(), alarmIntent);
                                Log.v("timelog", cal.getTime().toString());
                            }
                            if(monitor.time2b != null) {
                                mDate = sdtf.parse(currentDateAndTime + monitor.time2b);
                                cal.setTime(mDate);
                                intent.putExtra("rc", ctr++); ctr--;
                                intent.putExtra("title", monitor.fullname + " needs medicine");
                                intent.putExtra("content", monitor.drug2 + " in room " + monitor.room);
                                PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), ctr++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.setExact(AlarmManager.RTC, cal.getTimeInMillis(), alarmIntent);
                                Log.v("timelog", cal.getTime().toString());
                            }
                            if(monitor.time2c != null) {
                                mDate = sdtf.parse(currentDateAndTime + monitor.time2c);
                                cal.setTime(mDate);
                                intent.putExtra("rc", ctr++); ctr--;
                                intent.putExtra("title", monitor.fullname + " needs medicine");
                                intent.putExtra("content", monitor.drug2 + " in room " + monitor.room);
                                PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), ctr++, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.setExact(AlarmManager.RTC, cal.getTimeInMillis(), alarmIntent);
                                Log.v("timelog", cal.getTime().toString());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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
}
