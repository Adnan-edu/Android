package com.example.movieapp.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.home.fragment.MovieViewFragment;
import com.example.movieapp.model.ImageTitle;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {
    Context context;
    private List<ImageTitle> imageTitles;
    public RecAdapter(Context context, List<ImageTitle> imageTitles){
        this.context = context;
        this.imageTitles = imageTitles;
    }
    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row,parent,false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, final int position) {
        final String title = imageTitles.get(position).getMovieTitle();
        final String imageUrl = imageTitles.get(position).getImageUrl();
        final String movieInfo = imageTitles.get(position).getMovieInfo();
        final String ratingMovie = imageTitles.get(position).getRating();
        TextView titleView = holder.text1;
        ImageView imageView = holder.imageView;
        Picasso.get().load(imageUrl).
                into(imageView);
        titleView.setText(title);
        holder.rowLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((HomeActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MovieViewFragment movieViewFragment = new MovieViewFragment();
                ImageTitle imageTitle = new ImageTitle();
                imageTitle.setMovieTitle(title);
                imageTitle.setImageUrl(imageUrl);
                imageTitle.setMovieInfo(movieInfo);
                imageTitle.setRating(ratingMovie);
                Bundle b = new Bundle();
                b.putSerializable("MovieView",imageTitle);
                b.putBoolean("EnableWatchList",true);
                movieViewFragment.setArguments(b);
                fragmentTransaction.replace(R.id.content_frame, movieViewFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageTitles.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder{

        TextView text1;
        ImageView imageView;
        ConstraintLayout rowLayout;
        public RecViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movMemImgViewId);
            text1 = itemView.findViewById(R.id.textView2);
            rowLayout = itemView.findViewById(R.id.mainRowLayout);
        }
    }
}
