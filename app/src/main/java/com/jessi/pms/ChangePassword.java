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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

/**
 * Created by Jessi on 11/20/2016.
 */

public class ChangePassword extends AppCompatActivity implements Validator.ValidationListener {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private Validator validator;

    private Button saveButton, cancelButton;

    @Password
    private EditText newPasswordEditText;

    @ConfirmPassword
    private EditText confirmPasswordEditText;

    private ProgressBar loadingProgressBar;

    private boolean isFormValid = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

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
        newPasswordEditText = (EditText) findViewById(R.id.newpassword_edittext);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmpassword_edittext);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        loadingProgressBar.setVisibility(View.GONE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
                loadingProgressBar.setVisibility(View.VISIBLE);

                if(isFormValid) {
                    String newPassword = newPasswordEditText.getText().toString().trim();
                    String confirmPassword = confirmPasswordEditText.getText().toString();

                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChangePassword.this, "Password is updated!", Toast.LENGTH_LONG).show();
                                        cancelButton.performClick();
                                    } else {
                                        Toast.makeText(ChangePassword.this, "Failed to update password!", Toast.LENGTH_LONG).show();
                                        loadingProgressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(ChangePassword.this, Profile.class);
                profileIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(profileIntent);
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

        //loadingProgressBar.setVisibility(View.GONE);
    }
}
