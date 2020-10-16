package com.example.movieapp.home.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.home.RecAdapter;
import com.example.movieapp.model.ImageTitle;
import com.example.movieapp.model.Person;
import com.example.movieapp.restapi.SearchGoogleAPI;
import com.example.movieapp.utility.Hashing;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchMovFragment extends Fragment {

    private String keyword;
    private RecyclerView recyclerView;
    private List<ImageTitle> imageTitles;

    public SearchMovFragment() {
        imageTitles = new ArrayList<>();
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_mov, container, false);
        final EditText editText=view.findViewById(R.id.ed_keyword) ;
        Button btnSearch = view.findViewById(R.id.btn_search);

        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                keyword = editText.getText().toString();
                //create an anonymous AsyncTask
                GetImgUrlTitle getImgUrlTitle = new GetImgUrlTitle();
                getImgUrlTitle.execute();
            }
        });
        return view;

    }

    private class GetImgUrlTitle extends AsyncTask<String, Void, String>
    {
        @Override protected String doInBackground(String... params)
        {
            return SearchGoogleAPI.search(keyword, new String[]{"num"}, new String[]{"10"});
        }
        @Override protected void onPostExecute(String result)
        {
            imageTitles = SearchGoogleAPI.getTitleImgUrl(result);
            recyclerView.setAdapter(new RecAdapter(getActivity(),imageTitles));
        }
    }
}
