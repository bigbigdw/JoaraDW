package Bigbigdw.JoaraDW.Book_Detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.R;

public class Book_Detail extends AppCompatActivity {

    private RequestQueue queue;
    String TOKEN = "";
    String BookDetailURL;
    TextView BookTitle, BookType, BookTitleUnder, BookWriter;
    ImageView BookCoverHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetail);
        queue = Volley.newRequestQueue(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String BookCode = intent.getStringExtra("BookCode");

        if(intent.getStringExtra("TOKEN") == null){
            TOKEN = intent.getStringExtra("TOKEN");
        }

        BookDetailURL = HELPER.API + "/v1/book/detail.joa" + HELPER.ETC + TOKEN + "&category=0&book_code=" + BookCode + "&promotion_code=";

        Log.d("BookDetailURL", BookDetailURL);

        BookTitle = findViewById(R.id.BookTitle);
        BookCoverHeader = findViewById(R.id.BookCoverHeader);
        BookType = findViewById(R.id.BookType);
        BookTitleUnder = findViewById(R.id.BookTitleUnder);
        BookWriter = findViewById(R.id.BookWriter);

//        TODO:성인인증 작업 필요

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, BookDetailURL, null, response -> {
            System.out.println(response);
            try {
                JSONObject book = response.getJSONObject("book");

                BookTitle.setText(book.getString("subject"));
                Glide.with(this).load(book.getString("book_img"))
                        .into(BookCoverHeader);

                if (book.getString("is_nobless").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.NOBLESS);
                    BookType.setTextColor(0xAAa5c500);
                } else if (book.getString("is_premium").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.PREMIUM);
                    BookType.setTextColor(0xAA4971EF);
                } else if (book.getString("is_finish").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.FINISH);
                    BookType.setTextColor(0xAA767676);
                } else if (book.getString("is_nobless").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_NOBLESS);
                    BookType.setTextColor(0xAAF44336);
                } else if (book.getString("is_premium").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_PREMIUM);
                    BookType.setTextColor(0xAA4971EF);
                } else if (book.getString("is_finish").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_FINISH);
                    BookType.setTextColor(0xAA767676);
                }

                BookTitleUnder.setText(book.getString("subject"));
                BookWriter.setText(book.getString("writer_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            Log.d("Book_Detail", "완료!");
        }, error -> Log.d("Book_Detail", "에러!"));

        queue.add(jsonRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bookdetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
