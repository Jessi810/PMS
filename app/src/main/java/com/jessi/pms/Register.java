package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jessi.pms.models.Users;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

/**
 * Created by Jessi on 11/19/2016.
 */

public class Register extends AppCompatActivity implements Validator.ValidationListener {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private String userId;
    private Validator validator;
    private boolean isFormValid = false;

    @NotEmpty
    @Email
    private EditText emailEditText;

    @NotEmpty
    private EditText usernameEditText;

    @NotEmpty
    @Password
    private EditText passwordEditText;

    @ConfirmPassword
    private EditText confirmPasswordEditText;

    @NotEmpty
    @Length(min = 2, max = 100)
    private EditText fullNameEditText;

    private Button registerButton;
    private Spinner roleSpinner;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        validator = new Validator(this);
        validator.setValidationListener(this);

        emailEditText = (EditText) findViewById(R.id.email_edittext);
        usernameEditText = (EditText) findViewById(R.id.username_edittext);
        fullNameEditText = (EditText) findViewById(R.id.fullname_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmpassword_edittext);
        registerButton = (Button) findViewById(R.id.register_button);
        roleSpinner = (Spinner) findViewById(R.id.role_spinner);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        loadingProgressBar.setVisibility(View.GONE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

                if(isFormValid) {
                    loadingProgressBar.setVisibility(View.VISIBLE);

                    final String email = emailEditText.getText().toString().trim();
                    final String username = usernameEditText.getText().toString().trim();
                    final String fullname = fullNameEditText.getText().toString().trim();
                    final String password = passwordEditText.getText().toString().trim();
                    final String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                    final String role = roleSpinner.getSelectedItem().toString();
                    Log.v("firebase", email);
                    Log.v("firebase", username);
                    Log.v("firebase", fullname);
                    Log.v("firebase", password);
                    Log.v("firebase", confirmPassword);
                    Log.v("firebase", role);

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.v("firebase", "onComplete");

                                    loadingProgressBar.setVisibility(View.GONE);

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Authentication failed. " + task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        user = auth.getCurrentUser();
                                        userId = user.getUid();

                                        Users user = new Users(email, username, fullname, password, role);
                                        database.child("Users").child(userId).setValue(user);

                                        Toast.makeText(Register.this, "Registration complete.", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Register.this, Home.class));
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
