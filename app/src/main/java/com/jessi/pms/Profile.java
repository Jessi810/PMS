package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

/**
 * Created by Jessi on 11/20/2016.
 */

public class Profile extends AppCompatActivity implements Validator.ValidationListener {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private Validator validator;

    private Button saveButton, cancelButton;
    private boolean isFormValid = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            // Not logged in, launch the Log In activity
            loadLoginView();
        }

        validator = new Validator(this);
        validator.setValidationListener(this);

        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Profile.this, Home.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
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

        //loadingProgressBar.setVisibility(View.GONE);
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
