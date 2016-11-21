package com.jessi.pms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jessi.pms.R;
import com.jessi.pms.models.Monitor;
import com.jessi.pms.models.Patient;

import java.util.ArrayList;

/**
 * Created by Jessi on 11/21/2016.
 */

public class MonitorPatientAdapter extends ArrayAdapter<Monitor> {
    public MonitorPatientAdapter(Context context, ArrayList<Monitor> monitors) {
        super(context, 0, monitors);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Monitor monitor = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_monitor_patients, parent, false);
        }

        TextView idTextView = (TextView) convertView.findViewById(R.id.list_id);
        TextView fullNameTextView = (TextView) convertView.findViewById(R.id.list_fullname);
        TextView nurseAssignedTextView = (TextView) convertView.findViewById(R.id.list_nurseAssigned);
        TextView roomTextView = (TextView) convertView.findViewById(R.id.list_room);
        //TextView medicine = (TextView) convertView.findViewById(R.id.list_medItem);

        idTextView.setText(monitor.id);
        fullNameTextView.setText(monitor.fullname);
        nurseAssignedTextView.setText(monitor.nurseAssigned);
        roomTextView.setText(monitor.room);

        return convertView;
    }
}
