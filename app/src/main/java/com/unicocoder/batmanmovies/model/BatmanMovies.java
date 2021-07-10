package com.unicocoder.batmanmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import com.unicocoder.batmanmovies.R;

public class BatmanMovies implements Parcelable {

    @SerializedName("Response")
    private String response;

    @SerializedName("totalResults")
    private String totalResults;

    @SerializedName("Search")
    private List<BatmanItem> search;

    protected BatmanMovies(Parcel in) {
        response = in.readString();
        totalResults = in.readString();
    }

    public static final Creator<BatmanMovies> CREATOR = new Creator<BatmanMovies>() {
        @Override
        public BatmanMovies createFromParcel(Parcel in) {
            return new BatmanMovies(in);
        }

        @Override
        public BatmanMovies[] newArray(int size) {
            return new BatmanMovies[size];
        }
    };

    public String getResponse() {
        return response;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public List<BatmanItem> getSearch() {
        return search;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(response);
        dest.writeString(totalResults);
    }

}