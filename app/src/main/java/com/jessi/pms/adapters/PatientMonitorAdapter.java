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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_patients_monitor, parent, false);
        }

        // Lookup view for data population
        TextView idTextView = (TextView) convertView.findViewById(R.id.list_id);
        TextView fullNameTextView = (TextView) convertView.findViewById(R.id.list_fullname);
        TextView physicianTextView = (TextView) convertView.findViewById(R.id.list_physician);
        TextView roomTextView = (TextView) convertView.findViewById(R.id.list_room);
        TextView drug1TextView = (TextView) convertView.findViewById(R.id.med1_drug);
        TextView drug2TextView = (TextView) convertView.findViewById(R.id.med2_drug);
        TextView time1aTextView = (TextView) convertView.findViewById(R.id.med1_time1);
        TextView time1bTextView = (TextView) convertView.findViewById(R.id.med1_time2);
        TextView time1cTextView = (TextView) convertView.findViewById(R.id.med1_time3);
        TextView time2aTextView = (TextView) convertView.findViewById(R.id.med2_time1);
        TextView time2bTextView = (TextView) convertView.findViewById(R.id.med2_time2);
        TextView time2cTextView = (TextView) convertView.findViewById(R.id.med2_time3);

        // Populate the data into the template view using the data object
        idTextView.setText(monitor.id);
        fullNameTextView.setText(monitor.fullname);
        physicianTextView.setText(monitor.physician);
        roomTextView.setText(monitor.room);
        drug1TextView.setText(monitor.drug1);
        drug2TextView.setText(monitor.drug2);
        time1aTextView.setText(monitor.time1a);
        time1bTextView.setText(monitor.time1b);
        time1cTextView.setText(monitor.time1c);
        time2aTextView.setText(monitor.time2a);
        time2bTextView.setText(monitor.time2b);
        time2cTextView.setText(monitor.time2c);

        // Return the completed view to render on screen
        return convertView;
    }
}
