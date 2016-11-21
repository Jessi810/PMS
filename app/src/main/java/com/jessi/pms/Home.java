package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jessi.pms.models.Patient;

public class Home extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            // Not logged in, launch the Log In activity
            loadLoginView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addpatient) {
            Intent addPatientIntent = new Intent(this, AddPatient.class);
            addPatientIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            addPatientIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(addPatientIntent);
            return true;
        }
        if (id == R.id.action_patientlist) {
            Intent patientListIntent = new Intent(this, PatientList.class);
            patientListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            patientListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(patientListIntent);
            return true;
        }
        if (id == R.id.action_userlog) {
            Intent patientListIntent = new Intent(this, Log.class);
            patientListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            patientListIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(patientListIntent);
            return true;
        }
        if (id == R.id.action_profile) {
            Intent profileIntent = new Intent(this, Profile.class);
            startActivity(profileIntent);
            return true;
        }
        if (id == R.id.action_logout) {
            auth.signOut();
            loadLoginView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
