package Bigbigdw.JoaraDW.Login;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Bigbigdw.JoaraDW.Main.Main_BookListData_A;


public interface Login_API {

    static void SignIn(RequestQueue queue,
                       TextInputLayout ID,
                       TextInputLayout PW, Button LoginBtn) {

        String API = "https://api.joara.com";
        String ETC = "/v1/user/auth.joa";
        String ResultURL = API + ETC;

        String idCheck = Objects.requireNonNull(ID.getEditText()).getText().toString();
        String pwCheck = Objects.requireNonNull(PW.getEditText()).getText().toString();

        System.out.println(idCheck);
        System.out.println(pwCheck);

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ResultURL, response -> {
            try {
                JSONObject reader = new JSONObject(response);
                JSONObject userInfo = reader.getJSONObject("user");
                System.out.println(userInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("member_id", "bigbigdw");
                params.put("passwd", "!ms47kdnt53");
                params.put("api_key", "mw_8ba234e7801ba288554ca07ae44c7");
                params.put("ver", "2.6.3");
                params.put("device", "mw");
                params.put("deviceuid", "5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604");
                params.put("devicetoken", "mw");
                return params;
            }
        };
        queue.add(stringRequest);
    }

}


