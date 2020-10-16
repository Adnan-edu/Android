package com.example.movieapp.home.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.movieapp.R;
import com.example.movieapp.appcontext.MovieGlobal;
import com.example.movieapp.entity.WatchList;
import com.example.movieapp.model.Cinema;
import com.example.movieapp.model.ImageTitle;
import com.example.movieapp.model.Memoir;
import com.example.movieapp.model.Person;
import com.example.movieapp.restapi.RestApi;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMemoirFragment extends Fragment {

    private ImageTitle imageTitle;
    private ImageView imageView;
    private TextView movTitRel;
    private TextView moviInfo;
    private TextView movRatings;
    private List<Cinema> cinemaList;
    private List<String> cinemaName;
    private List<String> suburbName;
    private List<String> cinemaSuburbList;
    private RestApi restApi= null;
    private Spinner cinemaSpinner;
    private Button datePickerButton;
    private Button timePickerButton;
    private EditText editCommentText;
    private RatingBar ratingBar;
    private TextView dateTxtView;
    private TextView timeTxtView;
    private Button submitMemoButton;
    private ArrayAdapter<String> cinemaAdapter;
    private MovieGlobal movieGlobal;
//    private Spinner suburbSpinner;

    public AddMemoirFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            imageTitle = (ImageTitle) bundle.getSerializable("AddMemoData");
        }
        cinemaList = new ArrayList<>();
        cinemaName = new ArrayList<>();
        suburbName = new ArrayList<>();
        cinemaSuburbList = new ArrayList<>();
        restApi = new RestApi();
        movieGlobal = (MovieGlobal) getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_memoir, container, false);

        cinemaSpinner= v.findViewById(R.id.cinemaNameId);
//        suburbSpinner = v.findViewById(R.id.suburbNmId);

        imageView = v.findViewById(R.id.addMemImg);
        movTitRel = v.findViewById(R.id.titleRelYrMem);
        moviInfo =  v.findViewById(R.id.movieInfMem);
        movRatings = v.findViewById(R.id.txtRtngMem);

        datePickerButton = v.findViewById(R.id.datePickerBtn);
        timePickerButton = v.findViewById(R.id.timePickerBtn);
        editCommentText = v.findViewById(R.id.editCommentTxt);
        ratingBar = v.findViewById(R.id.ratingBarId);
        dateTxtView = v.findViewById(R.id.dateTxtViewId);
        timeTxtView = v.findViewById(R.id.timeTxtViewId);
        submitMemoButton = v.findViewById(R.id.submitMemoId);

        Picasso.get().load(imageTitle.getImageUrl()).
                into(imageView);
        movTitRel.setText(imageTitle.getMovieTitle());
        moviInfo.setText(imageTitle.getMovieInfo());
        movRatings.setText(imageTitle.getRating());

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

        submitMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double ratingScore = Double.parseDouble(String.valueOf(ratingBar.getRating()));
                String comment = editCommentText.getText().toString();
                String[] titleYear = imageTitle.getMovieTitle().split("[\\(\\)]"); // Need to parse movie title and release year
                String movieTitle =  titleYear[0];
                String movieRelYear = titleYear[1];

                Cinema cinema = selectedCinema();
                Person person = movieGlobal.getPerson();
                Memoir memoir = new Memoir();
                memoir.setCinemaid(cinema);
                memoir.setPersonid(person);
                String movieRel = movieRelYear +"-02-15T00:00:00+11:00"; // Need to provide to make ISO offset date time
/*
                TimeZone tz = TimeZone.getTimeZone("UTC");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"); // Quoted "Z" to indicate UTC, no timezone offset
                df.setTimeZone(tz);

                SimpleDateFormat formatter6=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");*/

                String watchingDateTime = dateTxtView.getText().toString().trim()+"T" + timeTxtView.getText().toString().trim()+":00+11:00";// + "T12:30:00+11:00";// + timeTxtView.getText().toString().trim();

              /*  SimpleDateFormat formatterNew=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                String nowAsISO = String.valueOf(formatterNew.format(watchingDateTime));*/



                memoir.setWatchingdatetime(watchingDateTime);
                memoir.setRatingscore(ratingScore);
                memoir.setComment(comment);
                memoir.setMoviename(movieTitle);
                memoir.setMoviereldate(movieRel);

                AddMemoirTask addMemoirTask = new AddMemoirTask();
                addMemoirTask.execute(memoir);

            }
        });

        FetchCinemaTask fetchCinemaTask = new FetchCinemaTask();
        fetchCinemaTask.execute();

        return v;
    }

    private Cinema selectedCinema(){
        String[] selectedSpinner = cinemaSpinner.getSelectedItem().toString().split("-");
        for(Cinema cinema: cinemaList){
            if(cinema.getCinemaname().equals(selectedSpinner[0]) && cinema.getSuburb().equals(selectedSpinner[1])){
                return cinema;
            }
        }
        return null;
    }

    private class FetchCinemaTask extends AsyncTask<String, Void, List<Cinema>>
    {
        @Override
        protected List<Cinema> doInBackground(String... params)
        {
            return restApi.fetchAllCinemas();
        }
        @Override
        protected void onPostExecute(List<Cinema> cinemas)
        {
            cinemaList = cinemas;
            for(Cinema cinema: cinemaList){
                cinemaSuburbList.add(cinema.getCinemaname()+"-"+cinema.getSuburb());
                cinemaName.add(cinema.getCinemaname());
                suburbName.add(cinema.getSuburb());
            }
            cinemaAdapter = new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item, cinemaSuburbList);
            cinemaSpinner.setAdapter(cinemaAdapter);
        }
    }
    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("yyyy-MM-dd", calendar1).toString();

                dateTxtView.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(getActivity());

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("HH:mm", calendar1).toString();
                timeTxtView.setText(dateText);
            }
        }, HOUR, MINUTE, true);

        timePickerDialog.show();

    }

    private class AddMemoirTask extends AsyncTask<Memoir, Void, String>
    {
        @Override
        protected String doInBackground(Memoir... memoirs)
        {
            return restApi.addMovieMemoir(memoirs[0]);
        }
        @Override
        protected void onPostExecute(String result)
        {
        }
    }
}
