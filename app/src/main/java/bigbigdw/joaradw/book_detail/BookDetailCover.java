package bigbigdw.joaradw.book_detail;

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

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.base.BookBaseActivity;
import bigbigdw.joaradw.book_viewer.BookViewer;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.R;
import bigbigdw.joaradw.model.BookInfo;


public class BookDetailCover extends BookBaseActivity {

    String userStatus = "";
    String userToken = "";
    String bookDetailURL;
    String bookCode = "";
    TextView bookType;
    TextView bookTitleUnder;
    TextView bookWriter;
    TextView bar;
    TextView category;
    TextView bookRead;
    TextView bookRecommend;
    TextView bookFav;
    TextView bookComment;
    TextView bookIntro;
    ImageView bookReadImg;
    ImageView bookRecommedImg;
    ImageView bookFavImg;
    ImageView bookCommentImg;
    ImageView bookCoverHeader;
    AppBarLayout bookAppBar;
    LinearLayout bookLabel;
    LinearLayout loadingLayout;
    LinearLayout bookCoverWrap;
    Button bookDetailHeader2;
    JSONObject book;
    Button btnChapterFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_cover);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        checkToken();
        JOARADW app = (JOARADW) getApplicationContext();

        if(app.getIsLogined()) {
            userToken = app.getToken();
            userStatus = app.getStatus();
        }

        Intent intent = getIntent();
        bookCode = intent.getStringExtra("BookCode");
        app.setBookCode(bookCode);
        bookDetailURL = HELPER.API + API.BOOK_DETAIL_JOA + HELPER.ETC + "&token=" + userToken + "&category=0&book_code=" + bookCode + "&promotion_code=";

        bookCoverHeader = findViewById(R.id.BookCoverHeader);
        bookType = findViewById(R.id.BookType);
        bookWriter = findViewById(R.id.BookWriter);
        bar = findViewById(R.id.Bar);
        category = findViewById(R.id.Category);
        bookRead = findViewById(R.id.BookRead);
        bookRecommend = findViewById(R.id.BookRecommend);
        bookFav = findViewById(R.id.BookFav);
        bookComment = findViewById(R.id.BookComment);
        bookIntro = findViewById(R.id.BookIntro);
        bookAppBar = findViewById(R.id.BookAppBar);
        bookLabel = findViewById(R.id.BookLabel);
        loadingLayout = findViewById(R.id.LoadingLayout);
        bookTitleUnder = findViewById(R.id.BookTitleUnder);
        bookReadImg = findViewById(R.id.BookReadImg);
        bookRecommedImg = findViewById(R.id.BookRecommedImg);
        bookFavImg = findViewById(R.id.BookFavImg);
        bookCommentImg = findViewById(R.id.BookCommentImg);
        bookDetailHeader2 = findViewById(R.id.BookDetailHeader2);
        loadingLayout = findViewById(R.id.LoadingLayout);
        bookCoverWrap = findViewById(R.id.BookCoverWrap);
        btnChapterFirst = findViewById(R.id.BtnChapterFirst);

        setLayout();

    }

    public void setLayout(){
        RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, bookDetailURL, null, response -> {
            try {
                book = response.getJSONObject("book");

                Glide.with(this).load(book.getString("book_img"))
                        .into(bookCoverHeader);

                categoryCheck();
                BookInfo tempBookInfo = BookInfo.getParseData(book);

                bookTitleUnder.setText(tempBookInfo.getTitle());
                bookWriter.setText(tempBookInfo.getWriter());
                category.setText(tempBookInfo.getCategoryKoName());
                bookRead.setText(tempBookInfo.getCntPageRead());
                bookRecommend.setText(tempBookInfo.getCntRecom());
                bookFav.setText(tempBookInfo.getCntFavorite());
                bookComment.setText(book.getString("cnt_total_comment"));
                bookIntro.setText(tempBookInfo.getIntro());

                bar.setVisibility(View.VISIBLE);
                bookReadImg.setVisibility(View.VISIBLE);
                bookRecommedImg.setVisibility(View.VISIBLE);
                bookFavImg.setVisibility(View.VISIBLE);
                bookCommentImg.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.GONE);

                bookCoverWrap.setBackgroundColor(Color.parseColor("#80000000"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Book_Detail", "완료!");
        }, error -> Log.d("Book_Detail", "에러!"));

        queue.add(jsonRequest);

        bookDetailHeader2.setOnClickListener(v -> {
            Intent bookDetailIntent = new Intent(getApplicationContext(), BookDetail.class);
            bookDetailIntent.putExtra("BookCode", String.format("%s", bookCode));
            bookDetailIntent.putExtra("TOKEN", String.format("%s", userToken));
            startActivity(bookDetailIntent);
        });

        btnChapterFirst.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BookViewer.class);
            intent.putExtra("Cid",String.format("%s", "000000"));
            intent.putExtra("TOKEN",String.format("%s", userToken));
            intent.putExtra("BOOKCODE",String.format("%s", bookCode));
            intent.putExtra("SORTNO",String.format("%s", "1"));
            startActivity(intent);
        });
    }

    public void categoryCheck() throws JSONException {

        String isAdult = "is_adult";
        String f = "FALSE";
        String t = "TRUE";

        if (book.getString("is_nobless").equals(t) && book.getString(isAdult).equals(f)) {
            textColor(R.string.NOBLESS, 0xAAa5c500);
        } else if (book.getString("is_premium").equals(t) && book.getString(isAdult).equals(f)) {
            textColor(R.string.PREMIUM, 0xAA4971EF);
        } else if (book.getString("is_finish").equals(t) && book.getString(isAdult).equals(f)) {
            textColor(R.string.FINISH, 0xAAa5c500);
        } else if (book.getString("is_nobless").equals(t) && book.getString(isAdult).equals(t)) {
            textColor(R.string.ADULT_NOBLESS, 0xAAF44336);
        } else if (book.getString("is_premium").equals(t) && book.getString(isAdult).equals(t)) {
            textColor(R.string.ADULT_PREMIUM, 0xAA4971EF);
        } else if (book.getString("is_finish").equals(t) && book.getString(isAdult).equals(t)) {
            textColor(R.string.ADULT_FINISH, 0xAA767676);
        } else {
            bookType.setText(R.string.FREE);
        }

    }

    public void textColor(int text, int color) {
        bookType.setText(text);
        bookLabel.setBackgroundColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bookdetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
