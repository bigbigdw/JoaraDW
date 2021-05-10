package Bigbigdw.JoaraDW.Etc;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.R;

public class Splash extends Activity {
    RequestQueue queue;
    String TOKEN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        queue = Volley.newRequestQueue(this);

        String ResultURL = HELPER.API + "/v1/user/token_check.joa" + HELPER.ETC + "&token=" + TOKEN;
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {

            try {
                if (!response.getString("status").equals("1")) {
                    Config.DeleteJSON();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Splash", "에러!"));
        queue.add(jsonRequest);
        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(this::finish, 2000);
    }

}
