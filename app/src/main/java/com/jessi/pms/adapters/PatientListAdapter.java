package com.jessi.pms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jessi.pms.R;
import com.jessi.pms.models.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jessi on 11/21/2016.
 */

public class PatientListAdapter extends ArrayAdapter<Patient> {
    public PatientListAdapter(Context context, ArrayList<Patient> patient) {
        super(context, 0, patient);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Patient patient = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_patients, parent, false);
        }

        // Lookup view for data population
        TextView idTextView = (TextView) convertView.findViewById(R.id.list_id);
        TextView caseNumberTextView = (TextView) convertView.findViewById(R.id.list_casenumber);
        TextView dateTimeTextView = (TextView) convertView.findViewById(R.id.list_datetime);
        TextView fullNameTextView = (TextView) convertView.findViewById(R.id.list_fullname);
        TextView sexTextView = (TextView) convertView.findViewById(R.id.list_sex);
        TextView physicianTextView = (TextView) convertView.findViewById(R.id.list_physician);
        TextView roomTextView = (TextView) convertView.findViewById(R.id.list_room);

        // Populate the data into the template view using the data object
        caseNumberTextView.setText(patient.caseNumber);
        dateTimeTextView.setText(patient.dateAdmitted + " " + patient.timeAdmitted);
        fullNameTextView.setText(patient.fullname);
        sexTextView.setText(patient.sex);
        physicianTextView.setText(patient.physician);
        roomTextView.setText(patient.room);

        // Return the completed view to render on screen
        return convertView;
    }
}
