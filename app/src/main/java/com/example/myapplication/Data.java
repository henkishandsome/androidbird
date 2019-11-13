package com.example.myapplication;

import android.app.Application;

public class Data extends Application {
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
