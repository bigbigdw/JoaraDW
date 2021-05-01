package Bigbigdw.JoaraDW.Book_Detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Fragment_New.Book_Pagination;
import Bigbigdw.JoaraDW.R;


public class Book_Detail_Cover extends AppCompatActivity {

    private RequestQueue queue;
    String TOKEN = "";
    String BookDetailURL;
    String BookCode = "";
    TextView BookType, BookTitleUnder, BookWriter, Bar, Category, BookRead, BookRecommend, BookFav, BookComment, BookIntro;
    ImageView BookReadImg, BookRecommedImg, BookFavImg, BookCommentImg;
    ImageView BookCoverHeader;
    AppBarLayout BookAppBar;
    LinearLayout BookLabel, LoadingLayout, FavBtnWrap, BookCoverWrap;
    Button BookDetailHeader1, BookDetailHeader3, BookDetailHeader2;
    String BookTitleText = "";
    JSONObject Book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_cover);
        queue = Volley.newRequestQueue(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        BookCode = intent.getStringExtra("BookCode");
        TOKEN = intent.getStringExtra("TOKEN");
        BookDetailURL = HELPER.API + "/v1/book/detail.joa" + HELPER.ETC + "&token=" + TOKEN + "&category=0&book_code=" + BookCode + "&promotion_code=";

        BookCoverHeader = findViewById(R.id.BookCoverHeader);
        BookType = findViewById(R.id.BookType);
        BookWriter = findViewById(R.id.BookWriter);
        Bar = findViewById(R.id.Bar);
        Category = findViewById(R.id.Category);
        BookRead = findViewById(R.id.BookRead);
        BookRecommend = findViewById(R.id.BookRecommend);
        BookFav = findViewById(R.id.BookFav);
        BookComment = findViewById(R.id.BookComment);
        BookIntro = findViewById(R.id.BookIntro);
        BookAppBar = findViewById(R.id.BookAppBar);
        BookLabel = findViewById(R.id.BookLabel);
        LoadingLayout = findViewById(R.id.LoadingLayout);
        BookTitleUnder = findViewById(R.id.BookTitleUnder);
        BookReadImg = findViewById(R.id.BookReadImg);
        BookRecommedImg = findViewById(R.id.BookRecommedImg);
        BookFavImg = findViewById(R.id.BookFavImg);
        BookCommentImg = findViewById(R.id.BookCommentImg);
        BookDetailHeader1 = findViewById(R.id.BookDetailHeader1);
        BookDetailHeader3 = findViewById(R.id.BookDetailHeader3);
        BookDetailHeader2 = findViewById(R.id.BookDetailHeader2);
        LoadingLayout = findViewById(R.id.LoadingLayout);
        BookCoverWrap = findViewById(R.id.BookCoverWrap);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, BookDetailURL, null, response -> {
            try {
                Book = response.getJSONObject("book");

                Glide.with(this).load(Book.getString("book_img"))
                        .into(BookCoverHeader);

                if (Book.getString("is_nobless").equals("TRUE") && Book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.NOBLESS);
                    BookLabel.setBackgroundColor(0xAAa5c500);
                } else if (Book.getString("is_premium").equals("TRUE") && Book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.PREMIUM);
                    BookLabel.setBackgroundColor(0xAA4971EF);
                } else if (Book.getString("is_finish").equals("TRUE") && Book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.FINISH);
                    BookLabel.setBackgroundColor(0xAAa5c500);
                } else if (Book.getString("is_nobless").equals("TRUE") && Book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_NOBLESS);
                    BookLabel.setBackgroundColor(0xAAF44336);
                } else if (Book.getString("is_premium").equals("TRUE") && Book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_PREMIUM);
                    BookLabel.setBackgroundColor(0xAAF44336);
                } else if (Book.getString("is_finish").equals("TRUE") && Book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_FINISH);
                    BookLabel.setBackgroundColor(0xAAF44336);
                } else {
                    BookType.setText(R.string.FREE);
                }

                BookTitleUnder.setText(Book.getString("subject"));
                BookWriter.setText(Book.getString("writer_name"));
                Category.setText(Book.getString("category_ko_name"));
                BookRead.setText(Book.getString("cnt_page_read"));
                BookRecommend.setText(Book.getString("cnt_recom"));
                BookFav.setText(Book.getString("cnt_favorite"));
                BookComment.setText(Book.getString("cnt_total_comment"));
                BookIntro.setText(Book.getString("intro"));
                BookTitleText = Book.getString("subject");

                Bar.setVisibility(View.VISIBLE);
                BookReadImg.setVisibility(View.VISIBLE);
                BookRecommedImg.setVisibility(View.VISIBLE);
                BookFavImg.setVisibility(View.VISIBLE);
                BookCommentImg.setVisibility(View.VISIBLE);
                LoadingLayout.setVisibility(View.GONE);

                BookCoverWrap.setBackgroundColor(Color.parseColor("#80000000"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Book_Detail", "완료!");
        }, error -> Log.d("Book_Detail", "에러!"));

        queue.add(jsonRequest);

        LoginCheck(queue, "&token=" + TOKEN);

    }

    void LoginCheck(RequestQueue queue, String USERTOKEN) {
        String ResultURL = HELPER.API + "/v1/user/token_check.joa" + HELPER.ETC + USERTOKEN;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {
            Log.d("Main", response.toString());

            try {
                if (response.getString("status").equals("1")) {
                    BookDetailHeader1.setVisibility(View.VISIBLE);
                } else {
                    BookDetailHeader1.setVisibility(View.GONE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Main", "완료!");
        }, error -> Log.d("Main", "에러!"));

        queue.add(jsonRequest);
    }


    public void onClickFavOn(View v) {
        Book_Pagination.FavToggle(queue, BookCode, TOKEN);
        BookDetailHeader1.setVisibility(View.GONE);
        BookDetailHeader3.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(), "'" + BookTitleText + "' 이(가) 선호작에 등록되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public void onClickFavOff(View v) {
        Book_Pagination.FavToggle(queue, BookCode, TOKEN);
        BookDetailHeader1.setVisibility(View.VISIBLE);
        BookDetailHeader3.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), "'" + BookTitleText + "' 이(가) 선호작에서 해제되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public void BookDetailHeaderOff(View v) {
        Intent intent = new Intent(getApplicationContext(), Book_Detail.class);
        intent.putExtra("BookCode",String.format("%s", BookCode));
        intent.putExtra("TOKEN",String.format("%s", TOKEN));
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bookdetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
