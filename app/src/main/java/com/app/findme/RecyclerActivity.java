package com.app.findme;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectMapper mapper = new ObjectMapper();
        List<RetroPhoto> respBody = null;
        try {
            respBody = mapper.readValue(new File(getFilesDir()+"respbody.json"), new TypeReference<List<RetroPhoto>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.recycler_view);
        getSupportActionBar().show();
        generateDataList(respBody);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            onUpload();
        });
    }

    private void generateDataList(List<RetroPhoto> photoList) {
        RecyclerView recyclerView = findViewById(R.id.customRecyclerView);
        CustomAdapter adapter = new CustomAdapter(this, photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void onUpload() {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }
}
