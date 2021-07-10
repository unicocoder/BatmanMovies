package com.unicocoder.batmanmovies.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.unicocoder.batmanmovies.R;
import com.unicocoder.batmanmovies.databinding.RowAllMoviesItemBinding;
import com.unicocoder.batmanmovies.model.BatmanItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllMoviesAdapter extends RecyclerView.Adapter<AllMoviesAdapter.ViewHolder> {

    private List<BatmanItem> batmanItems;

    private OnClickItem onClickItem;

    public interface OnClickItem {
        void onClick(BatmanItem batmanItem, ImageView imageView);
    }

    public AllMoviesAdapter(List<BatmanItem> batmanItems, OnClickItem onClickItem) {
        this.batmanItems = batmanItems;
        this.onClickItem = onClickItem;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowAllMoviesItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_all_movies_item
                , parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBatmanItem(batmanItems.get(position));

        holder.binding.cardView.setOnClickListener(v -> {
            onClickItem.onClick(batmanItems.get(position), holder.binding.imgthumb);
        });
    }

    @Override
    public int getItemCount() {
        return batmanItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowAllMoviesItemBinding binding;

        public ViewHolder(RowAllMoviesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
