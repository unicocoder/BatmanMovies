package com.unicocoder.batmanmovies.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.unicocoder.batmanmovies.R;
import com.unicocoder.batmanmovies.databinding.RowAllMoviesItemBinding;
import com.unicocoder.batmanmovies.databinding.RowRatingsMovieItemBinding;
import com.unicocoder.batmanmovies.model.BatmanItem;
import com.unicocoder.batmanmovies.model.RatingsItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RatingsMovieAdapter extends RecyclerView.Adapter<RatingsMovieAdapter.ViewHolder> {

    private List<RatingsItem> ratingsItems;


    public RatingsMovieAdapter(List<RatingsItem> ratingsItems) {
        this.ratingsItems = ratingsItems;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowRatingsMovieItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_ratings_movie_item
                , parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setContent(ratingsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return ratingsItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowRatingsMovieItemBinding binding;

        public ViewHolder(RowRatingsMovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
