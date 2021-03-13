package Bigbigdw.JoaraDW.Test;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import Bigbigdw.JoaraDW.R;

public class Test extends AppCompatActivity implements Test_RestAPI {

    private static final String TAG = "imagesearchexample";
    public static final int LOAD_SUCCESS = 101;

    private String SEARCH_URL = "https://secure.flickr.com/services/rest/?method=flickr.photos.search";
    private String API_KEY = "&api_key=b901381d5d56065a36032436ff20243a";
    private String PER_PAGE = "&per_page=50";
    private String SORT = "&sort=interestingness-desc";
    private String FORMAT = "&format=json";
    private String CONTECT_TYPE = "&content_type=1";
    private String SEARCH_TEXT = "&text='cat'";
    private String REQUEST_URL = "SEARCH_URL + API_KEY + PER_PAGE + SORT + FORMAT + CONTECT_TYPE + SEARCH_TEXT";


    private TextView textviewJSONText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Button buttonRequestJSON = findViewById(R.id.button_main_requestjson);
        textviewJSONText = findViewById(R.id.textview_main_jsontext);
        textviewJSONText.setMovementMethod(new ScrollingMovementMethod());

        buttonRequestJSON.setOnClickListener(v -> {
            Test_RestAPI.getJSON(TAG, REQUEST_URL, textviewJSONText);
        });

    }




}
