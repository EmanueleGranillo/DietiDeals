package com.example.dietideals24.connection;

import android.app.Notification;
import android.provider.ContactsContract;

import com.example.dietideals24.models.Asta;
import com.example.dietideals24.models.Notifica;
import com.example.dietideals24.models.Profilo;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyApiService {
    @POST("/create_user/")
    Call<ResponseBody> saveUser(@Body UserRegistrationRequest request);

    @GET("/get_user/{nickname}")
    Call<Profilo> getUser(@Path("nickname") String nickname);

    @POST("/edit_user/")
    Call<Void> editUser(@Body UserModifiedRequest request);

    @POST("/check_user_credentials/")
    Call<NumeroResponse> checkCredentials(@Body UserAccessRequest request);

    @GET("/get_aste/")
    Call<ArrayList<Asta>> getAste();

    @POST("/get_aste_per_categoria/")
    Call<ArrayList<Asta>> getAstePerCategoria(@Body CategoriaRequest request);

    @POST("/get_aste_per_venditore/")
    Call<ArrayList<Asta>> getAstePerVenditore(@Body NicknameRequest request);

    @POST("/check_notifiche/")
    Call<NumeroResponse> checkNotifications(@Body NicknameRequest request);

    @POST("/set_notifiche_a_lette/")
    Call<Void> setNotificheALette(@Body NicknameRequest nickname);

    @POST("/get_notifiche/")
    Call<ArrayList<Notifica>> getNotifiche(@Body NicknameRequest request);

    @POST("/create_asta_tf/")
    Call<ResponseBody> saveUser(@Body UserRegistrationRequest request);

}

