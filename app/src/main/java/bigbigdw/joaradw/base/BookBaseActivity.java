package bigbigdw.joaradw.base;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import bigbigdw.joaradw.book_detail.BookDetailCover;
import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.main.MainBookListData;

public class BookBaseActivity extends AppCompatActivity {

    String token = "";
    String status = "";
    String name;
    String mana;
    String expireCash;
    String cash;
    String manuscriptCoupon;
    String supportCoupon;

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

    public void adapterListener(MainBookListData item, String value, RequestQueue queue) {
        if (value.equals("FAV")) {
            BookPagination.favToggle(queue, item.getBookCode(), token);
        } else if (value.equals("BookDetail")) {
            Intent intent = new Intent(this.getApplicationContext().getApplicationContext(), BookDetailCover.class);
            intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
            intent.putExtra("TOKEN", String.format("%s", token));
            startActivity(intent);
        }
    }
}
