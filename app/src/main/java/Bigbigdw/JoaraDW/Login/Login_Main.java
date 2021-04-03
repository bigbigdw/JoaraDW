package Bigbigdw.JoaraDW.Login;

import android.content.Intent;
import android.os.Bundle;
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

import Bigbigdw.JoaraDW.HELPER;
import Bigbigdw.JoaraDW.Etc.Splash;
import Bigbigdw.JoaraDW.Main.Main;
import Bigbigdw.JoaraDW.R;

public class Login_Main extends AppCompatActivity {

    TextInputLayout IDtext, PWtext;
    ImageView LOGO;
    RequestQueue queue;
    Button LoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        IDtext = findViewById(R.id.IDtext);
        PWtext = findViewById(R.id.PWtext);
        LoginBtn = findViewById(R.id.LoginBtn);
        queue = Volley.newRequestQueue(this);

        Intent intent = new Intent(this, Splash.class);
        startActivity(intent);

        LOGO = findViewById(R.id.LOGO);

    }


    public void onClickLogin(View v) {

        String ResultURL = "https://api.joara.com/v1/user/auth.joa";
        String idCheck = Objects.requireNonNull(IDtext.getEditText()).getText().toString();
        String pwCheck = Objects.requireNonNull(PWtext.getEditText()).getText().toString();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ResultURL, response -> {
            try {
                JSONObject reader = new JSONObject(response);
                JSONObject userInfo = reader.getJSONObject("user");
                String UserName = userInfo.getString("nickname");

                FileWriter fw = null;
                String filename = getDataDir() + "/";
                try {
                    fw = new FileWriter(filename + "userInfo.json");
                    System.out.println(filename);

                    BufferedWriter bufwr = new BufferedWriter(fw);
                    bufwr.write(response);
                    bufwr.close();

                    System.out.println("USERINFO 쓰기 완료");

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("USERINFO 쓰기 실패");
                }

                Toast.makeText(getApplicationContext(), "환영합니다!" + UserName + "님!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Main.class);
                intent.putExtra("IsFirstPage", false);
                startActivity(intent);

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
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            System.out.println("HIHI");
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

}



