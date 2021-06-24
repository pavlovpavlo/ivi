package org.vse.zaimy.online.pojo.actual_backend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActualBackendResponse {
    @SerializedName("actualbackend")
    @Expose
    private String actualbackend;

    public String getActualbackend() {
        return actualbackend;
    }

    public void setActualbackend(String actualbackend) {
        this.actualbackend = actualbackend;
    }
}
