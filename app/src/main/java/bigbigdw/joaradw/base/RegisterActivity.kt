package bigbigdw.joaradw.base

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import bigbigdw.joaradw.R
import android.text.Editable
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.Button
import bigbigdw.joaradw.login.ActivityLogin

open class RegisterActivity : AppCompatActivity() {
    fun idCheck(text: CharSequence, inputID: TextInputLayout) {
        if (text.toString() == "kdw0310@ajou.ac.kr") {
            inputID.error = getString(R.string.Register_IDCheck_Message)
            inputID.isErrorEnabled = true
        } else if (text.toString().length < 10) {
            inputID.error = getString(R.string.FindID_IDInvalid)
            inputID.isErrorEnabled = true
        } else {
            inputID.isErrorEnabled = false
        }
    }

    fun pwCheck(text: CharSequence, initPW: Editable, inputPWcheck: TextInputLayout) {
        if (text.toString() == initPW.toString()) {
            inputPWcheck.isErrorEnabled = true
            inputPWcheck.isErrorEnabled = false
        } else {
            inputPWcheck.error = getString(R.string.Register_PWCheck_Message)
        }
    }

    fun phoneCheck(text: CharSequence, phone: TextInputLayout, onClickPhone: Button) {
        if (text.toString().length < 10) {
            phone.error = getString(R.string.Find_InputPhone_NO)
            phone.isErrorEnabled = true
            onClickPhone.visibility = View.GONE
        } else if (text.toString().length == 11) {
            phone.isErrorEnabled = false
            onClickPhone.visibility = View.VISIBLE
        } else {
            phone.isErrorEnabled = false
            onClickPhone.visibility = View.GONE
        }
    }

    fun numCheck(
        text: CharSequence,
        num: TextInputLayout,
        phone: TextInputLayout,
        onClickNum: Button
    ) {
        if (text.toString().length < 5) {
            num.error = getString(R.string.Find_InputNum_NO)
            num.isErrorEnabled = true
            onClickNum.visibility = View.GONE
        } else if (text.toString().length == 6) {
            phone.isErrorEnabled = false
            onClickNum.visibility = View.VISIBLE
        } else {
            num.isErrorEnabled = false
            onClickNum.visibility = View.GONE
        }
    }

    fun sendNumMsg(num: TextInputLayout) {
        Toast.makeText(applicationContext, "인증번호가 전송되었습니다", Toast.LENGTH_SHORT).show()
        num.visibility = View.VISIBLE
    }

    fun goToMain() {
        val intent = Intent(applicationContext, ActivityLogin::class.java)
        startActivity(intent)
    }
}