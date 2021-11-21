package bigbigdw.joaradw.novel;

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

import bigbigdw.joaradw.base.BookBaseActivity;
import bigbigdw.joaradw.R;

public class BookPageEtc extends BookBaseActivity {
    private OLD_MainBookListAdapterC adapter;
    private RecyclerView recyclerView;
    private final ArrayList<OLD_MainBookListData> items = new ArrayList<>();
    LinearLayout wrap;
    LinearLayout cover;
    LinearLayout blank;
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

//        BookPagination.populateData(apiUrl, etcUrl + "&page=1", queue, wrap, items, cover, blank);
//        initAdapter();
//
//        if(type.equals("FINISH")){
//            BookPagination.scrollListener(apiUrl, queue, wrap, items, adapter, recyclerView, etcUrl);
//        }

    }

    private void initAdapter() {
        if (type.equals("BEST")) {

        } else {
            adapter = new OLD_MainBookListAdapterC(items);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener((v, position, value) -> {
                OLD_MainBookListData item = adapter.getItem(position);
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
