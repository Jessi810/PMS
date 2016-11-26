package com.jessi.pms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jessi.pms.R;
import com.jessi.pms.models.Monitor;
import com.jessi.pms.models.Patient;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jessi on 11/21/2016.
 */

public class PatientMonitorAdapter extends ArrayAdapter<Monitor> {
    private HashMap<String, String> textValues = new HashMap<String, String>();

    public PatientMonitorAdapter(Context context, ArrayList<Monitor> monitors) {
        super(context, 0, monitors);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Monitor monitor = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_monitor, parent, false);
        }

        // Lookup view for data population
        TextView idTextView = (TextView) convertView.findViewById(R.id.list_id);
        TextView fullNameTextView = (TextView) convertView.findViewById(R.id.list_fullname);
        TextView sexTextView = (TextView) convertView.findViewById(R.id.list_sex);
        TextView nurseAssignedTextView = (TextView) convertView.findViewById(R.id.list_nurseassigned);
        TextView roomTextView = (TextView) convertView.findViewById(R.id.list_room);
        TextView medicinesTextView = (TextView) convertView.findViewById(R.id.list_medicines);

        // Populate the data into the template view using the data object
        idTextView.setText(monitor.id);
        fullNameTextView.setText(monitor.fullname);
        nurseAssignedTextView.setText(monitor.nurseAssigned);
        roomTextView.setText(monitor.room);
        medicinesTextView.setText(monitor.medicine);

        // Return the completed view to render on screen
        return convertView;
    }
}
