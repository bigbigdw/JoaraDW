package Bigbigdw.JoaraDW.Test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Bigbigdw.JoaraDW.R;

public class Test extends AppCompatActivity implements Test_RestAPI {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Test_RestAPI.getJSON();



    }


}
