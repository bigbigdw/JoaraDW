package Bigbigdw.JoaraDW.Book_Detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import Bigbigdw.JoaraDW.R;

public class Book_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String BookCode = intent.getStringExtra("BookCode");

        Log.d("BookCode", BookCode);

    }
}
