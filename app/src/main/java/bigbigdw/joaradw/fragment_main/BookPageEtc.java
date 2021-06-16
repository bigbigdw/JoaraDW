package bigbigdw.joaradw.fragment_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Objects;

import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.Fragment_Best.Main_BookListAdapter_Best;
import bigbigdw.joaradw.base.BookBaseAcitivy;
import bigbigdw.joaradw.main.Main_BookListAdapter_C;
import bigbigdw.joaradw.main.Main_BookListData;
import bigbigdw.joaradw.R;

public class BookPageEtc extends BookBaseAcitivy {
    private Main_BookListAdapter_C adapter;
    private RecyclerView recyclerView;
    private final ArrayList<Main_BookListData> items = new ArrayList<>();
    LinearLayout wrap;
    LinearLayout cover;
    LinearLayout blank;
    String token = "";
    String title;
    String etcUrl;
    String apiUrl;
    String type = "";
    TextView booktitle;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklist_etc);

        booktitle = findViewById(R.id.BookTitle);
        recyclerView = findViewById(R.id.BookDetail);
        wrap = findViewById(R.id.TabWrap);
        cover = findViewById(R.id.LoadingLayout);
        blank = findViewById(R.id.BlankLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        checkToken();

        Intent pageIntent = getIntent();
        title = pageIntent.getStringExtra("Title");
        booktitle.setText(title);
        apiUrl = pageIntent.getStringExtra("API_URL");
        etcUrl = pageIntent.getStringExtra("ETC_URL");

        if(pageIntent.getStringExtra("TYPE") != null){
            type = pageIntent.getStringExtra("TYPE");
        } else {
            type = "";
        }

        queue = Volley.newRequestQueue(this);

        BookPagination.populateData(apiUrl, etcUrl + "&page=1", queue, wrap, items, cover, blank, type);
        initAdapter();

        if(type.equals("FINISH")){
            BookPagination.ScrollListener(apiUrl, queue, wrap, items, adapter, recyclerView, etcUrl);
        }

    }

    private void initAdapter() {
        if (type.equals("BEST")) {
            Main_BookListAdapter_Best bestAdapter = new Main_BookListAdapter_Best(items);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            bestAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(bestAdapter);

            bestAdapter.setOnItemClickListener((v, position, value) -> {
                Main_BookListData item = adapter.getItem(position);
                adapterListener(item, value, queue);
            });
        } else {
            adapter = new Main_BookListAdapter_C(items);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener((v, position, value) -> {
                Main_BookListData item = adapter.getItem(position);
                adapterListener(item, value, queue);
            });
        }
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
