package com.example.dietideals24.connection;
import com.google.gson.annotations.SerializedName;

public class VincitoreRibassoRequest {

    @SerializedName("vincitore")
    private String vincitore;

    @SerializedName("id")
    private int id;


    public VincitoreRibassoRequest(String vincitore, int id) {
        this.vincitore = vincitore;
        this.id = id;
    }


}