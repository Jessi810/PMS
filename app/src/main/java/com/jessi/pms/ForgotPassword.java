package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mobsandgeeks.saripaar.annotation.Email;

/**
 * Created by Jessi on 11/26/2016.
 */

public class ForgotPassword extends AppCompatActivity {
    private ProgressBar loadingProgressBar;
    private Button resetPasswordButton;

    @Email
    private TextView emailTextView;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        emailTextView = (TextView) findViewById(R.id.email_edittext);
        resetPasswordButton = (Button) findViewById(R.id.resetpassword_button);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
        loadingProgressBar.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextView.getText().toString().trim();

                loadingProgressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_LONG).show();
                                    Intent loginIntent = new Intent(getApplicationContext(), Login.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(loginIntent);
                                } else {
                                    Toast.makeText(ForgotPassword.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
                                }

                                loadingProgressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
