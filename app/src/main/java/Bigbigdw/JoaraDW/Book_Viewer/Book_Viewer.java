package Bigbigdw.JoaraDW.Book_Viewer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Book_Detail.Detail_BookPageData;
import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class Book_Viewer extends AppCompatActivity {

    private RequestQueue queue;
    String CID,TOKEN, API_URL, BookCode;
    TextView ViewerText;
    private ArrayList<Detail_BookPageData> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_viewer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewerText = findViewById(R.id.ViewerText);

        Intent intent = getIntent();
        CID = intent.getStringExtra("Cid");
        TOKEN = intent.getStringExtra("TOKEN");
        BookCode = intent.getStringExtra("BOOKCODE");
        queue = Volley.newRequestQueue(this);

        API_URL = HELPER.API + "/v1/book/chapter.joa" + HELPER.ETC + "&book_code=" + BookCode + "&cid=" + CID + "&token" + TOKEN;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, response -> {
            try {
                JSONObject CHAPTER = response.getJSONObject("chapter");
                String Contents = CHAPTER.getString("content");
//                Log.d("Contents", Contents);
                ViewerText.setText(Contents);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("response", response.toString());
        }, error -> Log.d("Book_Pagination", "에러!"));
        queue.add(jsonRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.viewer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
