package Bigbigdw.JoaraDW.Book_Viewer;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Bigbigdw.JoaraDW.Book_Detail.Detail_BookPageData;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.R;

public class Book_Viewer extends AppCompatActivity {

    String CID, TOKEN, API_URL, BookCode, CryptKey_URL, CrptedContents, SortNO;
    TextView ViewerText;
    JSONArray Data;
    private final ArrayList<Detail_BookPageData> items = new ArrayList<>();
    JSONObject ViwerJSON;

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
        SortNO = intent.getStringExtra("SORTNO");
        RequestQueue queue = Volley.newRequestQueue(this);

        CryptKey_URL = HELPER.API + "/v1/book/chapter_valid.joa" + HELPER.ETC + "&token=" + TOKEN + "&category=0";

        final JsonObjectRequest CryptKey = new JsonObjectRequest(Request.Method.GET, CryptKey_URL, null, response -> {
            try {
                Data = response.getJSONArray("data");
                Data.getString(1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Book_Pagination", "에러!"));
        queue.add(CryptKey);

        API_URL = HELPER.API + "/v1/book/chapter.joa" + HELPER.ETC + "&book_code=" + BookCode + "&cid=" + CID + "&token=" + TOKEN + "&sortno=" + SortNO;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, response -> {
            try {
                JSONObject CHAPTER = response.getJSONObject("chapter");
                CrptedContents = CHAPTER.getString("content");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Book_Pagination", "에러!"));
        queue.add(jsonRequest);

        AssetManager assetManager = getAssets();

        ViwerJSON = Config.GETFAKEVIEWER(assetManager);
        try {
            String Result = ViwerJSON.getString("books");
            ViewerText.setText(Result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
