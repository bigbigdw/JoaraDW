package bigbigdw.joaradw.book_detail;

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

import java.util.Objects;

import bigbigdw.joaradw.R;

public class BookDetailSubPage extends AppCompatActivity {
    private final BookDetailPageAdapter noticeAdapter = new BookDetailPageAdapter();
    String bookCode;
    String token;
    String value;
    RecyclerView recyclerView;
    LinearLayout wrap;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_subpage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        bookCode = intent.getStringExtra("BOOKCODE");
        token = intent.getStringExtra("TOKEN");
        value = intent.getStringExtra("VALUE");

        wrap = findViewById(R.id.Wrap);

        queue = Volley.newRequestQueue(this);

        noticeAdapter();
    }

    public void noticeAdapter() {
        recyclerView = findViewById(R.id.Book_NoticeList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(noticeAdapter);
        noticeAdapter.setItems(new DetailTab3Data().getData(queue, wrap, value));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                manager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        noticeAdapter.notifyDataSetChanged();
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
