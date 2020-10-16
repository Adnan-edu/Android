package com.example.movieapp.home.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.database.WatchListDatabase;
import com.example.movieapp.entity.WatchList;
import com.example.movieapp.model.ImageTitle;
import com.example.movieapp.viewmodel.WatchListViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieViewFragment extends Fragment {

    private ImageTitle imageTitle;
    private Button watchListBtn;
    private Button addMemoirBtn;
    private WatchListDatabase watchListDatabase = null;
    private boolean isEnableWatchList;
    private WatchListViewModel watchListViewModel;
    private RatingBar ratingBar;
    private List<WatchList> watchListsLiveData;
    public MovieViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            imageTitle = (ImageTitle) bundle.getSerializable("MovieView");
            isEnableWatchList = bundle.getBoolean("EnableWatchList");
        }
        watchListDatabase = WatchListDatabase.getInstance(getActivity());
        watchListsLiveData = new ArrayList<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_view, container, false);
        TextView ttleRelValTxt = v.findViewById(R.id.ttleRelVal);

        TextView movieInfoValTxt = v.findViewById(R.id.movieInfoVal);

        ratingBar = v.findViewById(R.id.ratingStar);

        watchListBtn = v.findViewById(R.id.watchList);
        addMemoirBtn = v.findViewById(R.id.addMemoir);
        if(!isEnableWatchList){
            watchListBtn.setEnabled(isEnableWatchList);
        }

        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        watchListViewModel.initalizeVars(getActivity().getApplication());
        watchListViewModel.getAllWatchLists().observe(this, new Observer<List<WatchList>>()
        {
            @Override public void onChanged(@Nullable final List<WatchList> watchLists)
            { watchListsLiveData.clear();
            for (WatchList temp : watchLists)
                {
                    watchListsLiveData.add(new WatchList(temp.getMovieNameRelYear(),temp.getStorageDate()));
                }
                //textView_read.setText("All data: " + allCustomers);
            } });

        ttleRelValTxt.setText(imageTitle.getMovieTitle());
        movieInfoValTxt.setText(imageTitle.getMovieInfo());
        float ratingConvert = Float.valueOf(imageTitle.getRating()) / 2;
        ratingBar.setRating(ratingConvert);

        addMemoirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddMemoirFragment addMemoirFragment = new AddMemoirFragment();
                Bundle b = new Bundle();
                b.putSerializable("AddMemoData",imageTitle);
                addMemoirFragment.setArguments(b);
                fragmentTransaction.replace(R.id.content_frame, addMemoirFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        watchListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWatchListExist(imageTitle)){
                    showMessage("Movie already exists in local database.");
                }else{
                    addMovieLocalDb();
                }

/*                ReadWatchListFrmDb readWatchListFrmDb = new ReadWatchListFrmDb();
                readWatchListFrmDb.execute(imageTitle);*/
            }
        });

        return v;
    }

    public boolean isWatchListExist(ImageTitle imageTitle){
        for (WatchList tempWatchObj : watchListsLiveData)
        {
            if(tempWatchObj.getMovieNameRelYear().equals(imageTitle.getMovieTitle())){
                return true;
            }
        }
        return false;
    }

    /*public boolean checkMovieExists(){
        return false;
    }*/
   /* private class ReadWatchListFrmDb extends AsyncTask<Object, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Object... objects)
        {
            List<WatchList> watchListsLiveData = watchListDatabase.watchlistDao().getAll();
            String movieName = watchLists.get(0).getMovieNameRelYear();
            ImageTitle imageTitle =(ImageTitle) objects[0];
            String allUsers = "";
            for (WatchList tempWatchObj : watchLists)
                {
                    //String movieName = tempWatchObj.getMovieNameRelYear();
                    if(tempWatchObj.getMovieNameRelYear().equals(imageTitle.getMovieTitle())){
                        return true;
                    }
                }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean details)
        {

        }
    }*/

    private void addMovieLocalDb(){

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        InsertWLstDatabase insertWLstDatabase = new InsertWLstDatabase();

        //SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");

        //Static Date Add Start
        Date staticDate = new Date();
        insertWLstDatabase.execute("13/05/2020 09:10:36",imageTitle.getMovieTitle());

        //Static Date Add End

        //insertWLstDatabase.execute(formatter.format(date),imageTitle.getMovieTitle());

    }

    private void showMessage(String msg){
        Toast.makeText(getContext().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private class InsertWLstDatabase extends AsyncTask<String, Void, Integer>
    {
        @Override
        protected Integer doInBackground(String... params)
        {
            WatchList watchList = new WatchList(params[1],params[0]);
            int id = (int) watchListDatabase.watchlistDao().insert(watchList);
            return id;
        }
        @Override
        protected void onPostExecute(Integer uid)
        {
            if(uid>0){
                showMessage("Movie has been added in the watchlist.");
            }
        }
    }


}
