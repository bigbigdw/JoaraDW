package Bigbigdw.JoaraDW.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Bigbigdw.JoaraDW.Etc.Splash;
import Bigbigdw.JoaraDW.Main.Main;
import Bigbigdw.JoaraDW.R;

public class Login_Main extends AppCompatActivity {

    TextInputLayout IDtext, PWtext;
    Editable idCheck, pwCheck;
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
        final CheckBox AutoLogin = findViewById(R.id.AutoLogin);

        Intent intent = new Intent(this, Splash.class);
        startActivity(intent);

        LOGO = findViewById(R.id.LOGO);

        AutoLogin.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if (AutoLogin.isChecked()) {
                        Toast.makeText(Login_Main.this.getApplicationContext(), "자동로그인이 활성화 되었습니다", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login_Main.this.getApplicationContext(), "자동로그인이 비활성화 되었습니다", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        idCheck = Objects.requireNonNull(IDtext.getEditText()).getText();
        pwCheck = Objects.requireNonNull(PWtext.getEditText()).getText();

        System.out.println(idCheck);



//        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.joara.com/v1/user/auth.joa", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                System.out.println(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("ERROR");
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("member_id", "bigbigdw");
//                params.put("passwd", "!ms47kdnt53");
//                params.put("api_key", "mw_8ba234e7801ba288554ca07ae44c7");
//                params.put("ver", "2.6.3");
//                params.put("device", "mw");
//                params.put("deviceuid", "5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604");
//                params.put("devicetoken", "mw");
//                return params;
//            }
//        };
//
//
//        LoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                queue.add(stringRequest);
//
//            }
//        });
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


    public void onClickLogin(View v) {
        idCheck = Objects.requireNonNull(IDtext.getEditText()).getText();
        pwCheck = Objects.requireNonNull(PWtext.getEditText()).getText();

//        if (idCheck.toString().equals("kdw0310@ajou.ac.kr") && pwCheck.toString().equals("bigbigdw")) {
//            Toast.makeText(getApplicationContext(), "환영합니다 김대우님!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), Main.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();
//        }





        Login_API.SignIn(queue, IDtext, PWtext, LoginBtn);
//        Intent intent = new Intent(getApplicationContext(), Main.class);
//        startActivity(intent);
    }

    public void onClickRegister(View v) {
        Toast.makeText(getApplicationContext(), "현재는 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(), Login_Register.class);
//        startActivity(intent);
    }
}
