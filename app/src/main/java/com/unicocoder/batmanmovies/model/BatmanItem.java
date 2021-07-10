package com.unicocoder.batmanmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import com.unicocoder.batmanmovies.R;

public class BatmanItem implements Parcelable {

	@SerializedName("Type")
	private String type;

	@SerializedName("Year")
	private String year;

	@SerializedName("imdbID")
	private String imdbID;

	@SerializedName("Poster")
	private String poster;

	@SerializedName("Title")
	private String title;

	protected BatmanItem(Parcel in) {
		type = in.readString();
		year = in.readString();
		imdbID = in.readString();
		poster = in.readString();
		title = in.readString();
	}

	public static final Creator<BatmanItem> CREATOR = new Creator<BatmanItem>() {
		@Override
		public BatmanItem createFromParcel(Parcel in) {
			return new BatmanItem(in);
		}

		@Override
		public BatmanItem[] newArray(int size) {
			return new BatmanItem[size];
		}
	};

	public String getType(){
		return type;
	}

	public String getYear(){
		return year;
	}

	public String getImdbID(){
		return imdbID;
	}

	public String getPoster(){
		return poster;
	}

	public String getTitle(){
		return title;
	}

	@BindingAdapter({"android:showImage"})
	public static void ShowImage(ImageView imageView, String urlImage) {
		Glide.with(imageView.getContext())
				.load(urlImage)
				.placeholder(R.drawable.logo)
				.into(imageView);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(type);
		dest.writeString(year);
		dest.writeString(imdbID);
		dest.writeString(poster);
		dest.writeString(title);
	}
}