package Bigbigdw.JoaraDW.Test;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import Bigbigdw.JoaraDW.R;

public class Test extends AppCompatActivity {
    private Test_ListAdapter adapter = new Test_ListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
//        init();
//
//        //아이템 로드
//        adapter.setItems(new TestData().getData());

    }

//    private void init() {
//        RecyclerView recyclerView = findViewById(R.id.MainBannerList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//    }


}
