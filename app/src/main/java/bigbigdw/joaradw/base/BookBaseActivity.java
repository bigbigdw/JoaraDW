package bigbigdw.joaradw.base;

import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import bigbigdw.joaradw.R;
import bigbigdw.joaradw.book_detail.BookDetail;
import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.book_detail.DetailBookPageData;
import bigbigdw.joaradw.book_viewer.BookViewer;
import bigbigdw.joaradw.novel.OLD_MainBookListData;

public class BookBaseActivity extends AppCompatActivity {

    String token = "";
    String status = "";
    String name;
    String mana;
    String expireCash;
    String cash;
    String manuscriptCoupon;
    String supportCoupon;

    /**
     * Toolbar Title 변경
     */
    public void setTitle(String title){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView tviewTitle = findViewById(R.id.toolbarTitle);
        tviewTitle.setText(title);
    }

    public void checkToken() {
        if (Config.getuserinfo() != null) {
            JSONObject getUserInfo = Config.getuserinfo();
            JSONObject userInfo;
            try {
                userInfo = getUserInfo.getJSONObject("user");
                token = userInfo.getString("token");
                status = getUserInfo.getString("status");
                name = new String(userInfo.getString("nickname").getBytes(), StandardCharsets.UTF_8);
                mana = userInfo.getString("mana");
                expireCash = userInfo.getString("expire_cash");
                cash = userInfo.getString("cash");
                manuscriptCoupon = userInfo.getString("manuscript_coupon");
                supportCoupon = userInfo.getString("support_coupon");

                JOARADW app = (JOARADW) getApplicationContext();
                app.setStatus(status);
                app.setToken(token);
                app.setName(name);
                app.setMana(mana);
                app.setExpireCash(expireCash);
                app.setCash(cash);
                app.setManuscriptCoupon(manuscriptCoupon);
                app.setSupportCoupon(supportCoupon);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void adapterListener(OLD_MainBookListData item, String value, RequestQueue queue) {
        if (value.equals("FAV")) {
            BookPagination.favToggle(queue, item.getBookCode(), token);
        } else if (value.equals("BookDetail")) {
            Intent intent = new Intent(this.getApplicationContext().getApplicationContext(), bigbigdw.joaradw.book_detail.BookDetailCover.class);
            intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
            intent.putExtra("TOKEN", String.format("%s", token));
            startActivity(intent);
        }
    }

    public void adapterListenerBookViewer(DetailBookPageData item, String userToken, String bookCode) {
        Intent intentViewer = new Intent(this.getApplicationContext(), BookViewer.class);
        intentViewer.putExtra("Cid", String.format("%s", item.getCid()));
        intentViewer.putExtra("TOKEN", String.format("%s", userToken));
        intentViewer.putExtra("BOOKCODE", String.format("%s", bookCode));
        intentViewer.putExtra("SORTNO", String.format("%s", item.getBookListNum()));
        intentViewer.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intentViewer);
    }

    public void onClickGoDetail(String userToken, String bookCode) {
        Intent intent = new Intent(getApplicationContext(), BookDetail.class);
        intent.putExtra("BookCode", String.format("%s", bookCode));
        intent.putExtra("TOKEN", String.format("%s", userToken));
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
