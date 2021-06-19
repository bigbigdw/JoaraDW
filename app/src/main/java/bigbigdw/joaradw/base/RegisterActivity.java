package bigbigdw.joaradw.base;

import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import bigbigdw.joaradw.R;
import bigbigdw.joaradw.login.LoginMain;

public class RegisterActivity extends AppCompatActivity {

    public void idCheck(CharSequence text, TextInputLayout inputID) {
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

    public void pwCheck(CharSequence text, Editable initPW, TextInputLayout inputPWcheck) {
        if (text.toString().equals(initPW.toString())) {
            inputPWcheck.setErrorEnabled(true);
            inputPWcheck.setErrorEnabled(false);
        } else {
            inputPWcheck.setError(getString(R.string.Register_PWCheck_Message));
        }
    }

    public void phoneCheck(CharSequence text, TextInputLayout phone, Button onClickPhone) {
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

    public void numCheck(CharSequence text, TextInputLayout num, TextInputLayout phone,  Button onClickNum) {
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

    public void sendNumMsg(TextInputLayout num) {
        Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다", Toast.LENGTH_SHORT).show();
        num.setVisibility(View.VISIBLE);
    }

    public void goToMain() {
        Intent intent = new Intent(getApplicationContext(), LoginMain.class);
        startActivity(intent);
    }
}
