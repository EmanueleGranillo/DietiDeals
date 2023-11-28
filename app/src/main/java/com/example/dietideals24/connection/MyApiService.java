package com.example.dietideals24.connection;

import com.example.dietideals24.models.Asta;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface MyApiService {
    @POST("/create_user/")
    Call<ResponseBody> saveUser(@Body UserRegistrationRequest request);

    @POST("/check_user_credentials/")
    Call<NumeroResponse> checkCredentials(@Body UserAccessRequest request);

    @GET("/get_aste/")
    Call<ArrayList<Asta>> getAste();

    @POST("/get_aste_per_categoria/")
    Call<ArrayList<Asta>> getAstePerCategoria(@Body CategoriaRequest request);
}

