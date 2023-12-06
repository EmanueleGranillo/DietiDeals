package com.example.dietideals24.connection;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

<<<<<<< Updated upstream
    private static final String BASE_URL = "https://3a48-34-82-124-100.ngrok.io";
=======
    private static final String BASE_URL = "https://429a-34-27-251-24.ngrok.io";
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

