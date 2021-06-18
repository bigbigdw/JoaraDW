package bigbigdw.joaradw.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.main.Main;
import bigbigdw.joaradw.R;

public class LoginMain extends AppCompatActivity {

    TextInputLayout idtext;
    TextInputLayout pwtext;
    TextView loginMainFindID;
    TextView loginMainFIndPW;
    ImageView logo;
    RequestQueue queue;
    Button loginBtn;
    Button registerBtn;
    String token;
    String loginFailMsg = "로그인에 실패하였습니다";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        idtext = findViewById(R.id.IDtext);
        pwtext = findViewById(R.id.PWtext);
        loginBtn = findViewById(R.id.LoginBtn);
        queue = Volley.newRequestQueue(this);
        logo = findViewById(R.id.LOGO);
        loginMainFindID = findViewById(R.id.LoginMain_FindID);
        loginMainFIndPW = findViewById(R.id.LoginMain_FIndPW);
        registerBtn = findViewById(R.id.RegisterBtn);

        Objects.requireNonNull(idtext.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("LoginMain", "idtext");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                Log.d("LoginMain", "idtext");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 1) {
                    idtext.setError(getString(R.string.Login_EmptyID));
                    idtext.setErrorEnabled(true);
                }
                else {
                    idtext.setErrorEnabled(false);
                }
            }
        });

        Objects.requireNonNull(pwtext.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("LoginMain", "pwtext");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                Log.d("LoginMain", "pwtext");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 1) {
                    pwtext.setError(getString(R.string.Login_EmptyPW));
                    pwtext.setErrorEnabled(true);
                }
                else {
                    pwtext.setErrorEnabled(false);
                }
            }
        });

        loginBtn.setOnClickListener(v -> {
            String resultURL = HELPER.API + "/v1/user/auth.joa";
            String idCheck = Objects.requireNonNull(idtext.getEditText()).getText().toString();
            String pwCheck = Objects.requireNonNull(pwtext.getEditText()).getText().toString();

            if (idCheck.length() > 1 && pwCheck.length() > 1) {
                final StringRequest stringRequest = new StringRequest(Request.Method.POST, resultURL, response -> {
                    try {
                        JSONObject reader = new JSONObject(response);
                        JSONObject userInfo = reader.getJSONObject("user");
                        String userName = userInfo.getString("nickname");
                        String dir = "/";
                        String filename = getDataDir() + dir;

                        writeJson(filename, response);

                        token = userInfo.getString("token");
                        JOARADW myApp = (JOARADW) getApplicationContext();
                        myApp.setToken(token);

                        Toast.makeText(getApplicationContext(), "환영합니다!" + " " + userName + "님!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        intent.putExtra("IsFirstPage", false);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(intent, 0);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), loginFailMsg, Toast.LENGTH_SHORT).show();
                    }

                }, error ->  Toast.makeText(getApplicationContext(), loginFailMsg, Toast.LENGTH_SHORT).show()) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("member_id", idCheck);
                        params.put("passwd", pwCheck);
                        params.put("api_key", HELPER.API_KEY);
                        params.put("ver", HELPER.VER);
                        params.put("device", HELPER.DEVICE);
                        params.put("deviceuid", HELPER.DEVICE_ID);
                        params.put("devicetoken", HELPER.DEVICE_TOKEN);
                        return params;
                    }
                };
                queue.add(stringRequest);
            } else {
                Toast.makeText(getApplicationContext(), loginFailMsg, Toast.LENGTH_SHORT).show();
            }
        });

        loginMainFindID.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "아이디 찾기로 이동합니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginFindID.class);
            startActivity(intent);
        });

        loginMainFIndPW.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "비밀번호 찾기로 이동합니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginFindPW.class);
            startActivity(intent);
        });

        registerBtn.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "회원가입 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginRegister.class);
            startActivity(intent);
        });

        logo.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Main.class);
            startActivity(intent);
        });

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Main.class);
        intent.putExtra("IsFirstPage", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        finish();
    }

    static void writeJson(String filename, String response) {
        try {
            FileWriter fw = new FileWriter(filename + "userInfo.json");
            try(BufferedWriter bufwr = new BufferedWriter(fw)){
                bufwr.write(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            Log.d("ONSTOP", "queue가 없습니다");
        }
    }
}



