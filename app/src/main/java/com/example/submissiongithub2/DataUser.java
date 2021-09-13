package com.example.submissiongithub2;

import android.os.Parcel;
import android.os.Parcelable;

public class DataUser implements Parcelable{
    private String photoUser;
    private String nameUser;
    private String userName;
    private String location;
    private String company;
    private String repository;
    private String follower;
    private String following;

    protected DataUser(Parcel in) {
        photoUser = in.readString();
        nameUser = in.readString();
        userName = in.readString();
        location = in.readString();
        company = in.readString();
        repository = in.readString();
        follower = in.readString();
        following = in.readString();
    }

    public DataUser() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(photoUser);
        dest.writeString(nameUser);
        dest.writeString(userName);
        dest.writeString(location);
        dest.writeString(company);
        dest.writeString(repository);
        dest.writeString(follower);
        dest.writeString(following);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataUser> CREATOR = new Creator<DataUser>() {
        @Override
        public DataUser createFromParcel(Parcel in) {
            return new DataUser(in);
        }

        @Override
        public DataUser[] newArray(int size) {
            return new DataUser[size];
        }
    };

    public String getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }
}
