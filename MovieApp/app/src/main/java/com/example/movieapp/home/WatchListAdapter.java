package com.example.movieapp.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.entity.WatchList;
import com.example.movieapp.home.fragment.AlertReceiver;
import com.example.movieapp.home.fragment.MovieViewFragment;
import com.example.movieapp.home.fragment.TimePickerFragment;
import com.example.movieapp.home.fragment.WatchListFragment;
import com.example.movieapp.model.ImageTitle;
import com.example.movieapp.restapi.SearchGoogleAPI;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class WatchListAdapter  extends RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder> implements TimePickerDialog.OnTimeSetListener {
    private Context context;
    private DeleteWatchEvent deleteWatchEvent;
    private List<WatchList> watchListsExpired;
    private List<WatchList> watchLists;
    public WatchListAdapter(Context context, List<WatchList> watchLists, DeleteWatchEvent deleteWatchEvent,List<WatchList> watchListsExpr){
        this.context = context;
        this.watchLists = watchLists;
        this.deleteWatchEvent = deleteWatchEvent;
        this.watchListsExpired = watchListsExpr;
    }
    @NonNull
    @Override
    public WatchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.watchlist_row,parent,false);
        return new WatchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchListViewHolder holder, final int position) {
        final String title = watchLists.get(position).getMovieNameRelYear();
        final String updatedDateTime = watchLists.get(position).getStorageDate();
        TextView titleView = holder.txtViewTYrVal;
        TextView updatedDtTm = holder.textView4;
        titleView.setText(title);
        updatedDtTm.setText(updatedDateTime);
        Button viewBtnBH = holder.viewBtnWtchLst;
        Button delBtnVH = holder.deleteBtnWtchLst;
        Button timePickerBtnVw = holder.timePickerBtn;
        timePickerBtnVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment(context,WatchListAdapter.this);
                timePicker.show(((HomeActivity)context).getSupportFragmentManager(), "time picker");
            }
        });
        viewBtnBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchSpecificMovie searchSpecificMovie = new SearchSpecificMovie();
                searchSpecificMovie.execute(title);
            }
        });
        delBtnVH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWatchEvent.deleteWatchEventListener(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return watchLists.size();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        startAlarm(c);
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) (context).getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("ExpiredDataWatchList",  String.valueOf(watchListsExpired.size()));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public class WatchListViewHolder extends RecyclerView.ViewHolder{

        TextView txtViewTYrVal;
        TextView textView4;
        Button viewBtnWtchLst;
        Button deleteBtnWtchLst;
        Button timePickerBtn;
        ConstraintLayout rowLayout;
        public WatchListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewTYrVal = itemView.findViewById(R.id.txtViewTYr);
            textView4 = itemView.findViewById(R.id.updatedDtTime);
            viewBtnWtchLst = itemView.findViewById(R.id.viewBtn);
            timePickerBtn = itemView.findViewById(R.id.buttonTrigger);
            deleteBtnWtchLst = itemView.findViewById(R.id.deleteBtn);
            rowLayout = itemView.findViewById(R.id.watchLstRowLayout);
        }
    }

    private class SearchSpecificMovie extends AsyncTask<String, Void, ImageTitle>
    {
        @Override protected ImageTitle doInBackground(String... params)
        {
            String result = SearchGoogleAPI.search(params[0], new String[]{"num"}, new String[]{"10"});
            ImageTitle searchedImageTitle = SearchGoogleAPI.getSpecificMovieInfo(result,params[0]);
            return searchedImageTitle;
        }

        @Override protected void onPostExecute(ImageTitle result)
        {
            FragmentManager fragmentManager = ((HomeActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MovieViewFragment movieViewFragment = new MovieViewFragment();
            Bundle b = new Bundle();
            b.putSerializable("MovieView",result);
            b.putBoolean("EnableWatchList",false);
            movieViewFragment.setArguments(b);
            fragmentTransaction.replace(R.id.content_frame, movieViewFragment).addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public interface DeleteWatchEvent{
        public void deleteWatchEventListener(int position);
    }
}
