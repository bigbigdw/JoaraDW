package bigbigdw.joaradw.book_detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import bigbigdw.joaradw.R;
import bigbigdw.joaradw.base.BookBaseActivity;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.HELPER;

public class BookReport extends BookBaseActivity {
    public static final String BOOK_CODE = "book_code";
    TextView subject;
    TextView writer;
    EditText reportDetail;
    String writerName;
    String writerId;
    String stringTitle;
    String bookCode;
    String comment = "";
    Button btnSubmit;
    String userToken;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_report);

        subject = findViewById(R.id.Subject);
        writer = findViewById(R.id.Writer);
        reportDetail = findViewById(R.id.ReportDetail);
        btnSubmit = findViewById(R.id.BtnSubmit);

        Intent intent = getIntent();
        stringTitle = intent.getStringExtra("Title");
        writerName = intent.getStringExtra("Writer");
        writerId = intent.getStringExtra("Writer_Id");
        userToken = intent.getStringExtra("Token");
        bookCode = intent.getStringExtra("BookCode");

        subject.setText(stringTitle);
        writer.setText(writerName);

        // Title
        setTitle("작품 신고");

        // 토큰 인증
        checkToken();

        btnSubmit.setOnClickListener(v -> postReportBook());
        queue = Volley.newRequestQueue(this);
    }

    /**
     * 필수항목 체크 후 전송
     */
    private void postReportBook() {
        if (comment.equals("")) {
            Toast.makeText(getApplicationContext(), "신고 유형을 선택해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, HELPER.API + API.BOOK_BOOK_REPORT, response ->
            {
                if (response.contains("{\"status\":1}")) {
                    Toast.makeText(getApplicationContext(), "작품명 : \"" + stringTitle + "\" 을 신고하였습니다", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "신고에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }, error -> Log.d("postReportBook", "실패!")) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("api_key", HELPER.API_KEY);
                    params.put("ver", HELPER.VER);
                    params.put("device", HELPER.DEVICE);
                    params.put("deviceuid", HELPER.DEVICE_ID);
                    params.put("devicetoken", HELPER.DEVICE_TOKEN);
                    params.put("token", userToken);
                    params.put("category", "0");
                    params.put("book_code", bookCode);
                    params.put("penalty_id", writerId);
                    params.put("penalty_name", writerName);
                    params.put("memo", reportDetail.getText().toString());
                    params.put("comment", comment);
                    return params;
                }
            };
            queue.add(stringRequest);
        }
    }


    public void onclickreport(View view) {
        if (view.getId() == R.id.Report_Radio1) {
            comment = "폭력 / 음란성 / 청소년 부적합 내용";
        } else if (view.getId() == R.id.Report_Radio2) {
            comment = "타 사이트 홍보 및 가입유도 / 상업성";
        } else if (view.getId() == R.id.Report_Radio3) {
            comment = "명예훼손, 사생활 침해, 저작권 침해 등";
        } else if (view.getId() == R.id.Report_Radio4) {
            comment = "동일 작품 중복 업로드(도배)";
        } else if (view.getId() == R.id.Report_Radio5) {
            comment = "기타 사유";
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
