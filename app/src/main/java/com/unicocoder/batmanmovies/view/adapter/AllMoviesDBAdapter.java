package com.unicocoder.batmanmovies.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.unicocoder.batmanmovies.R;
import com.unicocoder.batmanmovies.databinding.RowAllMoviesDbItemBinding;
import com.unicocoder.batmanmovies.db.model.Batman;
import com.unicocoder.batmanmovies.model.BatmanItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllMoviesDBAdapter extends RecyclerView.Adapter<AllMoviesDBAdapter.ViewHolder> {

    private List<Batman> batmanItems;

    public AllMoviesDBAdapter(List<Batman> batmanItems) {
        this.batmanItems = batmanItems;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowAllMoviesDbItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_all_movies_db_item
                , parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBatmanDB(batmanItems.get(position));

    }

    @Override
    public int getItemCount() {
        return batmanItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowAllMoviesDbItemBinding binding;

        public ViewHolder(RowAllMoviesDbItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
