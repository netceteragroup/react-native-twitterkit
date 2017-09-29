package com.netcetera.reactnative.twitterkit;

import android.os.Parcel;
import android.os.Parcelable;

public class FullScreenEvent implements Parcelable {

    byte isFullScreenOn;//new state of full screen, on, off, more for debug

    /* all your getter and setter methods */

    public FullScreenEvent(boolean isFullScreenOn){
        if(isFullScreenOn){
            this.isFullScreenOn = 1;
        } else {
            this.isFullScreenOn = 0;
        }
    }

    public FullScreenEvent(Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FullScreenEvent createFromParcel(Parcel in) {
            return new FullScreenEvent(in);
        }

        public FullScreenEvent[] newArray(int size) {
            return new FullScreenEvent[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeByte(isFullScreenOn);
    }

    private void readFromParcel(Parcel in) {

        isFullScreenOn = in.readByte();
    }


    public boolean isFullScreenOn(){
        return isFullScreenOn > 0;
    }

    public void setIsFullScreenOn(boolean isFullScreenOn){
        if(isFullScreenOn){
            this.isFullScreenOn = 1;
        } else {
            this.isFullScreenOn = 0;
        }
    }
}
