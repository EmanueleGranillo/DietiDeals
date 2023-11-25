package com.example.dietideals24;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface MyApiService {
    @POST("/create_user/")
    Call<ResponseBody> saveUser(@Body UserRegistrationRequest request);

    @POST("/check_user_credentials/")
    Call<NumeroResponse> checkCredentials(@Body UserAccessRequest request);
}

