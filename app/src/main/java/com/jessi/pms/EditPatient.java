package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

/**
 * Created by Jessi on 12/1/2016.
 */

public class EditPatient extends AppCompatActivity implements Validator.ValidationListener {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private Validator validator;

    private Button saveButton, cancelButton;
    private ProgressBar loadingProgressBar;
    private EditText fullNameEditText, physicianEditText, dateAdmittedEditText, timeAdmittedEditText, roomEditText;

    private boolean isFormValid = false;
    private String id, fullname, physican, room;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.v("intentTest", "Bundle is Null");
            return;
        }

        // get data via the key
        id = extras.getString("Id", "");
        fullname = extras.getString("Fullname", "");
        physican = extras.getString("Physician", "");
        room = extras.getString("Room", "");
        if (physican != null  && physican.contains("Physician: ")) {
            Log.v("intentTest", "Removed \"Physician: \"");
            physican = physican.replace("Physician: ", "");
        }
        if (room != null  && room.contains("Room: ")) {
            Log.v("intentTest", "Removed \"Room: \"");
            room = room.replace("Room: ", "");
        }

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();

        if (user == null) {
            // Not logged in, launch the Log In activity
            loadLoginView();
        }

        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        loadingProgressBar.setVisibility(View.GONE);
        fullNameEditText = (EditText) findViewById(R.id.fullname_edittext);
        physicianEditText = (EditText) findViewById(R.id.physician_edittext);
        roomEditText = (EditText) findViewById(R.id.room_edittext);
        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        fullNameEditText.setText(fullname);
        physicianEditText.setText(physican);
        roomEditText.setText(room);

        validator = new Validator(this);
        validator.setValidationListener(this);

        database = FirebaseDatabase.getInstance().getReference();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                validator.validate();

                if (isFormValid) {

                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent patientListIntent = new Intent(getApplicationContext(), EditPatient.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(patientListIntent);
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
    public void onValidationSucceeded() {
        isFormValid = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        isFormValid = false;

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

        loadingProgressBar.setVisibility(View.GONE);
    }
}
