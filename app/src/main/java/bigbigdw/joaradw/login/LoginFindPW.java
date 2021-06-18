package bigbigdw.joaradw.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import bigbigdw.joaradw.R;

public class LoginFindPW extends AppCompatActivity {

    TextInputLayout phone;
    TextInputLayout num;
    TextInputLayout id;
    Button onClickID;
    Button onClickPhone;
    Button onClickNum;
    Button btnBack;
    Button btnFindID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_findpw);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        phone = findViewById(R.id.Phone);
        num = findViewById(R.id.Num);
        id = findViewById(R.id.ID);
        onClickID = findViewById(R.id.onClickID);
        onClickPhone = findViewById(R.id.onClickPhone);
        onClickNum = findViewById(R.id.onClickNum);
        btnBack = findViewById(R.id.Btn_Back);
        btnFindID = findViewById(R.id.Btn_FindID);

        Objects.requireNonNull(id.getEditText()).addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged_id", "id");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().length() < 10) {
                    id.setError(getString(R.string.FindID_IDInvalid));
                    id.setErrorEnabled(true);
                    onClickID.setVisibility(View.GONE);
                } else {
                    id.setErrorEnabled(false);
                    onClickID.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged_id", "id");
            }
        }));

        Objects.requireNonNull(phone.getEditText()).addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged", "phone");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().length() < 10) {
                    phone.setError(getString(R.string.Find_InputPhone_NO));
                    phone.setErrorEnabled(true);
                    onClickPhone.setVisibility(View.GONE);
                } else if (text.toString().length() == 11) {
                    phone.setErrorEnabled(false);
                    onClickPhone.setVisibility(View.VISIBLE);
                } else {
                    phone.setErrorEnabled(false);
                    onClickPhone.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", "phone");
            }
        }));

        Objects.requireNonNull(num.getEditText()).addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged", "num");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().length() < 5) {
                    num.setError(getString(R.string.Find_InputNum_NO));
                    num.setErrorEnabled(true);
                    onClickNum.setVisibility(View.GONE);
                } else if (text.toString().length() == 6) {
                    phone.setErrorEnabled(false);
                    onClickNum.setVisibility(View.VISIBLE);
                } else {
                    num.setErrorEnabled(false);
                    onClickNum.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", "num");
            }
        }));

        id.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "유효한 아이디입니다", Toast.LENGTH_SHORT).show());

        onClickPhone.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다", Toast.LENGTH_SHORT).show();
            num.setVisibility(View.VISIBLE);
        });

        onClickNum.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "임시 비밀번호가 전송되었습니다", Toast.LENGTH_SHORT).show());

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginMain.class);
            startActivity(intent);
        });

        btnFindID.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginFindID.class);
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