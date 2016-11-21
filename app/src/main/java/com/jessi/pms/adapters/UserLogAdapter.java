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
import com.jessi.pms.models.UserLog;

import java.util.ArrayList;

/**
 * Created by Jessi on 11/21/2016.
 */

public class UserLogAdapter extends ArrayAdapter<UserLog> {
    public UserLogAdapter(Context context, ArrayList<UserLog> userLogs) {
        super(context, 0, userLogs);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserLog userLog = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_user_log, parent, false);
        }

        TextView idTextView = (TextView) convertView.findViewById(R.id.list_id);
        TextView roleTextView = (TextView) convertView.findViewById(R.id.list_role);
        TextView usernameTextView = (TextView) convertView.findViewById(R.id.list_username);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.list_date);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.list_time);

        idTextView.setText(userLog.id);
        roleTextView.setText(userLog.role);
        usernameTextView.setText(userLog.username);
        dateTextView.setText(userLog.date);
        timeTextView.setText(userLog.time);

        return convertView;
    }
}
