package com.example.googlebooksapp.network;

import com.example.googlebooksapp.model.BookResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("volumes")
    Call<BookResponse> searchBooks(@Query("q") String query);
}
