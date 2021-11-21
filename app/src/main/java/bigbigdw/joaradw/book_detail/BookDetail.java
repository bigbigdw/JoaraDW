package bigbigdw.joaradw.book_detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import bigbigdw.joaradw.base.BookBaseActivity;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.R;
import bigbigdw.joaradw.model.BookInfo;

public class BookDetail extends BookBaseActivity {

    String userToken = "";
    String userStatus = "";
    String bookDetailURL;
    String bookCode = "";
    String bookTitleText = "";
    TextView bookTitle;
    TextView bookTypeBody;
    TextView categoryBody;
    TextView bookTitleBody;
    TextView bookWriterBody;
    TextView bookReadBody;
    TextView bookRecommendBody;
    TextView bookFavBody;
    TextView bookCommentBody;
    TextView barBody;
    TextView bookDetailIntro;
    AppBarLayout bookAppBar;
    ImageView bookCover;
    ImageView btnFavOn;
    ImageView btnFavOff;
    JOARADW app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        bookCode = intent.getStringExtra("BookCode");
        userStatus = intent.getStringExtra("TOKEN");
        bookDetailURL = HELPER.API + API.BOOK_DETAIL_JOA + HELPER.ETC + "&token=" + userStatus + "&category=0&book_code=" + bookCode + "&promotion_code=";

        bookAppBar = findViewById(R.id.BookAppBar);
        bookTypeBody = findViewById(R.id.BookTypeBody);
        categoryBody = findViewById(R.id.CategoryBody);
        bookTitleBody = findViewById(R.id.BookTitleBody);
        bookWriterBody = findViewById(R.id.BookWriterBody);
        bookReadBody = findViewById(R.id.BookReadBody);
        bookRecommendBody = findViewById(R.id.BookRecommendBody);
        bookFavBody = findViewById(R.id.BookFavBody);
        bookCommentBody = findViewById(R.id.BookCommentBody);
        barBody = findViewById(R.id.BarBody);
        bookCover = findViewById(R.id.BookCover);
        bookDetailIntro = findViewById(R.id.BookDetailIntro);
        bookTitle = findViewById(R.id.BookTitle);
        btnFavOn = findViewById(R.id.BtnFavOn);
        btnFavOff = findViewById(R.id.BtnFavOff);

        ViewPager viewPager = findViewById(R.id.view_pager);

        setupViewPager(viewPager);

        checkToken();
        app = (JOARADW) getApplicationContext();
        if(app.getIsLogined()) {
            userToken = app.getToken();
            userStatus = app.getStatus();
        }
        app.setBookCode(bookCode);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setLayout();
    }


    public void setLayout(){
        RequestQueue queue = Volley.newRequestQueue(this);

        if (userStatus.equals("1")) {
            btnFavOn.setVisibility(View.VISIBLE);
            btnFavOff.setVisibility(View.VISIBLE);
        } else {
            btnFavOn.setVisibility(View.GONE);
            btnFavOff.setVisibility(View.GONE);
        }

       JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, bookDetailURL, null, response -> {
            try {
                JSONObject book = response.getJSONObject("book");

                Glide.with(this).load(book.getString("book_img")).fitCenter()
                        .into(bookCover);

                BookInfo tempBookInfo = BookInfo.getParseData(book);

                categoryCheck(book);

                app.setBookTitle(tempBookInfo.getTitle());
                app.setWriterName(tempBookInfo.getWriter());
                app.setWriterID(tempBookInfo.getWriterID());

                bookTitleBody.setText(tempBookInfo.getTitle());
                bookWriterBody.setText(tempBookInfo.getWriter());
                categoryBody.setText(tempBookInfo.getCategoryKoName());
                bookReadBody.setText(tempBookInfo.getCntPageRead());
                bookRecommendBody.setText(tempBookInfo.getCntRecom());
                bookFavBody.setText(tempBookInfo.getCntFavorite());
                bookCommentBody.setText(book.getString("cnt_total_comment"));
                bookDetailIntro.setText(tempBookInfo.getIntro());
                bookTitleText = tempBookInfo.getTitle();

                if(tempBookInfo.getIsFavorite().equals("TRUE")){
                    btnFavOn.setVisibility(View.VISIBLE);
                    btnFavOff.setVisibility(View.GONE);
                } else if(tempBookInfo.getIsFavorite().equals("FALSE")){
                    btnFavOn.setVisibility(View.GONE);
                    btnFavOff.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Book_Detail", "에러!"));

        queue.add(jsonRequest);

        bookCover.setOnClickListener(v -> {
            if(btnFavOff.getVisibility() == View.VISIBLE){
//                BookPagination.favToggle(queue, bookCode, userToken);
                Toast.makeText(getApplicationContext(), "'" + bookTitleText + "'이(가) 선호작에 등록되었습니다.", Toast.LENGTH_SHORT).show();
                btnFavOn.setVisibility(View.VISIBLE);
                btnFavOff.setVisibility(View.GONE);
            } else if (btnFavOn.getVisibility() == View.VISIBLE) {
//                BookPagination.favToggle(queue, bookCode, userToken);
                Toast.makeText(getApplicationContext(), "'" + bookTitleText + "'을(를) 선호작에서 해제하였습니다.", Toast.LENGTH_SHORT).show();
                btnFavOn.setVisibility(View.GONE);
                btnFavOff.setVisibility(View.VISIBLE);
            }
        });
    }

    public void categoryCheck(JSONObject book) throws JSONException {

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
            bookTypeBody.setText(R.string.FREE);
        }
    }

    public void textColor(int text, int color) {
        bookTypeBody.setText(text);
        bookTypeBody.setTextColor(color);
        categoryBody.setTextColor(color);
        barBody.setTextColor(color);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailTab1(), "연재목록");
        adapter.addFragment(new DetailTab2(), "작가의 다른 작품");
        adapter.addFragment(new DetailTab3(), "작품 게시판");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
