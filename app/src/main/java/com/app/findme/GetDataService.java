package com.app.findme;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface GetDataService {

    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();
}
