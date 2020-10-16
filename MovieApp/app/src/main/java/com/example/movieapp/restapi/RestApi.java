package com.example.movieapp.restapi;

import android.util.Log;

import com.example.movieapp.appcontext.MovieGlobal;
import com.example.movieapp.model.BarGraphModel;
import com.example.movieapp.model.Cinema;
import com.example.movieapp.model.Credentials;
import com.example.movieapp.model.ImageTitle;
import com.example.movieapp.model.Memoir;
import com.example.movieapp.model.MovieMem;
import com.example.movieapp.model.MoviesPerMonth;
import com.example.movieapp.model.Person;
import com.example.movieapp.model.SignUpModel;
import com.example.movieapp.model.TopFiveMovie;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestApi {
    private static final String BASE_URL = "http://10.0.2.2:8080/MovieMemory/webresources";
    private OkHttpClient client=null;
    private static final String TAG = "ADDMEMOIRDATA";


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public RestApi()
    {
        client=new OkHttpClient();
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    public String verifyUserLogIn(String username, String password){
        String results = null;
        final String methodPath = "/restmemory.credentials/findByIdPassword/"+username+"/"+password;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return results;
    }

    public List<TopFiveMovie> topFiveRecentYrsMoviesList(int personId){
        String results = null;
        final String methodPath = "/restmemory.memoir/findRecntYrssMovByRating/"+personId;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        List<TopFiveMovie> outputList = null;
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
            outputList = stringToArray(results, TopFiveMovie[].class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return outputList;
    }

    public String addCinema(String[] cinemaDetails){
        Cinema cinema = new Cinema(cinemaDetails[0], cinemaDetails[1], cinemaDetails[2]);
        Gson gson = new Gson();
        String cinemaJson = gson.toJson(cinema);
        String strResponse="";
        final String methodPath = "/restmemory.cinema/";
        RequestBody body = RequestBody.create(cinemaJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return strResponse;

    }

    public String addPersonInServer(SignUpModel signUpModel){
        String finalResult = "";
        SignUpModel signUpModel1 = signUpModel;
        Gson gson = new Gson();
        String signUpJson = gson.toJson(signUpModel1.getPerson());
        String strResponse="";
        final String methodPath = "/restmemory.person/getid/";
        RequestBody body = RequestBody.create(signUpJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return finalResult;
    }

    public String addCredentials(SignUpModel signUpModel){
        String finalResult = "";
        SignUpModel signUpModel1 = signUpModel;
        Gson gson = new Gson();
        String signUpJson = gson.toJson(signUpModel1.getPerson());
        String strResponse="";
        final String credMethodPath = "/restmemory.credentials/";
        RequestBody body = RequestBody.create(signUpJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + credMethodPath).post(body).build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return strResponse;
    }

    public List<Cinema> fetchAllCinemas(){
        Gson gson = new Gson();
        final String methodPath = "/restmemory.cinema/";
        String results = "";
        List<Cinema> outputList = null;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results=response.body().string();
            outputList = stringToArray(results, Cinema[].class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return outputList;
    }

    public String addMovieMemoir(Memoir memoir){
        Memoir addMemoir = memoir;
        Gson gson = new Gson();
        String memoirJson = gson.toJson(addMemoir);
        String strResponse="";
        final String methodPath = "/restmemory.memoir/";
        RequestBody body = RequestBody.create(memoirJson, JSON);
        Request request = new Request.Builder().url(BASE_URL + methodPath).post(body).build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.d(TAG, "memoirData: Name: " + strResponse);
        return strResponse;

    }

    public List<MovieMem> getMovieMemoList(Person person){
            List<MovieMem> movieMems = new ArrayList<>();
            List<String> movieTitles = new ArrayList<>();
            List<ImageTitle> imageTitleList = null;
            Gson gson = new Gson();
            final String methodPath = "/restmemory.memoir/findByPresonid/"+person.getPersonid();
            ArrayList<String> movieImagesList = new ArrayList<>();
            String results = "";
            List<Memoir> outputList = null;
            Request.Builder builder = new Request.Builder();
            builder.url(BASE_URL + methodPath);
            Request request = builder.build();
            try {
                Response response = client.newCall(request).execute();
                results=response.body().string();
                outputList = stringToArray(results, Memoir[].class);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
            movieTitles.add(outputList.get(0).getMoviename());
            for(Memoir memoir:outputList){
                if(movieTitles.contains(memoir.getMoviename())){
                    continue;
                }else{
                    movieTitles.add(memoir.getMoviename());
                }
            }
            if(outputList!=null && outputList.size()>0){
                imageTitleList = SearchGoogleAPI.getImageUrlList(movieTitles);
            }

            for(int i=0;i<outputList.size();i++){
                for(int j=0;j<imageTitleList.size();j++){
                    String mytitle = outputList.get(i).getMoviename().replaceAll("\\s+","").replaceAll(":","").toLowerCase();
                    String[] titleYear = imageTitleList.get(j).getMovieTitle().split("[\\(\\)]");
                    String movieTitle =  titleYear[0].replaceAll("\\s+","").replaceAll(":","").toLowerCase();
                    String movieRelYear = titleYear[1];
                    if(mytitle.equals(movieTitle))
                    {
                        movieMems.add(new MovieMem(outputList.get(i),imageTitleList.get(j)));
                    }
                }
            }
            return movieMems;
        }

    public List<BarGraphModel> moviesWatchPerSuburb(String[] dateDetails) {
        String startDate = dateDetails[0] + "T00:00:00+11:00";
        String endDate = dateDetails[1] + "T00:00:00+11:00";
        List<BarGraphModel> outputList = null;
        String results="";
        try {
            final String methodPath = "/restmemory.memoir/findCinemaByPerson/" + 2 + "/" + startDate + "/" + endDate;
            Request.Builder builder = new Request.Builder(); builder.url(BASE_URL + methodPath);
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            results=response.body().string();
            outputList = stringToArray(results, BarGraphModel[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputList;
    }

    public List<MoviesPerMonth> moviesWatchedPerMonth(String[] details) {
        List<MoviesPerMonth> outputList = null;
        String results="";
        try {
            final String methodPath = "/restmemory.memoir/findByMemCinPerYr/" + 1 + "/" + details[0];
            Request.Builder builder = new Request.Builder();
            builder.url(BASE_URL + methodPath);
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            results=response.body().string();
            outputList = stringToArray(results, MoviesPerMonth[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputList;
    }
}
