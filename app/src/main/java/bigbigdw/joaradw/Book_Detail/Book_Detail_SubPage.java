package bigbigdw.joaradw.Book_Detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import bigbigdw.joaradw.R;

public class Book_Detail_SubPage extends AppCompatActivity {
    private final Book_Detail_PageAdapter NoticeAdapter = new Book_Detail_PageAdapter();
    String BookCode, TOKEN, Value;
    RecyclerView recyclerView;
    LinearLayout Wrap;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_subpage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        BookCode = intent.getStringExtra("BOOKCODE");
        TOKEN = intent.getStringExtra("TOKEN");
        Value = intent.getStringExtra("VALUE");

        Wrap = findViewById(R.id.Wrap);

        queue = Volley.newRequestQueue(this);

        NoticeAdapter();
    }

    public void NoticeAdapter() {
        recyclerView = findViewById(R.id.Book_NoticeList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(NoticeAdapter);
        NoticeAdapter.setItems(new Detail_Tab_3_Data().getData(queue, Wrap, BookCode, Value));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                manager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        NoticeAdapter.notifyDataSetChanged();
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
