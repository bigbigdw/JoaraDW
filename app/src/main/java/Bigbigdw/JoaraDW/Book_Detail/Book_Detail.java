package Bigbigdw.JoaraDW.Book_Detail;

import android.content.Intent;
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


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Book_Pagination;
import Bigbigdw.JoaraDW.JOARADW;
import Bigbigdw.JoaraDW.R;

public class Book_Detail extends AppCompatActivity {

    private RequestQueue queue;
    String STATUS = "", TOKEN = "", BookDetailURL, BookCode = "", BookTitleText = "", FavCheck = "";
    TextView BookTitle, BookTypeBody, CategoryBody, BookTitleBody, BookWriterBody, BookReadBody, BookRecommendBody, BookFavBody, BookCommentBody, BarBody, BookDetailIntro;
    AppBarLayout BookAppBar;
    ImageView BookCover, FavON, FavOff;
    Boolean BookDetailTF = false;
    LinearLayout FavWrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);
        queue = Volley.newRequestQueue(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        BookCode = intent.getStringExtra("BookCode");
        TOKEN = intent.getStringExtra("TOKEN");
        BookDetailURL = HELPER.API + "/v1/book/detail.joa" + HELPER.ETC + "&token=" + TOKEN + "&category=0&book_code=" + BookCode + "&promotion_code=";

        BookAppBar = findViewById(R.id.BookAppBar);
        BookTypeBody = findViewById(R.id.BookTypeBody);
        CategoryBody = findViewById(R.id.CategoryBody);
        BookTitleBody = findViewById(R.id.BookTitleBody);
        BookWriterBody = findViewById(R.id.BookWriterBody);
        BookReadBody = findViewById(R.id.BookReadBody);
        BookRecommendBody = findViewById(R.id.BookRecommendBody);
        BookFavBody = findViewById(R.id.BookFavBody);
        BookCommentBody = findViewById(R.id.BookCommentBody);
        BarBody = findViewById(R.id.BarBody);
        BookCover = findViewById(R.id.BookCover);
        BookDetailIntro = findViewById(R.id.BookDetailIntro);
        FavON = findViewById(R.id.FavON);
        FavOff = findViewById(R.id.FavOff);
        FavWrap = findViewById(R.id.FavWrap);
        BookTitle = findViewById(R.id.BookTitle);
        ViewPager viewPager = findViewById(R.id.view_pager);

        JOARADW myApp = (JOARADW) getApplicationContext();
        myApp.setBookCode(BookCode);
        myApp.setToken(TOKEN);
        myApp.setAPI_URL(BookDetailURL);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, BookDetailURL, null, response -> {
            try {
                JSONObject BOOK = response.getJSONObject("book");

                Glide.with(this).load(BOOK.getString("book_img"))
                        .into(BookCover);

                FavCheck = BOOK.getString("is_favorite");

                if (BOOK.getString("is_nobless").equals("TRUE") && BOOK.getString("is_adult").equals("FALSE")) {
                    textColor(R.string.NOBLESS, 0xAAa5c500);
                } else if (BOOK.getString("is_premium").equals("TRUE") && BOOK.getString("is_adult").equals("FALSE")) {
                    textColor(R.string.PREMIUM, 0xAA4971EF);
                } else if (BOOK.getString("is_finish").equals("TRUE") && BOOK.getString("is_adult").equals("FALSE")) {
                    textColor(R.string.FINISH, 0xAAa5c500);
                } else if (BOOK.getString("is_nobless").equals("TRUE") && BOOK.getString("is_adult").equals("TRUE")) {
                    textColor(R.string.ADULT_NOBLESS, 0xAAF44336);
                } else if (BOOK.getString("is_premium").equals("TRUE") && BOOK.getString("is_adult").equals("TRUE")) {
                    textColor(R.string.ADULT_PREMIUM, 0xAA4971EF);
                } else if (BOOK.getString("is_finish").equals("TRUE") && BOOK.getString("is_adult").equals("TRUE")) {
                    textColor(R.string.ADULT_FINISH, 0xAA767676);
                } else {
                    BookTypeBody.setText(R.string.FREE);
                }

                BookTitleBody.setText(BOOK.getString("subject"));
                BookWriterBody.setText(BOOK.getString("writer_name"));
                CategoryBody.setText(BOOK.getString("category_ko_name"));
                BookReadBody.setText(BOOK.getString("cnt_page_read"));
                BookRecommendBody.setText(BOOK.getString("cnt_recom"));
                BookFavBody.setText(BOOK.getString("cnt_favorite"));
                BookCommentBody.setText(BOOK.getString("cnt_total_comment"));
                BookDetailIntro.setText(BOOK.getString("intro"));
                BookTitleText = BOOK.getString("subject");

                String isFavorate = BOOK.getString("is_favorite");

                if(isFavorate.equals("TRUE")){
                    FavON.setVisibility(View.VISIBLE);
                    FavOff.setVisibility(View.GONE);
                } else {
                    FavON.setVisibility(View.GONE);
                    FavOff.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Book_Detail", "에러!"));

        queue.add(jsonRequest);

        setupViewPager(viewPager);

        if (Config.GETUSERINFO() != null) {
            JSONObject GETUSERINFO = Config.GETUSERINFO();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
                STATUS = GETUSERINFO.getString("status");

                if (STATUS.equals("1")) {
                    FavWrap.setVisibility(View.VISIBLE);
                } else {
                    FavWrap.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                TOKEN = "";
            }
        }

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void toggleOption(FloatingActionButton FloatingActionButton) {
        if ((BookDetailTF.equals(false))) {
            FloatingActionButton.setVisibility(View.GONE);
            FloatingActionButton.animate().alpha(1.0f);
        } else if ((BookDetailTF.equals(true))) {
            FloatingActionButton.setVisibility(View.VISIBLE);
            FloatingActionButton.animate().alpha(1.0f);
        }
    }

    public void textColor(int Text, int Color) {
        BookTypeBody.setText(Text);
        BookTypeBody.setTextColor(Color);
        CategoryBody.setTextColor(Color);
        BarBody.setTextColor(Color);
    }

    public void onClickFav(View v) {
        if (FavOff.getVisibility() == View.VISIBLE) {
            Book_Pagination.FavToggle(queue, BookCode, TOKEN);
            Toast.makeText(getApplicationContext(), "'" + BookTitleText + "'이(가) 선호작에 등록되었습니다.", Toast.LENGTH_SHORT).show();
            FavON.setVisibility(View.VISIBLE);
            FavOff.setVisibility(View.GONE);
        } else if (FavON.getVisibility() == View.VISIBLE) {
            Book_Pagination.FavToggle(queue, BookCode, TOKEN);
            Toast.makeText(getApplicationContext(), "'" + BookTitleText + "'을(를) 선호작에서 해제하였습니다.", Toast.LENGTH_SHORT).show();
            FavON.setVisibility(View.GONE);
            FavOff.setVisibility(View.VISIBLE);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Detail_Tab_1(), "연재목록");
        adapter.addFragment(new Detail_Tab_2(), "작가의 다른 작품");
        adapter.addFragment(new Detail_Tab_3(), "작품 게시판");
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
