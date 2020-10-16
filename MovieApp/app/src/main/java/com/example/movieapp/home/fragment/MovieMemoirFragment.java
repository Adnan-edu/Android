package com.example.movieapp.home.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.appcontext.MovieGlobal;
import com.example.movieapp.home.MovieMemoAdapter;
import com.example.movieapp.home.RecAdapter;
import com.example.movieapp.model.ImageTitle;
import com.example.movieapp.model.MovieMem;
import com.example.movieapp.model.Person;
import com.example.movieapp.restapi.RestApi;
import com.example.movieapp.restapi.SearchGoogleAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieMemoirFragment extends Fragment {
    private RecyclerView recyclerView;
    private RestApi restApi;
    private List<MovieMem> movieMemListMain;
    private MovieGlobal movieGlobal;
    private Spinner filterSpinner;
    private ArrayAdapter<String> filterAdapter;
    private List<String> filterOptions;
    private MovieMemoAdapter movieMemoAdapter;
    private Button spinnerTriggerBtn;
    public MovieMemoirFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieMemListMain = new ArrayList<>();
        restApi = new RestApi();
        movieGlobal = (MovieGlobal) getActivity().getApplicationContext();
        filterOptions = new ArrayList<>();
        filterOptions.add("Date");
        filterOptions.add("Rating Scores");
        filterOptions.add("Public Review Scores");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_memoir, container, false);
        filterSpinner = view.findViewById(R.id.spinnerFilterId);
        recyclerView = view.findViewById(R.id.movMemRecViewId);
        filterAdapter = new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item, filterOptions);
        filterSpinner.setAdapter(filterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        spinnerTriggerBtn = view.findViewById(R.id.spinnerTrigger);
        GetMovieMemoList getMovieMemoList = new GetMovieMemoList();
        getMovieMemoList.execute();
        spinnerTriggerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortBasedOnFilter();
                movieMemoAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private class GetMovieMemoList extends AsyncTask<Void, Void, List<MovieMem>>
    {
        @Override
        protected List<MovieMem> doInBackground(Void... lists) {
            Person person = movieGlobal.getPerson();
            List<MovieMem> movieMemList1 = restApi.getMovieMemoList(person);
            return movieMemList1;
        }

        @Override protected void onPostExecute(List<MovieMem> result)
        {
            movieMemListMain = result;
            Collections.sort(movieMemListMain, new Comparator<MovieMem>() {
                @Override
                public int compare(MovieMem o1, MovieMem o2) {
                    return o1.getMemoir().getMoviereldate().compareTo(o2.getMemoir().getMoviereldate());
                }
            });
            movieMemoAdapter = new MovieMemoAdapter(getActivity(),result);
            recyclerView.setAdapter(movieMemoAdapter);

        }
    }

    private void sortBasedOnFilter(){
        if(filterSpinner.getSelectedItem().equals("Date")){
            Collections.sort(movieMemListMain, new Comparator<MovieMem>() {
                @Override
                public int compare(MovieMem o1, MovieMem o2) {
                    return o2.getMemoir().getMoviereldate().compareTo(o1.getMemoir().getMoviereldate());
                }
            });
        }
        else if(filterSpinner.getSelectedItem().equals("Rating Scores")){
            Collections.sort(movieMemListMain, new Comparator<MovieMem>() {
                @Override
                public int compare(MovieMem o1, MovieMem o2) {
                    return o2.getMemoir().getRatingscore().compareTo(o1.getMemoir().getRatingscore());
                }
            });

        }else if(filterSpinner.getSelectedItem().equals("Public Review Scores")){
            Collections.sort(movieMemListMain, new Comparator<MovieMem>() {
                @Override
                public int compare(MovieMem o1, MovieMem o2) {
                    return o2.getImageTitle().getRating().compareTo(o1.getImageTitle().getRating());
                }
            });
        }
    }
}
