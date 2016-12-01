package com.jessi.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jessi on 12/1/2016.
 */

public class EditMedicine extends AppCompatActivity implements Validator.ValidationListener {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private Validator validator;

    private String drug1, drug2, time1a, time1b, time1c, time2a, time2b, time2c;

    @Nullable
    private EditText drug1EditText, drug2EditText;

    private EditText time1aEditText, time1bEditText, time1cEditText,
            time2aEditText, time2bEditText, time2cEditText;
    private Button saveButton, cancelButton, addTimeslot1, addTimeslot2;
    private CheckBox med1Checkbox, med2Checkbox;
    private ProgressBar loadingProgressBar;

    private String id;
    private int nSlot1, nSlot2;
    private boolean isFormValid = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

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

        validator = new Validator(this);
        validator.setValidationListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            android.util.Log.v("intentTest", "Bundle is Null");
            return;
        }

        id = extras.getString("id", "");
        drug1 = extras.getString("drug1", "");
        drug2 = extras.getString("drug2", "");
        time1a = extras.getString("time1a", "");
        time1b = extras.getString("time1b", "");
        time1c = extras.getString("time1c", "");
        time2a = extras.getString("time2a", "");
        time2b = extras.getString("time2b", "");
        time2c = extras.getString("time2c", "");

        initialize();

        if(!drug1.isEmpty()) med1Checkbox.setChecked(true);
        if(!drug2.isEmpty()) med2Checkbox.setChecked(true);

        setEditTextsEnabled(med1Checkbox.isChecked(), med2Checkbox.isChecked());
        setEditTextsVisibility();

        visibleEditTexts();
        showHideButton(nSlot1, nSlot2);

        med1Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditTextsEnabled(med1Checkbox.isChecked(), med2Checkbox.isChecked());
            }
        });

        med2Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditTextsEnabled(med1Checkbox.isChecked(), med2Checkbox.isChecked());
            }
        });

        addTimeslot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibleEditTexts();
                addEditText(nSlot1);
                showHideButton(nSlot1, nSlot2);
            }
        });

        addTimeslot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibleEditTexts();
                addEditText(nSlot2 * 4);
                showHideButton(nSlot1, nSlot2);
            }
        });

        drug1EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                visibleEditTexts();
                showHideButton(nSlot1, nSlot2);
            }
        });

        drug2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                visibleEditTexts();
                showHideButton(nSlot1, nSlot2);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                //validator.validate();

                if(isFormValid) {
                    String nDrug1 = drug1EditText.getText().toString().trim();
                    String nDrug2 = drug2EditText.getText().toString().trim();
                    String nTime1a = time1aEditText.getText().toString().trim();
                    String nTime1b = time1bEditText.getText().toString().trim();
                    String nTime1c = time1cEditText.getText().toString().trim();
                    String nTime2a = time2aEditText.getText().toString().trim();
                    String nTime2b = time2bEditText.getText().toString().trim();
                    String nTime2c = time2cEditText.getText().toString().trim();

                    Map<String, Object> newInfo = new HashMap<String, Object>();
                    if (med1Checkbox.isChecked()) {
                        if (!nDrug1.isEmpty()) newInfo.put("drug1", nDrug1);
                        if (!nTime1a.isEmpty()) newInfo.put("time1a", nTime1a);
                        if (!nTime1b.isEmpty()) newInfo.put("time1b", nTime1b);
                        if (!nTime1c.isEmpty()) newInfo.put("time1c", nTime1c);
                    }
                    if (med2Checkbox.isChecked()) {
                        if (!nDrug2.isEmpty()) newInfo.put("drug2", nDrug2);
                        if (!nTime2a.isEmpty()) newInfo.put("time2a", nTime2a);
                        if (!nTime2b.isEmpty()) newInfo.put("time2b", nTime2b);
                        if (!nTime2c.isEmpty()) newInfo.put("time2c", nTime2c);
                    }
                    database.child("Patients").child(id).updateChildren(newInfo);
                    database.child("Patients").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Toast.makeText(EditMedicine.this, "Changed succesfully", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    loadingProgressBar.setVisibility(View.GONE);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monitorPatientIntent = new Intent(getApplicationContext(), EditMedicine.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(monitorPatientIntent);
            }
        });
    }

    private void visibleEditTexts() {
        nSlot1 = time1aEditText.getVisibility() + time1bEditText.getVisibility() + time1cEditText.getVisibility();
        nSlot2 = time2aEditText.getVisibility() + time2bEditText.getVisibility() + time2cEditText.getVisibility();
        Log.v("medicinelog", "nSlot1: " + String.valueOf(nSlot1));
        Log.v("medicinelog", "nSlot2: " + String.valueOf(nSlot2) + "\n------------------------------------");
    }

    private void addEditText(int n) {
        switch (n) {
            case 24:
                time1aEditText.setVisibility(View.VISIBLE);
                break;
            case 16:
                time1bEditText.setVisibility(View.VISIBLE);
                break;
            case 8:
                time1cEditText.setVisibility(View.VISIBLE);
                break;
            case 96:
                time2aEditText.setVisibility(View.VISIBLE);
                break;
            case 64:
                time2bEditText.setVisibility(View.VISIBLE);
                break;
            case 32:
                time2cEditText.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void showHideButton(int n1, int n2) {
        if (med1Checkbox.isChecked() && !drug1EditText.getText().toString().isEmpty()) {
            if (n1 > 8) addTimeslot1.setVisibility(View.VISIBLE);
            else addTimeslot1.setVisibility(View.GONE);
        }

        if (med2Checkbox.isChecked() && !drug2EditText.getText().toString().isEmpty()) {
            if (n2 > 8) addTimeslot2.setVisibility(View.VISIBLE);
            else addTimeslot2.setVisibility(View.GONE);
        }
    }

    private void setEditTextsVisibility() {
        time1aEditText.setVisibility(time1a.isEmpty() ? View.GONE : View.VISIBLE);
        time1bEditText.setVisibility(time1b.isEmpty() ? View.GONE : View.VISIBLE);
        time1cEditText.setVisibility(time1c.isEmpty() ? View.GONE : View.VISIBLE);

        time2aEditText.setVisibility(time2a.isEmpty() ? View.GONE : View.VISIBLE);
        time2bEditText.setVisibility(time2b.isEmpty() ? View.GONE : View.VISIBLE);
        time2cEditText.setVisibility(time2c.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void setEditTextsEnabled(boolean checked1, boolean checked2) {
        drug1EditText.setEnabled(checked1);
        drug2EditText.setEnabled(checked2);

        time1aEditText.setEnabled(checked1);
        time1bEditText.setEnabled(checked1);
        time1cEditText.setEnabled(checked1);

        time2aEditText.setEnabled(checked2);
        time2bEditText.setEnabled(checked2);
        time2cEditText.setEnabled(checked2);

        visibleEditTexts();
        showHideButton(nSlot1, nSlot2);
    }

    private void initialize() {
        drug1EditText = (EditText) findViewById(R.id.drug1_edittext);
        drug2EditText = (EditText) findViewById(R.id.drug2_edittext);
        time1aEditText = (EditText) findViewById(R.id.time1a_edittext);
        time1bEditText = (EditText) findViewById(R.id.time1b_edittext);
        time1cEditText = (EditText) findViewById(R.id.time1c_edittext);
        time2aEditText = (EditText) findViewById(R.id.time2a_edittext);
        time2bEditText = (EditText) findViewById(R.id.time2b_edittext);
        time2cEditText = (EditText) findViewById(R.id.time2c_edittext);
        med1Checkbox = (CheckBox) findViewById(R.id.med1_checkbox);
        med2Checkbox = (CheckBox) findViewById(R.id.med2_checkbox);
        addTimeslot1 = (Button) findViewById(R.id.med1_addtime);
        addTimeslot2 = (Button) findViewById(R.id.med2_addtime);
        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        drug1EditText.setText(drug1);
        drug2EditText.setText(drug2);
        time1aEditText.setText(time1a);
        time1bEditText.setText(time1b);
        time1cEditText.setText(time1c);
        time2aEditText.setText(time2a);
        time2bEditText.setText(time2b);
        time2cEditText.setText(time2c);
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
