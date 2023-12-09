package com.example.dietideals24.connection;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

<<<<<<< Updated upstream
    private static final String BASE_URL = "https://38c1-35-245-59-231.ngrok.io";
=======
    private static final String BASE_URL = "https://bced-35-245-155-78.ngrok.io";
>>>>>>> Stashed changes


    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

