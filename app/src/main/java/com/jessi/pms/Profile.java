package com.jessi.pms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jessi.pms.models.EditUser;
import com.jessi.pms.models.Users;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jessi on 11/20/2016.
 */

public class Profile extends AppCompatActivity implements Validator.ValidationListener {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private Validator validator;
    private String userId;

    @Length(min = 2, max = 100)
    private EditText fullnameEditText;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    private Button saveButton, cancelButton, changePasswordButton;
    private boolean isFormValid = false;
    private ProgressBar loadingProgressBar;
    private TextView roleTextView;

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

        settings = getSharedPreferences("userInfo", 0);
        editor = settings.edit();

        database = FirebaseDatabase.getInstance().getReference();

        validator = new Validator(this);
        validator.setValidationListener(this);

        fullnameEditText = (EditText) findViewById(R.id.fullname_edittext);
        fullnameEditText.setText(settings.getString("setting_ufn", ""));

        roleTextView = (TextView) findViewById(R.id.role_textview);
        roleTextView.setText(settings.getString("setting_ur", ""));

        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        changePasswordButton = (Button) findViewById(R.id.changepassword_button);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        loadingProgressBar.setVisibility(View.GONE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                validator.validate();

                if(isFormValid) {
                    String fullname = fullnameEditText.getText().toString().trim();

                    userId = user.getUid();

                    //EditUser user = new EditUser(fullname);
                    Map<String, Object> newInfo = new HashMap<String, Object>();
                    newInfo.put("fullname", fullname);
                    database.child("Users").child(userId).updateChildren(newInfo);
                    database.child("Users").child(userId).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Toast.makeText(Profile.this, "Account is updated!", Toast.LENGTH_LONG).show();
                            loadingProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            Toast.makeText(Profile.this, "Account is updated!", Toast.LENGTH_LONG).show();
                            String newFullName = "";
                            try {
                                newFullName = dataSnapshot.getValue().toString();
                                editor.putString("setting_ufn", newFullName);
                                editor.commit();
                            } catch (Exception e) {
                                android.util.Log.v("sharedpref", e.getMessage());
                            }
                            android.util.Log.v("sharedpref", newFullName);
                            loadingProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
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

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePasswordIntent = new Intent(Profile.this, ChangePassword.class);
                startActivity(changePasswordIntent);
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

        loadingProgressBar.setVisibility(View.GONE);
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
