package com.reza.seafoodcatalogue;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    private String id;
    private String title;
    private String image;
    private String area;
    private String instruction;
    private String video;

    public Data() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getArea() {
        return area;
    }

    public String setArea(String area) {
        return area;
    }

    public String getInstruction() {
        return instruction;
    }

    public String setInstruction(String instruction) {
        return instruction;
    }

    public String getVideo() {
        return video;
    }

    public String setVideo(String video) {
        return video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(area);
        dest.writeString(instruction);
        dest.writeString(video);
    }

    protected Data(Parcel in) {
        id = in.readString();
        title = in.readString();
        image = in.readString();
        area = in.readString();
        instruction = in.readString();
        video = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
