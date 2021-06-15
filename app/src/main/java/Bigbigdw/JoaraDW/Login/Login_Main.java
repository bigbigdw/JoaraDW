package Bigbigdw.JoaraDW.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.JOARADW;
import Bigbigdw.JoaraDW.Main.Main;
import Bigbigdw.JoaraDW.R;

public class Login_Main extends AppCompatActivity {

    TextInputLayout IDtext, PWtext;
    ImageView LOGO;
    RequestQueue queue;
    Button LoginBtn;
    String TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        IDtext = findViewById(R.id.IDtext);
        PWtext = findViewById(R.id.PWtext);
        LoginBtn = findViewById(R.id.LoginBtn);
        queue = Volley.newRequestQueue(this);
        LOGO = findViewById(R.id.LOGO);

        Objects.requireNonNull(IDtext.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 1) {
                    IDtext.setError(getString(R.string.Login_EmptyID));
                    IDtext.setErrorEnabled(true);
                }
                else {
                    IDtext.setErrorEnabled(false);
                }
            }
        });

        Objects.requireNonNull(PWtext.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 1) {
                    PWtext.setError(getString(R.string.Login_EmptyPW));
                    PWtext.setErrorEnabled(true);
                }
                else {
                    PWtext.setErrorEnabled(false);
                }
            }
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

    static void WriteJson(String filename, String response) {
        FileWriter fw = null;

        try {
            fw = new FileWriter(filename + "userInfo.json");
            BufferedWriter bufwr = new BufferedWriter(fw);
            bufwr.write(response);
            bufwr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onClickLogin(View v) {

        String ResultURL = HELPER.API + "/v1/user/auth.joa";
        String idCheck = Objects.requireNonNull(IDtext.getEditText()).getText().toString();
        String pwCheck = Objects.requireNonNull(PWtext.getEditText()).getText().toString();

        if (idCheck.length() > 1 && pwCheck.length() > 1) {
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, ResultURL, response -> {
                try {
                    JSONObject reader = new JSONObject(response);
                    JSONObject UserInfo = reader.getJSONObject("user");
                    String UserName = UserInfo.getString("nickname");
                    String filename = getDataDir() + "/";

                    WriteJson(filename, response);

                    TOKEN = UserInfo.getString("token");
                    JOARADW userInfo = (JOARADW) getApplicationContext();
                    userInfo.setTOKEN(TOKEN);

                    Toast.makeText(getApplicationContext(), "환영합니다!" + " " + UserName + "님!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    intent.putExtra("IsFirstPage", false);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(intent, 0);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();
                }

            }, error -> {
                System.out.println("ERROR");
                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
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
            Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            Log.d("ONSTOP", "queue가 없습니다");
        }
    }

    public void onClickMain(View v) {
        Intent intent = new Intent(getApplicationContext(), Main.class);
        startActivity(intent);
    }

    public void onClickFindID(View v) {
        Toast.makeText(getApplicationContext(), "아이디 찾기로 이동합니다.", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(), Login_FindID.class);
//        startActivity(intent);
    }

    public void onClickFindPW(View v) {
        Toast.makeText(getApplicationContext(), "비밀번호 찾기로 이동합니다.", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(), Login_FindPW.class);
//        startActivity(intent);
    }


    public void onClickRegister(View v) {
        Toast.makeText(getApplicationContext(), "현재는 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(), Login_Register.class);
//        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}



