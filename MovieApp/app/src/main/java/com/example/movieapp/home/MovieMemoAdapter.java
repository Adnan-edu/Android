package com.example.movieapp.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.home.fragment.MovieViewFragment;
import com.example.movieapp.model.ImageTitle;
import com.example.movieapp.model.MovieMem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieMemoAdapter extends RecyclerView.Adapter<MovieMemoAdapter.MovMemoViewHolder> {
    Context context;
    private List<MovieMem> movieMemList;

    public MovieMemoAdapter(Context context, List<MovieMem> movieMemList) {
        this.context = context;
        this.movieMemList = movieMemList;
    }

    @NonNull
    @Override
    public MovieMemoAdapter.MovMemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movmemo_row, parent, false);
        return new MovieMemoAdapter.MovMemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovMemoViewHolder holder, int position) {
        final String title = movieMemList.get(position).getMemoir().getMoviename();
        final String titleRelYr = movieMemList.get(position).getImageTitle().getMovieTitle();
        final String imageUrl = movieMemList.get(position).getImageTitle().getImageUrl();
        final String watchingDtTm = movieMemList.get(position).getMemoir().getWatchingdatetime();
        final String cinemaSub = movieMemList.get(position).getMemoir().getCinemaid().getCinemaname() + "-" + movieMemList.get(position).getMemoir().getCinemaid().getSuburb();
        final String userCmntTxt = movieMemList.get(position).getMemoir().getComment();
        final String movieInfo = movieMemList.get(position).getImageTitle().getMovieInfo();
        final String publicRating = movieMemList.get(position).getImageTitle().getRating();
        final float userRatingTxt = Float.valueOf(String.valueOf(movieMemList.get(position).getMemoir().getRatingscore()));

        TextView titleView = holder.movTltRel;
        ImageView movMemImgTxtVw = holder.movMemImgView;
        TextView watchingDTmTxt = holder.watchingDtTm;
        TextView userComntTxVw = holder.userComntTxt;
        RatingBar ratingMovMemVw = holder.ratingMovMem;
        TextView cinSubTextView = holder.cinSubTxtVw;

        Picasso.get().load(imageUrl).
                into(movMemImgTxtVw);

        titleView.setText(title);
        watchingDTmTxt.setText(watchingDtTm);
        userComntTxVw.setText(userCmntTxt);
        ratingMovMemVw.setRating(userRatingTxt);
        cinSubTextView.setText(cinemaSub);

        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((HomeActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MovieViewFragment movieViewFragment = new MovieViewFragment();
                ImageTitle imageTitle = new ImageTitle();
                imageTitle.setMovieTitle(titleRelYr);
                imageTitle.setImageUrl(imageUrl);
                imageTitle.setMovieInfo(movieInfo);
                imageTitle.setRating(publicRating);
                Bundle b = new Bundle();
                b.putSerializable("MovieView", imageTitle);
                b.putBoolean("EnableWatchList", true);
                movieViewFragment.setArguments(b);
                fragmentTransaction.replace(R.id.content_frame, movieViewFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieMemList.size();
    }

    public class MovMemoViewHolder extends RecyclerView.ViewHolder {


        ImageView movMemImgView;
        TextView movTltRel;
        TextView watchingDtTm;
        TextView userComntTxt;
        TextView cinSubTxtVw;
        RatingBar ratingMovMem;

        ConstraintLayout rowLayout;

        public MovMemoViewHolder(@NonNull View itemView) {
            super(itemView);
            movMemImgView = itemView.findViewById(R.id.movMemImgViewId);
            movTltRel = itemView.findViewById(R.id.movTltRelId);
            watchingDtTm = itemView.findViewById(R.id.watchingDtTmId);
            userComntTxt = itemView.findViewById(R.id.userComntTxtId);
            cinSubTxtVw = itemView.findViewById(R.id.cinSubId);
            ratingMovMem = itemView.findViewById(R.id.ratingMovMemId);
            rowLayout = itemView.findViewById(R.id.movmemoRowLayout);
        }
    }
}
