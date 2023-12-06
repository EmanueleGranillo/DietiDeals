package com.example.dietideals24.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.dietideals24.connection.CategoriaRequest;
import com.example.dietideals24.connection.MyApiService;
import com.example.dietideals24.connection.NumeroResponse;
import com.example.dietideals24.connection.RetrofitClient;
import com.example.dietideals24.connection.SearchRequest;
import com.example.dietideals24.models.Asta;
import com.example.dietideals24.customs.CustomBaseAdapterProducts;
import com.example.dietideals24.customs.CustomListViewProductEnglish;
import com.example.dietideals24.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageCompratoreActivity extends AppCompatActivity {

    private MyApiService apiService;
    ArrayList<Asta> aste = new ArrayList<Asta>();
    ListView listView;
    CustomBaseAdapterProducts customBaseAdapterProducts;
    ImageView pallinoImg;
    Button tutteBtn;
    Button elettronicaBtn;
    Button motoriBtn;
    Button animaliBtn;
    Button modaBtn;
    Button intrattenimentoBtn;
    Button immobiliBtn;
    Button sportBtn;
    Button arredamentoBtn;
    String nickname;
    String tipo;
    SearchView searchView;
    String checkActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_compratore);

        apiService = RetrofitClient.getInstance().create(MyApiService.class);

        pallinoImg = findViewById(R.id.pallinoButton);
        tutteBtn = findViewById(R.id.buttonTutteLeCategorie);
        elettronicaBtn = findViewById(R.id.buttonElettronica);
        motoriBtn = findViewById(R.id.buttonMotori);
        animaliBtn = findViewById(R.id.buttonAnimali);
        modaBtn = findViewById(R.id.buttonModa);
        intrattenimentoBtn = findViewById(R.id.buttonIntrattenimento);
        immobiliBtn = findViewById(R.id.buttonImmobili);
        sportBtn = findViewById(R.id.buttonSport);
        arredamentoBtn = findViewById(R.id.buttonArredamento);
        ImageButton profiloBtn = findViewById(R.id.profiloButtonHomeCompratore);
        ImageButton notificheBtn = findViewById(R.id.notificheButtonHomeCompratore);
        searchView = findViewById(R.id.cercaAstaSearchView);

        checkActivity = "mine";
        listView = (ListView) findViewById(R.id.customListViewProducts);
        nickname = getIntent().getStringExtra("nickname");
        tipo = getIntent().getStringExtra("tipo");
        pallinoImg.setVisibility(View.INVISIBLE);


        //controllaNotifiche();

        riempiLista();
        riempiListaRibasso();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Logic to handle item click
                if (aste.get(position).getTipologia().equals("asta inglese")) {
                    Intent goToAstaIngleseActivity = new Intent(HomepageCompratoreActivity.this, AstaIngleseActivity.class);
                    goToAstaIngleseActivity.putExtra("nickname", nickname);
                    goToAstaIngleseActivity.putExtra("tipo", tipo);
                    goToAstaIngleseActivity.putExtra("asta", aste.get(position));
                    goToAstaIngleseActivity.putExtra("id", aste.get(position).getId());
                    startActivity(goToAstaIngleseActivity);
                } else if (aste.get(position).getTipologia().equals("asta al ribasso")) {
                    Intent goToAstaRibassoActivity = new Intent(HomepageCompratoreActivity.this, AstaRibassoActivity.class);
                    goToAstaRibassoActivity.putExtra("nickname", nickname);
                    goToAstaRibassoActivity.putExtra("tipo", tipo);
                    goToAstaRibassoActivity.putExtra("id", aste.get(position).getId());
                    goToAstaRibassoActivity.putExtra("asta", aste.get(position));
                    startActivity(goToAstaRibassoActivity);
                } else if (aste.get(position).getTipologia().equals("asta a tempo fisso")) {
                    Intent goToAstaTempoFissoActivity = new Intent(HomepageCompratoreActivity.this, AstaTempoFissoActivity.class);
                    goToAstaTempoFissoActivity.putExtra("nickname", nickname);
                    goToAstaTempoFissoActivity.putExtra("tipo", tipo);
                    goToAstaTempoFissoActivity.putExtra("asta", aste.get(position));
                    startActivity(goToAstaTempoFissoActivity);
                }
            }
        });

        profiloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfilo = new Intent(HomepageCompratoreActivity.this, ProfiloActivity.class);
                goToProfilo.putExtra("nickname", nickname);
                goToProfilo.putExtra("tipo", tipo);
                goToProfilo.putExtra("checkActivity", checkActivity);
                startActivity(goToProfilo);
            }
        });

        notificheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNotifiche = new Intent(HomepageCompratoreActivity.this, NotificationsActivity.class);
                goToNotifiche.putExtra("nickname", nickname);
                goToNotifiche.putExtra("tipo", tipo);
                startActivity(goToNotifiche);
            }
        });

        tutteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tutteBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {

                } else {
                    setWhite();
                    tutteBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                    tutteBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    riempiLista();
                    //riempiListaRibasso();
                }
            }
        });

        elettronicaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(elettronicaBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {

                } else {
                    setWhite();
                    elettronicaBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                    elettronicaBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    riempiListaPerCategoria("elettronica");
                }
            }
        });

        motoriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motoriBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {

                } else {
                    setWhite();
                    motoriBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                    motoriBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    riempiListaPerCategoria("motori");
                }
            }
        });

        animaliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animaliBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {

                } else {
                    setWhite();
                    animaliBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                    animaliBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    riempiListaPerCategoria("animali");
                }
            }
        });

        modaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modaBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {

                } else {
                    setWhite();
                    modaBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                    modaBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    riempiListaPerCategoria("moda");
                }
            }
        });

        intrattenimentoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intrattenimentoBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {

                } else {
                    setWhite();
                    intrattenimentoBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                    intrattenimentoBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    riempiListaPerCategoria("intrattenimento");
                }
            }
        });

        immobiliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(immobiliBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {

                } else {
                    setWhite();
                    immobiliBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                    immobiliBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    riempiListaPerCategoria("immobili");
                }
            }
        });

        sportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sportBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {

                } else {
                    setWhite();
                    sportBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                    sportBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    riempiListaPerCategoria("sport");
                }
            }
        });

        arredamentoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arredamentoBtn.getBackgroundTintList().getDefaultColor() == Color.parseColor("#00CC66")) {

                } else {
                    setWhite();
                    arredamentoBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00CC66")));
                    arredamentoBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    riempiListaPerCategoria("arredamento");
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return false;
            }
        });

    }

    /*private void controllaNotifiche() {
        Call<NumeroResponse> call = apiService.checkNotifications(nickname);
        call.enqueue(new Callback<NumeroResponse>() {
            @Override
            public void onResponse(Call<NumeroResponse> call, Response<NumeroResponse> response) {
                if (response.isSuccessful()) {
                    NumeroResponse num = response.body();
                    if (num.getNumero() == 0) {
                        pallinoImg.setVisibility(View.INVISIBLE);
                    } else {
                        pallinoImg.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<NumeroResponse> call, Throwable t) {
                Toast.makeText(HomepageCompratoreActivity.this, "Connessione fallita per check notifiche", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void riempiLista() {
        Call<ArrayList<Asta>> call = apiService.getAste();
        call.enqueue(new Callback<ArrayList<Asta>>() {
            @Override
            public void onResponse(Call<ArrayList<Asta>> call, Response<ArrayList<Asta>> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    aste.clear();
                    aste.addAll(response.body());

                    // Aggiorna la ListView con i nuovi dati
                    //customBaseAdapterProducts = new CustomBaseAdapterProducts(getApplicationContext(), aste);
                    //listView.setAdapter(customBaseAdapterProducts);
                    //CustomListViewProductEnglish.setListViewHeightBasedOnChildren(listView);


                    //Toast.makeText(HomepageCompratoreActivity.this, "Ci siamo quasi: "+ aste.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomepageCompratoreActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Asta>> call, Throwable t) {
                Toast.makeText(HomepageCompratoreActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void riempiListaRibasso() {
        Call<ArrayList<Asta>> call = apiService.getAsteRibasso();
        call.enqueue(new Callback<ArrayList<Asta>>() {
            @Override
            public void onResponse(Call<ArrayList<Asta>> call, Response<ArrayList<Asta>> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    aste.addAll(response.body());

                    // Aggiorna la ListView con i nuovi dati
                    customBaseAdapterProducts = new CustomBaseAdapterProducts(getApplicationContext(), aste);
                    listView.setAdapter(customBaseAdapterProducts);
                    CustomListViewProductEnglish.setListViewHeightBasedOnChildren(listView);


                    //Toast.makeText(HomepageCompratoreActivity.this, "Ci siamo quasi: "+ aste.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomepageCompratoreActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Asta>> call, Throwable t) {
                Toast.makeText(HomepageCompratoreActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void riempiListaPerCategoria(String categoria) {
        CategoriaRequest categoriaRequest = new CategoriaRequest(categoria);
        Call<ArrayList<Asta>> call = apiService.getAstePerCategoria(categoriaRequest);
        call.enqueue(new Callback<ArrayList<Asta>>() {
            @Override
            public void onResponse(Call<ArrayList<Asta>> call, Response<ArrayList<Asta>> response) {
                // Gestisci la risposta del server
                if (response.isSuccessful()) {
                    aste.clear();
                    aste.addAll(response.body());

                    // Aggiorna la ListView con i nuovi dati
                    customBaseAdapterProducts.notifyDataSetChanged();

                    //Toast.makeText(HomepageCompratoreActivity.this, "Ci siamo quasi: "+ aste.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomepageCompratoreActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Asta>> call, Throwable t) {
                Toast.makeText(HomepageCompratoreActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performSearch(String query) {
        SearchRequest searchRequest = new SearchRequest(query);
        Call<ArrayList<Asta>> call = apiService.getAstePerRicerca(searchRequest);
        call.enqueue(new Callback<ArrayList<Asta>>() {
            @Override
            public void onResponse(Call<ArrayList<Asta>> call, Response<ArrayList<Asta>> response) {
                if (response.isSuccessful()) {
                    aste.clear();
                    aste.addAll(response.body());
                    customBaseAdapterProducts.notifyDataSetChanged();
                } else {
                    Toast.makeText(HomepageCompratoreActivity.this, "Richiesta fallita", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Asta>> call, Throwable t) {
                Toast.makeText(HomepageCompratoreActivity.this, "Connessione fallita", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setWhite() {
        tutteBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        tutteBtn.setTextColor(Color.parseColor("#000000"));
        elettronicaBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        elettronicaBtn.setTextColor(Color.parseColor("#000000"));
        motoriBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        motoriBtn.setTextColor(Color.parseColor("#000000"));
        animaliBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        animaliBtn.setTextColor(Color.parseColor("#000000"));
        modaBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        modaBtn.setTextColor(Color.parseColor("#000000"));
        intrattenimentoBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        intrattenimentoBtn.setTextColor(Color.parseColor("#000000"));
        immobiliBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        immobiliBtn.setTextColor(Color.parseColor("#000000"));
        sportBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        sportBtn.setTextColor(Color.parseColor("#000000"));
        arredamentoBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        arredamentoBtn.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}