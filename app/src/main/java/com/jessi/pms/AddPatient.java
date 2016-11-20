package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jessi.pms.models.Patient;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

/**
 * Created by Jessi on 11/20/2016.
 */

public class AddPatient extends AppCompatActivity implements Validator.ValidationListener {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private String userId;
    private Validator validator;

    @NotEmpty
    private EditText caseNumberEditText;
    @NotEmpty
    private EditText fullNameEditText;
    @NotEmpty
    private EditText sexEditText;
    @NotEmpty
    private EditText physicianEditText;
    @NotEmpty
    private EditText roomEditText;
    @NotEmpty
    private EditText dateAdmittedEditText;
    @NotEmpty
    private EditText timeAdmittedEditText;

    private Button addButton, cancelButton;
    private ProgressBar loadingProgressBar;

    private boolean isFormValid = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        // Initialize Firebase Auth and Database Reference
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();

        if (user == null) {
            // Not logged in, launch the Log In activity
            loadLoginView();
        }

        validator = new Validator(this);
        validator.setValidationListener(this);

        caseNumberEditText = (EditText) findViewById(R.id.casenumber_edittext);
        fullNameEditText = (EditText) findViewById(R.id.fullname_edittext);
        sexEditText = (EditText) findViewById(R.id.sex_edittext);
        physicianEditText = (EditText) findViewById(R.id.physician_edittext);
        roomEditText = (EditText) findViewById(R.id.room_edittext);
        dateAdmittedEditText = (EditText) findViewById(R.id.dateadmitted_edittext);
        timeAdmittedEditText = (EditText) findViewById(R.id.timedmitted_edittext);
        addButton = (Button) findViewById(R.id.add_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        loadingProgressBar.setVisibility(View.GONE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

                if(isFormValid) {
                    String caseNumber = caseNumberEditText.getText().toString().trim();
                    String fullName= fullNameEditText.getText().toString().trim();
                    String sex = sexEditText.getText().toString().trim();
                    String physician = physicianEditText.getText().toString().trim();
                    String room = roomEditText.getText().toString().trim();
                    String dateAdmitted = dateAdmittedEditText.getText().toString().trim();
                    String timeAdmitted = timeAdmittedEditText.getText().toString().trim();

                    Patient patient = new Patient(caseNumber, fullName, sex, physician, room,
                            dateAdmitted, timeAdmitted);

                    database.child("patients").child(caseNumber).setValue(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddPatient.this, "Patient successfully added", Toast.LENGTH_LONG).show();
                            loadingProgressBar.setVisibility(View.GONE);

                            Intent homeIntent = new Intent(AddPatient.this, Home.class);
                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeIntent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPatient.this, "Failed adding the patient information. Check your internet connection", Toast.LENGTH_LONG).show();
                            loadingProgressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(AddPatient.this, Home.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
