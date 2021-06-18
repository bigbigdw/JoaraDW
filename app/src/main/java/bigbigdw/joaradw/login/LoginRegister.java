package bigbigdw.joaradw.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import bigbigdw.joaradw.R;
import bigbigdw.joaradw.policy.Policy;

public class LoginRegister extends AppCompatActivity {

    TextInputLayout inputPW;
    TextInputLayout inputPWcheck;
    TextInputLayout inputID;
    TextInputLayout num;
    TextInputLayout phone;
    Editable initPW;
    CheckBox promise1;
    CheckBox promise2;
    Button onClickNum;
    Button onClickPhone;
    Button btnBack;
    Button btnContinue;
    Editable phoneCheck;
    Editable numCheck;
    Editable pwCheck;
    TextView policy1;
    TextView policy2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        inputID = findViewById(R.id.inputID);
        inputPW = findViewById(R.id.inputPW);
        num = findViewById(R.id.Num);
        onClickNum = findViewById(R.id.onClickNum);
        phone = findViewById(R.id.Phone);
        onClickPhone = findViewById(R.id.onClickPhone);
        inputPWcheck = findViewById(R.id.inputPWcheck);
        initPW = Objects.requireNonNull(inputPW.getEditText()).getText();
        promise1 = findViewById(R.id.promise1);
        promise2 = findViewById(R.id.promise2);
        btnBack = findViewById(R.id.Btn_Back);
        btnContinue = findViewById(R.id.Btn_Continue);
        policy1 = findViewById(R.id.PolicyText1);
        policy2 = findViewById(R.id.PolicyText2);


        Objects.requireNonNull(inputID.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged_inputID", "inputID");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                idCheck(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged_inputID", "inputID");
            }
        });

        Objects.requireNonNull(inputPWcheck.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged_inputPWcheck", "inputPWcheck");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.toString().equals(initPW.toString())) {
                    inputPWcheck.setErrorEnabled(true);
                    inputPWcheck.setErrorEnabled(false);
                } else {
                    inputPWcheck.setError(getString(R.string.Register_PWCheck_Message));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged_inputPWcheck", "inputPWcheck");
            }
        });

        Objects.requireNonNull(phone.getEditText()).addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged_phone", "phone");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                phoneCheck(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged_phone", "phone");
            }
        }));

        Objects.requireNonNull(num.getEditText()).addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                Log.d("beforeTextChanged_num", "num");
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                numCheck(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged_num", "num");
            }
        }));

        promise1.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if ( promise1.isChecked()) {
                        Toast.makeText(getApplicationContext(), "이용약관에 동의하셨습니다", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        promise2.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    if ( promise2.isChecked()) {
                        Toast.makeText(getApplicationContext(), "개인정보 및 수집 이용에 동의하셨습니다", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        onClickPhone.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다", Toast.LENGTH_SHORT).show();
            num.setVisibility(View.VISIBLE);
        });

        onClickNum.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "임시 비밀번호가 전송되었습니다", Toast.LENGTH_SHORT).show());

        btnBack.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "로그인 화면으로 돌아갑니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginMain.class);
            startActivity(intent);
        });

        btnContinue.setOnClickListener(v -> {
            phoneCheck = Objects.requireNonNull(phone.getEditText()).getText();
            numCheck = Objects.requireNonNull(num.getEditText()).getText();
            pwCheck = Objects.requireNonNull(inputPWcheck.getEditText()).getText();

            Intent intent = new Intent(getApplicationContext(), LoginCardPW.class);
            startActivity(intent);

//            if(promise1.isChecked() && promise2.isChecked() && numCheck.toString().length() != 0 &&  phoneCheck.toString().length() != 0 && pwCheck.toString().equals(initPW.toString())){
//                Toast.makeText(getApplicationContext(), "결제 비밀번호 설정으로 이동합니다.", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), LoginCardPW.class);
//                startActivity(intent);
//            } else {
//                Toast.makeText(getApplicationContext(), "회원가입이 완료되지 않았습니다.", Toast.LENGTH_SHORT).show();
//            }
        });

        policy1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Policy.class);
            startActivity(intent);
        });

        policy2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Policy.class);
            startActivity(intent);
        });
    }

    public void idCheck(CharSequence text) {
        if (text.toString().equals("kdw0310@ajou.ac.kr")) {
            inputID.setError(getString(R.string.Register_IDCheck_Message));
            inputID.setErrorEnabled(true);
        }
        else if (text.toString().length() < 10) {
            inputID.setError(getString(R.string.FindID_IDInvalid));
            inputID.setErrorEnabled(true);
        }
        else {
            inputID.setErrorEnabled(false);
        }
    }

    public void phoneCheck(CharSequence text) {
        if (text.toString().length() < 10) {
            phone.setError(getString(R.string.Find_InputPhone_NO));
            phone.setErrorEnabled(true);
            onClickPhone.setVisibility(View.GONE);
        } else if(text.toString().length() == 11){
            phone.setErrorEnabled(false);
            onClickPhone.setVisibility(View.VISIBLE);
        } else {
            phone.setErrorEnabled(false);
            onClickPhone.setVisibility(View.GONE);
        }
    }

    public void numCheck(CharSequence text) {
        if (text.toString().length() < 5) {
            num.setError(getString(R.string.Find_InputNum_NO));
            num.setErrorEnabled(true);
            onClickNum.setVisibility(View.GONE);
        }
        else if(text.toString().length() == 6){
            phone.setErrorEnabled(false);
            onClickNum.setVisibility(View.VISIBLE);
        }
        else {
            num.setErrorEnabled(false);
            onClickNum.setVisibility(View.GONE);
        }
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
