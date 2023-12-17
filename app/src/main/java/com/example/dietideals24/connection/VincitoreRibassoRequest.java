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

    public String getVincitore() {
        return vincitore;
    }

    public void setVincitore(String vincitore) {
        this.vincitore = vincitore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String validate() {

        if(vincitore.isEmpty()){
            return "Non c'è un vincitore.";
        } else if (vincitore.length() > 30){
            return "Nome non valido.";
        }
        if (id < 0) {
            return "ID non valido.";
        }
        return "I valori sono corretti.";
    }

}