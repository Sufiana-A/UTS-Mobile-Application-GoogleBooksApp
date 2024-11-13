package com.example.googlebooksapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.googlebooksapp.adapter.BookAdapter;
import com.example.googlebooksapp.model.Book;
import com.example.googlebooksapp.model.BookResponse;
import com.example.googlebooksapp.network.ApiClient;
import com.example.googlebooksapp.network.ApiService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchBooks("android development");
    }

    private void fetchBooks(String query) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<BookResponse> call = apiService.searchBooks(query);

        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BookResponse.Item> items = response.body().getItems();
                    List<Book> books = new ArrayList<>();

                    for (BookResponse.Item item : items) {
                        String title = item.getVolumeInfo().getTitle();
                        String author = item.getVolumeInfo().getAuthors() != null && !item.getVolumeInfo().getAuthors().isEmpty()
                                ? item.getVolumeInfo().getAuthors().get(0) : "Unknown Author";
                        books.add(new Book(title, author));
                    }

                    bookAdapter = new BookAdapter(books);
                    recyclerView.setAdapter(bookAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
