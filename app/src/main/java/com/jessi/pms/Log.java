package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.jessi.pms.adapters.UserLogAdapter;
import com.jessi.pms.models.Patient;
import com.jessi.pms.models.UserLog;

import java.util.ArrayList;

/**
 * Created by Jessi on 11/21/2016.
 */

public class Log extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ListView userLogListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();

        if (user == null) {
            // Not logged in, launch the Log In activity
            loadLoginView();
        }

        // Construct the data source
        ArrayList<UserLog> arrayOfUsers = new ArrayList<>();
        // Create the adapter to convert the array to views
        final UserLogAdapter adapter = new UserLogAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        userLogListView = (ListView) findViewById(R.id.userlog_listview);
        userLogListView.setAdapter(adapter);

        database.child("Logs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserLog userLog = postSnapshot.getValue(UserLog.class);
                    adapter.add(userLog);
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
        startActivity(intent);
    }
}
