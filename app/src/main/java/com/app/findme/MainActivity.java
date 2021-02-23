package com.app.findme;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetDataService service = retrofit.create(GetDataService.class);
        Call<List<RetroPhoto>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    mapper.writeValue(new File(getFilesDir()+"respbody.json"), response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                onLoad();
            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try again!", Toast.LENGTH_SHORT).show();
                finishAndRemoveTask();
            }
        });
    }

    private void onLoad() {
        Intent intent = new Intent(this, RecyclerActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}