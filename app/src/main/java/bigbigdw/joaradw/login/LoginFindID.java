package bigbigdw.joaradw.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import bigbigdw.joaradw.R;
import bigbigdw.joaradw.base.RegisterActivity;

public class LoginFindID extends RegisterActivity {

    TextInputLayout phone;
    TextInputLayout num;
    TextView id;
    Button onclickphone;
    Button onclicknum;
    Button btnGoBack;
    Button btnFindPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_findid);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        phone =  findViewById(R.id.Phone);
        num =  findViewById(R.id.Num);
        id = findViewById(R.id.ID);
        onclicknum = findViewById(R.id.onClickNum);
        onclickphone = findViewById(R.id.onClickPhone);
        btnGoBack = findViewById(R.id.BtnGoBack);
        btnFindPW  = findViewById(R.id.BtnFindPW);

        setLayout();

    }

    public void setLayout(){
        Objects.requireNonNull(phone.getEditText()).addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged", "phone");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                phoneCheck(text, phone, onclickphone);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("LoginFindID", "phone");
            }
        }));

        Objects.requireNonNull(num.getEditText()).addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged", "num");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                numCheck(text,num , phone, onclicknum);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", "num");
            }
        }));

        onclickphone.setOnClickListener(v -> sendNumMsg(num));

        onclicknum.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"인증이 완료되었습니다", Toast.LENGTH_SHORT).show();
            id.setVisibility(View.VISIBLE);
        });

        btnGoBack.setOnClickListener(v -> goToMain());

        btnFindPW.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginFindPW.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}