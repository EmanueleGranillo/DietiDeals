package com.example.dietideals24;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyApiService {
    @POST("/create_user/")
    Call<ResponseBody> saveUser(@Body UserRegistrationRequest request);
}

