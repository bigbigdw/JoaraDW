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

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Fragment_New.Book_Pagination;
import Bigbigdw.JoaraDW.R;

public class Book_Detail extends AppCompatActivity {

    private RequestQueue queue;
    String TOKEN = "";
    String BookDetailURL;
    TextView BookTypeBody, CategoryBody, BookTitleBody, BookWriterBody, BookReadBody, BookRecommendBody, BookFavBody, BookCommentBody, BarBody, BookDetailIntro;
    AppBarLayout BookAppBar;
    FloatingActionButton BookDetailOption;
    String BookCode = "";
    String BookTitleText = "";
    Button BookDetailHeader1, BookDetailHeader3;
    ImageView BookCover;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
        BookDetailOption = findViewById(R.id.BookDetailOption);

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

        BookDetailHeader1 = findViewById(R.id.BookDetailHeader1);
        BookDetailHeader3 = findViewById(R.id.BookDetailHeader3);
        BookDetailIntro = findViewById(R.id.BookDetailIntro);

        BookAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
            {
                BookDetailOption.setVisibility(View.VISIBLE);
                BookDetailOption.animate().alpha(1.0f);
            }
            else
            {
                BookDetailOption.setVisibility(View.GONE);
                BookDetailOption.animate().alpha(0.0f);
            }
        });

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, BookDetailURL, null, response -> {
//            System.out.println(response);
            try {
                JSONObject book = response.getJSONObject("book");

                Glide.with(this).load(book.getString("book_img"))
                        .into(BookCover);

                if (book.getString("is_nobless").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookTypeBody.setText(R.string.NOBLESS);
                    BookTypeBody.setTextColor(0xAAa5c500);
                    CategoryBody.setTextColor(0xAAa5c500);
                    BarBody.setTextColor(0xAAa5c500);
                } else if (book.getString("is_premium").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookTypeBody.setText(R.string.PREMIUM);
                    BookTypeBody.setTextColor(0xAA4971EF);
                    CategoryBody.setTextColor(0xAA4971EF);
                    BarBody.setTextColor(0xAA4971EF);
                } else if (book.getString("is_finish").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookTypeBody.setText(R.string.FINISH);
                    BookTypeBody.setTextColor(0xAAa5c500);
                    CategoryBody.setTextColor(0xAAa5c500);
                    BarBody.setTextColor(0xAAa5c500);
                } else if (book.getString("is_nobless").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookTypeBody.setText(R.string.ADULT_NOBLESS);
                    BookTypeBody.setTextColor(0xAAF44336);
                    CategoryBody.setTextColor(0xAAF44336);
                    BarBody.setTextColor(0xAAF44336);
                } else if (book.getString("is_premium").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookTypeBody.setText(R.string.ADULT_PREMIUM);
                    BookTypeBody.setTextColor(0xAA4971EF);
                    CategoryBody.setTextColor(0xAA4971EF);
                    BarBody.setTextColor(0xAA4971EF);
                } else if (book.getString("is_finish").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookTypeBody.setText(R.string.ADULT_FINISH);
                    BookTypeBody.setTextColor(0xAA767676);
                    CategoryBody.setTextColor(0xAA767676);
                    BarBody.setTextColor(0xAA767676);
                } else {
                    BookTypeBody.setText(R.string.FREE);
                }


                BookTitleBody.setText(book.getString("subject"));
                BookWriterBody.setText(book.getString("writer_name"));
                CategoryBody.setText(book.getString("category_ko_name"));
                BookReadBody.setText(book.getString("cnt_page_read"));
                BookRecommendBody.setText(book.getString("cnt_recom"));
                BookFavBody.setText(book.getString("cnt_favorite"));
                BookCommentBody.setText(book.getString("cnt_total_comment"));
                BookDetailIntro.setText(book.getString("intro"));
                BookTitleText = book.getString("subject");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Book_Detail", "완료!");
        }, error -> Log.d("Book_Detail", "에러!"));

        queue.add(jsonRequest);

        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Detail_Tab_1(), "연재목록");
        adapter.addFragment(new Detail_Tab_1(), "작가의 다른 작품");
        adapter.addFragment(new Detail_Tab_1(), "추천 작품");
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
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
