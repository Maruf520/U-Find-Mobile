package com.app.findme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    File selectedImage;
    ImageView preview;
    Button btnImage, btnSend;
    TextView phoneText, descText, locText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);
        getSupportActionBar().hide();

        preview = findViewById(R.id.imageView2);
        btnImage = findViewById(R.id.button4);
        btnSend = findViewById(R.id.button6);
        btnImage.setOnClickListener(v -> onPickPhoto());
        btnSend.setOnClickListener(v -> onSend());

        phoneText = findViewById(R.id.editTextPhone);
        descText = findViewById(R.id.descText);
        locText = findViewById(R.id.editTextTextPostalAddress);
    }

    private void onPickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1046);
        }
    }

    private void onSend() {
        if (selectedImage == null) {
            btnImage.setError("Please Select an Image!");
            return;
        }

        if (phoneText.getText().toString().length() != 11) {
            phoneText.setError("Please Enter a valid Phone Number!");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostDataService service = retrofit.create(PostDataService.class);

        RequestBody img = RequestBody.create(MediaType.parse("image/*"), selectedImage);
        RequestBody phn = RequestBody.create(MediaType.parse("text/plain"), phoneText.getText().toString());
        RequestBody dsc = RequestBody.create(MediaType.parse("text/plain"), descText.getText().toString());
        RequestBody loc = RequestBody.create(MediaType.parse("text/plain"), locText.getText().toString());

        Call<UploadFound> call = service.postFound(img, phn, dsc, loc);

        call.enqueue(new Callback<UploadFound>() {
            @Override
            public void onResponse(Call<UploadFound> call, Response<UploadFound> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UploadActivity.this, "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UploadFound> call, Throwable t) {
                Toast.makeText(UploadActivity.this, "Upload Failed! Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            if (Build.VERSION.SDK_INT > 27){
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((data != null) && requestCode == 1046) {
            Uri photoUri = data.getData();

            Bitmap image = loadFromUri(photoUri);
            selectedImage = new File(photoUri.getPath());

            preview.setImageBitmap(image);
        }
    }
}
