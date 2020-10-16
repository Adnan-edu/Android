package com.example.movieapp.home.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.MainActivity;
import com.example.movieapp.R;
import com.example.movieapp.restapi.RestApi;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCinemaFragment extends Fragment {
    EditText cinameNameEdtTxt;
    EditText suburbNameEdtTxt;
    EditText postalCodeEdtTxt;
    private RestApi restApi = null;
    Button addCinemaButton;
    TextView textCinemaAddMsg;

    public AddCinemaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restApi = new RestApi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_cinema, container, false);
        cinameNameEdtTxt = view.findViewById(R.id.cinemaNameEdt);
        suburbNameEdtTxt = view.findViewById(R.id.suburbNameEdt);
        postalCodeEdtTxt = view.findViewById(R.id.postalNameEdt);
        addCinemaButton = view.findViewById(R.id.addCinemaBtn);
        textCinemaAddMsg = view.findViewById(R.id.addCinemaMsg);
        addCinemaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cinemaName = cinameNameEdtTxt.getText().toString().trim();
                String suburbName = suburbNameEdtTxt.getText().toString().trim();
                String postCode = postalCodeEdtTxt.getText().toString().trim();
                //Add validation
                if(cinemaName.length()==0 || suburbName.length()==0 || postCode.length()==0){
                    Toast.makeText(getActivity(),"cinemaName, suburbName or postCode can not be empty",Toast.LENGTH_LONG).show();
                }else{
                    AddCinemaTask addCinemaTask = new AddCinemaTask();
                    addCinemaTask.execute(cinemaName,suburbName,postCode);
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private class AddCinemaTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            String message= " The Cinema " + params[0] + " was added";
            return restApi.addCinema(params)+message;
        }
        @Override
        protected void onPostExecute(String result)
        {
            textCinemaAddMsg.setText(result);
        }
    }
}
