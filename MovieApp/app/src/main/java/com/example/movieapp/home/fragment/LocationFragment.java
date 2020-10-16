package com.example.movieapp.home.fragment;

import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.movieapp.R;
import com.example.movieapp.appcontext.MovieGlobal;
import com.example.movieapp.home.PlaceAutoSuggestAdapter;
import com.example.movieapp.model.Person;
import com.google.android.gms.maps.model.LatLng;

import android.location.Address;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {
    private MovieGlobal movieGlobal;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieGlobal = (MovieGlobal) getActivity().getApplicationContext();
        Person person = movieGlobal.getPerson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        Person person = movieGlobal.getPerson();
        String address =  person.getState();
        Log.d("Lat Lng","Lat Lng Address: "+address);
        LatLng latLng=getLatLngFromAddress(address);
        if(latLng!=null) {
            Log.d("Lat Lng : ", " " + latLng.latitude + " " + latLng.longitude);
        }
        else {
             Log.d("Lat Lng","Lat Lng Not Found");
        }
        final AutoCompleteTextView autoCompleteTextView= view.findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(getActivity(),android.R.layout.simple_list_item_1));
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Address : ",autoCompleteTextView.getText().toString());
                LatLng latLng=getLatLngFromAddress(autoCompleteTextView.getText().toString());
                if(latLng!=null) {
                    Log.d("Lat Lng : ", " " + latLng.latitude + " " + latLng.longitude);
                }
                else {
                   // Log.d("Lat Lng","Lat Lng Not Found");
                }

            }
        });
        return view;
    }

    private LatLng getLatLngFromAddress(String address){

        Geocoder geocoder=new Geocoder(getActivity());
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if(addressList!=null){
                Address singleaddress=addressList.get(0);
                LatLng latLng=new LatLng(singleaddress.getLatitude(),singleaddress.getLongitude());
                return latLng;
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
