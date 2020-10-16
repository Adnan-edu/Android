package com.example.movieapp.home.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.database.WatchListDatabase;
import com.example.movieapp.entity.WatchList;
import com.example.movieapp.home.WatchListAdapter;
import com.example.movieapp.viewmodel.WatchListViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class WatchListFragment extends Fragment implements WatchListAdapter.DeleteWatchEvent {
    private WatchListAdapter watchListAdapter;
    private List<WatchList> watchListList;
    private RecyclerView recyclerView;
    private WatchListDatabase watchListDatabase = null;
    private WatchListViewModel watchListViewModel;
    private List<WatchList> watchListsLiveData;
    private List<WatchList> watchListsLiveDataExpr;

    public WatchListFragment() {
        watchListList = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watchListDatabase = WatchListDatabase.getInstance(getActivity());
        watchListsLiveDataExpr = new ArrayList<>();
        watchListsLiveData = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watch_list, container, false);

        recyclerView = view.findViewById(R.id.recViewWatchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        watchListViewModel.initalizeVars(getActivity().getApplication());
        watchListViewModel.getAllWatchLists().observe(this, new Observer<List<WatchList>>() {
            @Override
            public void onChanged(@Nullable final List<WatchList> watchLists) {
                watchListsLiveData.clear();
                watchListsLiveDataExpr.clear();
                for (WatchList temp : watchLists) {
                    //watchListsLiveData.add(new WatchList(temp.getMovieNameRelYear(), temp.getStorageDate()));
                    watchListsLiveData.add(temp);

                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date d1 = new Date();
                    try {
                        d1 = formatter.parse(temp.getStorageDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long diffInMillies = Math.abs(d1.getTime() - date.getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    Log.d("DATECOMPARE", "Actual Date " + diff);
                    if (diff >= 7) {
                        watchListsLiveDataExpr.add(new WatchList(temp.getMovieNameRelYear(), temp.getStorageDate()));
                        //It's more than 7 days.
                        Log.d("DATECOMPARE", "It was added 7 days before");
                    } else {
                        Log.d("DATECOMPARE", "It was added recently" + d1.compareTo(date));
                    }
                }
                //textView_read.setText("All data: " + allCustomers);
                if (watchListsLiveData != null) {

                    watchListAdapter = new WatchListAdapter(getActivity(), watchListsLiveData, WatchListFragment.this, watchListsLiveDataExpr);
                    recyclerView.setAdapter(watchListAdapter);
                }
            }
        });


        return view;
    }

    @Override
    public void deleteWatchEventListener(int position) {
        WatchList watchList = watchListsLiveData.get(position);
        String name = watchList.getMovieNameRelYear();
        watchListViewModel.delete(watchList);
        Toast.makeText(getActivity(), name + " has been deleted from watchlist." + position, Toast.LENGTH_LONG).show();
    }
}
