package Bigbigdw.JoaraDW.Etc;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import Bigbigdw.JoaraDW.Main.MainBanner_ListAdapter;
import Bigbigdw.JoaraDW.Main.MainBanner_ListData;
import Bigbigdw.JoaraDW.R;

public class Test extends Activity {
    MainBanner_ListAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        init();
        getData();
    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.MainBannerList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainBanner_ListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        MainBanner_ListData data = new MainBanner_ListData(R.drawable.splash_logo);
        adapter.addItem(data);
        data = new MainBanner_ListData(R.drawable.splash_logo);
        adapter.addItem(data);
    }

}
