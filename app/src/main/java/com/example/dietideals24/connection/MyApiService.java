package com.example.dietideals24.connection;

import com.example.dietideals24.models.Asta;
import com.example.dietideals24.models.Notifica;
import com.example.dietideals24.models.Profilo;
import com.example.dietideals24.models.RegisterCheck;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyApiService {
    @POST("/create_user/")
    Call<RegisterCheck> saveUser(@Body UserRegistrationRequest request);

    @GET("/get_user/{nickname}")
    Call<Profilo> getUser(@Path("nickname") String nickname);

    @POST("/edit_user/")
    Call<Void> editUser(@Body UserModifiedRequest request);

    @POST("/check_user_credentials/")
    Call<NumeroResponse> checkCredentials(@Body UserAccessRequest request);

    @GET("/get_asta/{idasta}")
    Call<Asta> getAsta(@Path("idasta") int idasta);

    @GET("/get_aste/")
    Call<ArrayList<Asta>> getAste();

    @GET("/get_aste_ribasso/")
    Call<ArrayList<Asta>> getAsteRibasso();

    @POST("/get_aste_per_categoria/")
    Call<ArrayList<Asta>> getAstePerCategoria(@Body CategoriaRequest request);

    @POST("/get_aste_per_venditore/{nickname}")
    Call<ArrayList<Asta>> getAstePerVenditore(@Path("nickname") String nickname);

    @POST("/get_aste_per_venditore_attive/{nickname}")
    Call<ArrayList<Asta>> getAstePerVenditoreAttive(@Path("nickname") String nickname);

    @POST("/get_aste_per_venditore_concluse/{nickname}")
    Call<ArrayList<Asta>> getAstePerVenditoreConcluse(@Path("nickname") String nickname);

    @POST("/check_notifiche/{nickname}")
    Call<NumeroResponse> checkNotifications(@Path("nickname") String nickname);

    @POST("/set_notifiche_a_lette/{nickname}")
    Call<Void> setNotificheALette(@Path("nickname") String nickname);

    @POST("/get_notifiche/{nickname}")
    Call<ArrayList<Notifica>> getNotifiche(@Path("nickname") String nickname);

    @POST("/create_asta_tf/")
    Call<ResponseBody> createAstatf(@Body CreateAstaTFRequest request);

    @POST("/create_asta_ing/")
    Call<ResponseBody> createAstaIng(@Body CreateAstaIngRequest request);

    @POST("/create_asta_ribasso/")
    Call<ResponseBody> createAstaRibasso(@Body CreateAstaRibassoRequest request);

    @POST("/get_aste_per_ricerca/")
    Call<ArrayList<Asta>> getAstePerRicerca(@Body SearchRequest request);

    @POST("/offerta_inglese/")
    Call<NumeroResponse> offertaInglese(@Body OffertaIngleseRequest request);
    @POST("/offerta_ribasso/")
    Call<Void> updateRibasso(@Body OffertaRibassoRequest request);

}

