package com.example.movieapp.home.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.model.Person;
import com.example.movieapp.model.TopFiveMovie;
import com.example.movieapp.restapi.RestApi;
import com.example.movieapp.utility.Hashing;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RestApi restApi = null;
    private Person person = null;
    private TextView textView11;
    private TextView textView12;
    private TextView textView13;

    private TextView textView21;
    private TextView textView22;
    private TextView textView23;

    private TextView textView31;
    private TextView textView32;
    private TextView textView33;

    private TextView textView41;
    private TextView textView42;
    private TextView textView43;

    private TextView textView51;
    private TextView textView52;
    private TextView textView53;

    private TextView assignUserName;

    public HomeFragment() {
        restApi = new RestApi();
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            person = (Person) bundle.getSerializable("person");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textView11 = view.findViewById(R.id.movName1);
        textView12 = view.findViewById(R.id.relDate1);
        textView13 = view.findViewById(R.id.ratingScore1);

        textView21 = view.findViewById(R.id.movName2);
        textView22 = view.findViewById(R.id.relDate2);
        textView23 = view.findViewById(R.id.ratingScore2);

        textView31 = view.findViewById(R.id.movName3);
        textView32 = view.findViewById(R.id.relDate3);
        textView33 = view.findViewById(R.id.ratingScore3);

        textView41 = view.findViewById(R.id.movName4);
        textView42 = view.findViewById(R.id.relDate4);
        textView43 = view.findViewById(R.id.ratingScore4);

        textView51 = view.findViewById(R.id.movName5);
        textView52 = view.findViewById(R.id.relDate5);
        textView53 = view.findViewById(R.id.ratingScore5);

        TextView currentTxtDate = view.findViewById(R.id.currentDateTxt);
        currentTxtDate.setText(getRecentDate());

        assignUserName = view.findViewById(R.id.currentUserName);
        assignUserName.setText("Welcome "+person.getFirstname());
        // Inflate the layout for this fragment
        return view;
    }
    private class GetTopRatedRecentMov extends AsyncTask<Void, Void, List<TopFiveMovie>>
    {
        @Override protected List<TopFiveMovie> doInBackground(Void... params)
        {
            return restApi.topFiveRecentYrsMoviesList(person.getPersonid());
        }

        @Override protected void onPostExecute(List<TopFiveMovie> result)
        {
            textView11.setText(result.get(0).getMoviename());
            textView12.setText(result.get(0).getMoviereldate());
            textView13.setText(String.valueOf(result.get(0).getRatingscore()));

            textView21.setText(result.get(1).getMoviename());
            textView22.setText(result.get(1).getMoviereldate());
            textView23.setText(String.valueOf(result.get(1).getRatingscore()));

            textView31.setText(result.get(2).getMoviename());
            textView32.setText(result.get(2).getMoviereldate());
            textView33.setText(String.valueOf(result.get(2).getRatingscore()));

            textView41.setText(result.get(3).getMoviename());
            textView42.setText(result.get(3).getMoviereldate());
            textView43.setText(String.valueOf(result.get(3).getRatingscore()));

            textView51.setText(result.get(4).getMoviename());
            textView52.setText(result.get(4).getMoviereldate());
            textView53.setText(String.valueOf(result.get(4).getRatingscore()));

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetTopRatedRecentMov getTopRatedRecentMov = new GetTopRatedRecentMov();
        getTopRatedRecentMov.execute();
    }
    private String getRecentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
