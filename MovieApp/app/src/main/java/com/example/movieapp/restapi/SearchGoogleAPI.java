package com.example.movieapp.restapi;

import com.example.movieapp.model.ImageTitle;
import com.example.movieapp.model.Memoir;
import com.example.movieapp.model.MovieMem;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SearchGoogleAPI
{
    private static final String API_KEY = "AIzaSyDGW8C-T3XYC5YOo-Tr46nJoNzZGwM7ZE4";
    private static final String SEARCH_ID_cx = "016347136693877103461:9yebzfhegjo";
    public static String search(String keyword, String[] params, String[] values)
    {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null; String textResult = "";
        String query_parameter="";
        if (params!=null && values!=null)
        {
            for (int i =0; i < params.length; i ++)
            {
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "="; query_parameter += values[i];
            }
        }
        try
        {
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+ API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json"); connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine())
            {
                textResult += scanner.nextLine();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            connection.disconnect();
        }
        return textResult;
    }
    public static List<ImageTitle> getTitleImgUrl(String result)
    {
        List<ImageTitle> imageTitleList = new ArrayList<>();
        String snippet = null;
        try{ JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i=0;i<jsonArray.length();i++){
                String title = jsonArray.getJSONObject(i).getString("title");
                JSONObject imageUrl = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("cse_thumbnail").getJSONObject(0);
                String movieRating = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("aggregaterating").getJSONObject(0).getString("ratingvalue");
                String movieDetails = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0).getString("og:description");
                String[] imgURLNew = imageUrl.getString("src").split(",");
                String s1 = imgURLNew[0];
                ImageTitle imageTitle = new ImageTitle();
                imageTitle.setMovieTitle(title);
                imageTitle.setImageUrl(s1);
                imageTitle.setRating(movieRating);
                imageTitle.setMovieInfo(movieDetails);
                imageTitleList.add(imageTitle);
//                hm.put(title, s1);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return imageTitleList;
    }
//.replaceAll("\\s+","")
    public static ImageTitle getSpecificMovieInfo(String result,String val){
        String mytitle = val.replaceAll(":","").trim();
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i=0;i<jsonArray.length();i++){
             try {
                 String title = jsonArray.getJSONObject(i).getString("title");
                 JSONObject imageUrl = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("cse_thumbnail").getJSONObject(0);
                 String movieRating = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("aggregaterating").getJSONObject(0).getString("ratingvalue");
                 String movieDetails = jsonArray.getJSONObject(i).getJSONObject("pagemap").getJSONArray("metatags").getJSONObject(0).getString("og:description");
                 String[] imgURLNew = imageUrl.getString("src").split(",");
                 String s1 = imgURLNew[0];

                 String[] titleYear = title.split("[\\(\\)]");
                 String movieTitle =  titleYear[0].replaceAll(":","").trim();
                 String movieRelYear = titleYear[1];
                 String[] newTitle = mytitle.split("[\\(\\)]");
                 mytitle = newTitle[0].trim();
                 if(mytitle.equals(movieTitle)){
                     ImageTitle imageTitle = new ImageTitle();
                     imageTitle.setMovieTitle(title);
                     imageTitle.setImageUrl(s1);
                     imageTitle.setRating(movieRating);
                     imageTitle.setMovieInfo(movieDetails);
                     return imageTitle;
                 }
             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    //.replaceAll("\\s+","")

    public static List<ImageTitle>  getImageUrlList(List<String> moviesTitle){
        List<ImageTitle> imageTitleList = new ArrayList<>();
        for(String movieMem: moviesTitle){
            String result = search(movieMem.replaceAll(":","").trim(), new String[]{"num"}, new String[]{"10"});
            ImageTitle imageTitle = SearchGoogleAPI.getSpecificMovieInfo(result,movieMem);
            if(imageTitle!=null){
                imageTitleList.add(imageTitle);
            }
        }
        return imageTitleList;

    }

}

