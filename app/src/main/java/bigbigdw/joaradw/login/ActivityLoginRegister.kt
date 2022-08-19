package bigbigdw.joaradw.login

import bigbigdw.joaradw.base.RegisterActivity
import com.google.android.material.textfield.TextInputLayout
import android.text.Editable
import android.os.Bundle
import bigbigdw.joaradw.R
import android.text.TextWatcher
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import bigbigdw.joaradw.policy.Policy
import java.util.*

class ActivityLoginRegister : RegisterActivity() {
    var inputPW: TextInputLayout? = null
    var inputPWcheck: TextInputLayout? = null
    var inputID: TextInputLayout? = null
    var num: TextInputLayout? = null
    var phone: TextInputLayout? = null
    var initPW: Editable? = null
    var promise1: CheckBox? = null
    var promise2: CheckBox? = null
    var onClickNum: Button? = null
    var onClickPhone: Button? = null
    var btnBack: Button? = null
    var btnContinue: Button? = null
    var phoneCheck: Editable? = null
    var numCheck: Editable? = null
    var pwCheck: Editable? = null
    var policy1: TextView? = null
    var policy2: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        inputID = findViewById(R.id.inputID)
        inputPW = findViewById(R.id.inputPW)
        num = findViewById(R.id.Num)
        onClickNum = findViewById(R.id.onClickNum)
        phone = findViewById(R.id.Phone)
        onClickPhone = findViewById(R.id.onClickPhone)
        inputPWcheck = findViewById(R.id.inputPWcheck)
        initPW = Objects.requireNonNull(inputPW!!.getEditText())!!.text
        promise1 = findViewById(R.id.promise1)
        promise2 = findViewById(R.id.promise2)
        btnBack = findViewById(R.id.Btn_Back)
        btnContinue = findViewById(R.id.Btn_Continue)
        policy1 = findViewById(R.id.PolicyText1)
        policy2 = findViewById(R.id.PolicyText2)
        setLayout()
    }

    fun setLayout() {
        Objects.requireNonNull(inputID!!.editText)!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged_inputID", "inputID")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                idCheck(text, inputID!!)
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged_inputID", "inputID")
            }
        })
        Objects.requireNonNull(inputPWcheck!!.editText)!!
            .addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    text: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.d("beforeTextChanged_inputPWcheck", "inputPWcheck")
                }

                override fun onTextChanged(
                    text: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    pwCheck(text, initPW!!, inputPWcheck!!)
                }

                override fun afterTextChanged(s: Editable) {
                    Log.d("afterTextChanged_inputPWcheck", "inputPWcheck")
                }
            })
        Objects.requireNonNull(phone!!.editText)!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged_phone", "phone")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                phoneCheck(text, phone!!, onClickPhone!!)
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged_phone", "phone")
            }
        })
        Objects.requireNonNull(num!!.editText)!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged_num", "num")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                numCheck(text, num!!, phone!!, onClickNum!!)
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged_num", "num")
            }
        })
        promise1!!.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (promise1!!.isChecked) {
                Toast.makeText(applicationContext, "이용약관에 동의하셨습니다", Toast.LENGTH_SHORT).show()
            }
        }
        promise2!!.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (promise2!!.isChecked) {
                Toast.makeText(applicationContext, "개인정보 및 수집 이용에 동의하셨습니다", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        onClickPhone!!.setOnClickListener { v: View? -> sendNumMsg(num!!) }
        onClickNum!!.setOnClickListener { v: View? ->
            Toast.makeText(
                applicationContext,
                "임시 비밀번호가 전송되었습니다",
                Toast.LENGTH_SHORT
            ).show()
        }
        btnBack!!.setOnClickListener { v: View? ->
            Toast.makeText(applicationContext, "로그인 화면으로 돌아갑니다", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, ActivityLogin::class.java)
            startActivity(intent)
        }
        btnContinue!!.setOnClickListener { v: View? ->
            phoneCheck = Objects.requireNonNull(phone!!.editText)!!.text
            numCheck = Objects.requireNonNull(num!!.editText)!!.text
            pwCheck = Objects.requireNonNull(inputPWcheck!!.editText)!!.text
            if (promise1!!.isChecked && promise2!!.isChecked && numCheck.toString().length != 0 && phoneCheck.toString().length != 0 && pwCheck.toString() == initPW.toString()) {
                Toast.makeText(applicationContext, "결제 비밀번호 설정으로 이동합니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, AcitivityLoginCardPW::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "회원가입이 완료되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        policy1!!.setOnClickListener { v: View? ->
            val intent = Intent(applicationContext, Policy::class.java)
            startActivity(intent)
        }
        policy2!!.setOnClickListener { v: View? ->
            val intent = Intent(applicationContext, Policy::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}