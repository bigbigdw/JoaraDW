package bigbigdw.joaradw.base;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import bigbigdw.joaradw.Book_Detail.Book_Detail_Cover;
import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.main.Main_BookListData;

public class BookBaseAcitivy extends AppCompatActivity {

    String token = "";
    String status = "";
    String userName;

    public void checkToken() {
        if (Config.getuserinfo() != null) {
            JSONObject getUserInfo = Config.getuserinfo();
            JSONObject userInfo;
            try {
                userInfo = getUserInfo.getJSONObject("user");
                token = userInfo.getString("token");
                status = getUserInfo.getString("status");
                userName = new String(userInfo.getString("nickname").getBytes(), StandardCharsets.UTF_8);

                JOARADW app = (JOARADW) getApplicationContext();
                app.setUserstatus(status);
                app.setToken(token);
                app.setUserName(userName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void adapterListener(Main_BookListData item, String value, RequestQueue queue) {
        if (value.equals("FAV")) {
            BookPagination.FavToggle(queue, item.getBookCode(), token);
        } else if (value.equals("BookDetail")) {
            Intent intent = new Intent(this.getApplicationContext().getApplicationContext(), Book_Detail_Cover.class);
            intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
            intent.putExtra("TOKEN", String.format("%s", token));
            startActivity(intent);
        }
    }
}
