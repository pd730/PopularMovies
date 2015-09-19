package com.myapp.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Paul on 9/9/2015.
 */
public class MovieInfo implements Parcelable {
    String title;
    String imagePath;
    String overview;
    String relDate;
    Long popularity;
    Double voteAvg;
    Integer voteCnt;

    public MovieInfo()
    {

    }

    public MovieInfo(String mTitle, String mImage, String mOverview, String mRelDate, Long mPop, Double mVoteAvg, Integer mVoteCnt)
    {
        this.title = mTitle;
        this.imagePath = mImage;
        this.overview = mOverview;
        this.relDate = mRelDate;
        this.popularity = mPop;
        this.voteAvg = mVoteAvg;
        this.voteCnt = mVoteCnt;
    }

    private MovieInfo(Parcel in)
    {
        title = in.readString();
        imagePath = in.readString();
        overview = in.readString();
        relDate = in.readString();
        popularity = in.readLong();
        voteAvg = in.readDouble();
        voteCnt = in.readInt();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(title);
        parcel.writeString(imagePath);
        parcel.writeString(overview);
        parcel.writeString(relDate);
        parcel.writeLong(popularity);
        parcel.writeDouble(voteAvg);
        parcel.writeInt(voteCnt);
    }

    public final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>()
    {
        @Override
        public MovieInfo createFromParcel(Parcel parcel)
        {
            return new MovieInfo(parcel);
        }

        @Override
        public MovieInfo[] newArray(int i) {
            return new MovieInfo[i];
        }
    };
}
