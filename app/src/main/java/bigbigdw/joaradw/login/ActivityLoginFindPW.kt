package bigbigdw.joaradw.login

import bigbigdw.joaradw.base.RegisterActivity
import com.google.android.material.textfield.TextInputLayout
import android.os.Bundle
import bigbigdw.joaradw.R
import android.text.TextWatcher
import android.text.Editable
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import java.util.*

class ActivityLoginFindPW : RegisterActivity() {
    var phone: TextInputLayout? = null
    var num: TextInputLayout? = null
    var id: TextInputLayout? = null
    var onClickID: Button? = null
    var onClickPhone: Button? = null
    var onClickNum: Button? = null
    var btnBack: Button? = null
    var btnFindID: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_findpw)
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        phone = findViewById(R.id.Phone)
        num = findViewById(R.id.Num)
        id = findViewById(R.id.ID)
        onClickID = findViewById(R.id.onClickID)
        onClickPhone = findViewById(R.id.onClickPhone)
        onClickNum = findViewById(R.id.onClickNum)
        btnBack = findViewById(R.id.Btn_Back)
        btnFindID = findViewById(R.id.Btn_FindID)
        setLayout()
    }

    fun setLayout() {
        Objects.requireNonNull(id!!.editText)!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged_id", "id")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                if (text.toString().length < 10) {
                    id!!.error = getString(R.string.FindID_IDInvalid)
                    id!!.isErrorEnabled = true
                    onClickID!!.visibility = View.GONE
                } else {
                    id!!.isErrorEnabled = false
                    onClickID!!.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged_id", "id")
            }
        })
        Objects.requireNonNull(phone!!.editText)!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged", "phone")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                phoneCheck(text, phone!!, onClickPhone!!)
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged", "phone")
            }
        })
        Objects.requireNonNull(num!!.editText)!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged", "num")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                numCheck(text, num!!, phone!!, onClickNum!!)
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged", "num")
            }
        })
        id!!.setOnClickListener { v: View? ->
            Toast.makeText(
                applicationContext,
                "유효한 아이디입니다",
                Toast.LENGTH_SHORT
            ).show()
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
            val intent = Intent(applicationContext, ActivityLoginMain::class.java)
            startActivity(intent)
        }
        btnFindID!!.setOnClickListener { v: View? -> goToMain() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}