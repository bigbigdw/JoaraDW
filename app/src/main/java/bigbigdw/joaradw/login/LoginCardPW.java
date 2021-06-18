package bigbigdw.joaradw.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import bigbigdw.joaradw.R;

public class LoginCardPW extends AppCompatActivity {

    EditText pwFirst;
    EditText pwSecond;
    TextView pwText1;
    TextView pwText2;
    Button onClickDone;
    Button onClickNext;
    LinearLayout done;
    LinearLayout before;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_cardpw);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        pwFirst = findViewById(R.id.pwFirst);
        pwSecond = findViewById(R.id.pwSecond);
        pwText1 = findViewById(R.id.PwText1);
        pwText2 = findViewById(R.id.PwText2);
        done = findViewById(R.id.RegisterDone);
        before = findViewById(R.id.RegisterBefore);
        onClickNext = findViewById(R.id.onClickNext);
        onClickDone =  findViewById(R.id.onClickDone);

        Objects.requireNonNull(pwFirst).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged_pwFirst", "pwFirst");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.length() == 6) {
                    pwSecond.setOnFocusChangeListener((v, hasFocus) -> {
                        if (hasFocus) {
                            pwText1.setVisibility(View.GONE);
                            pwFirst.setVisibility(View.GONE);
                            pwText2.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged_pwFirst", "pwFirst");
            }
        });

        Objects.requireNonNull(pwSecond).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged_pwSecond", "pwSecond");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.length() == 6 && text.toString().equals(pwFirst.getText().toString())) {
                    onClickNext.setVisibility(View.VISIBLE);
                } else if(text.length() == 6) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged_pwSecond", "pwSecond");
            }
        });

        pwSecond.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                InputMethodManager imm = (InputMethodManager) LoginCardPW.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pwSecond.getWindowToken(), 0);
                return true;
            }
            return false;
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

    public void onClickNext(View v) {
        before.setVisibility(View.GONE);
        onClickNext.setVisibility(View.GONE);
        done.setVisibility(View.VISIBLE);
        onClickDone.setVisibility(View.VISIBLE);
    }

    public void onClickDone(View v) {
        Toast.makeText(getApplicationContext(), "조아라에 가입하신 것을 환영합니다!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), LoginMain.class);
        startActivity(intent);
    }

}
