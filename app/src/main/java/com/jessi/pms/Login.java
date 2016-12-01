package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.jessi.pms.models.UserLog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Jessi on 11/19/2016.
 */

public class Login extends AppCompatActivity implements Validator.ValidationListener {

    private boolean isFormValid = false;

    @NotEmpty
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView, forgotPasswordTextView;
    private Spinner roleSpinner;
    private ProgressBar loadingProgressBar;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private Validator validator;
    private String userId;
    private String currentUsername;
    private String role;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        validator = new Validator(this);
        validator.setValidationListener(this);

        usernameEditText = (EditText) findViewById(R.id.username_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);
        loginButton = (Button) findViewById(R.id.login_button);
        registerTextView = (TextView) findViewById(R.id.register_textview);
        forgotPasswordTextView = (TextView) findViewById(R.id.forgotpassword_textview);
        roleSpinner = (Spinner) findViewById(R.id.role_spinner);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        loadingProgressBar.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

                if(isFormValid) {
                    loadingProgressBar.setVisibility(View.VISIBLE);

                    final String username = usernameEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();

                    auth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Login successful.", Toast.LENGTH_LONG).show();

                                        user = auth.getCurrentUser();
                                        userId = user.getUid();

                                        // Get the username and role of the logged in user
                                        database.child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                currentUsername = dataSnapshot.child("username").getValue().toString();
                                                role = dataSnapshot.child("role").getValue().toString();
                                                Log.v("Logs", userId);
                                                Log.v("Logs", currentUsername);
                                                Log.v("Logs", role);

                                                Date date = new Date();
                                                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                                                DateFormat timeFormat = new SimpleDateFormat("H:mm:ss");

//                                                UserLog userLog = new UserLog(userId, role, currentUsername, dateFormat.format(date), timeFormat.format(date));
//                                                uid = database.child("Logs").push().getKey();
//                                                Log.v("Logs", uid);
//                                                database.child("Logs").child(uid).setValue(userLog);

                                                String lkey = database.child("Logs").push().getKey();
                                                Log.v("Logs", lkey);
                                                UserLog log = new UserLog(lkey, userId, role, currentUsername, dateFormat.format(date), timeFormat.format(date));
                                                database.child("Logs").child(lkey).setValue(log);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                        Intent homeIntent = new Intent(Login.this, Home.class);
                                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(homeIntent);
                                    } else {
                                        Toast.makeText(Login.this, "Authentication failed. " + task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                        loadingProgressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPasswordIntent = new Intent(Login.this, ForgotPassword.class);
                startActivity(forgotPasswordIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clear) {
            usernameEditText.setText("");
            passwordEditText.setText("");
            return true;
        }
        if (id == R.id.action_exit) {
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
