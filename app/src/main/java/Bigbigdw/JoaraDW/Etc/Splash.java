package Bigbigdw.JoaraDW.Etc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Main.Main;
import Bigbigdw.JoaraDW.R;

public class Splash extends Activity {
    RequestQueue queue;
    JSONObject GETUSERINFO;
    String TOKEN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        queue = Volley.newRequestQueue(this);

        if(Config.GETUSERINFO() != null){
            GETUSERINFO = Config.GETUSERINFO();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
                TOKEN = "";
            }
        }

        String ResultURL = HELPER.API + "/v1/user/token_check.joa" + HELPER.ETC + "&token=" + TOKEN;
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {

            try {
                if (!response.getString("status").equals("1")) {
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(getDataDir() + "/" + "userInfo.json");
                        BufferedWriter bufwr = new BufferedWriter(fw);
                        bufwr.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Main", "에러!"));

        queue.add(jsonRequest);

        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(this::finish, 2000);
    }


}
