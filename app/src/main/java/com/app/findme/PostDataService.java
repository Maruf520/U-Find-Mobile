package com.app.findme;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostDataService {

    @Multipart
    @POST("/found")
    Call<UploadFound> postFound(@Part("image\"; filename=\"img.png\" ") RequestBody image,
                                @Part("phone") RequestBody phone,
                                @Part("description") RequestBody description,
                                @Part("location") RequestBody location);
}
