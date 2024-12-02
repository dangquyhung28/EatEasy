package com.example.eateasy.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("User_id")
    private int userId;

    @SerializedName("Email")
    private String email;

    @SerializedName("PassWord")
    private String password;

    @SerializedName("SĐT")
    private String sdt;

    @SerializedName("Type")
    private int type;

    @SerializedName("Facebook_ID")
    private String facebook_ID;



    public User(String sdt, String email, String password, int type, String facebook_ID) {
        this.sdt = sdt;
        this.email = email;
        this.password = password;
        this.type = type;
        this.facebook_ID = facebook_ID;
    }

    public User(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
