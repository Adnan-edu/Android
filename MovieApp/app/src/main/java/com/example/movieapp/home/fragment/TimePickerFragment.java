package com.example.movieapp.home.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.example.movieapp.home.HomeActivity;

import java.util.Calendar;
public class TimePickerFragment extends DialogFragment {
    private Context context;
    private TimePickerDialog.OnTimeSetListener listener;
    public TimePickerFragment(Context context, TimePickerDialog.OnTimeSetListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(context, listener, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}
