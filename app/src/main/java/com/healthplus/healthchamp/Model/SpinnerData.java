package com.healthplus.healthchamp.Model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@SuppressLint("ParcelCreator")
public class SpinnerData implements Serializable, Parcelable {
   public String state;
   public String id;

    public SpinnerData() {
    }
    public SpinnerData(String state, String id) {
        this.state = state;
        this.id=id;
    }
    public void setName(String state)
    {
        this.state = state;
    }
    public String setId(String id)
    {
        this.id = id;
        return id;
    }
    public String getName()
    {
        return state;
    }

    public String getId()
    {
        return id;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.id,this.state});
    }

}
