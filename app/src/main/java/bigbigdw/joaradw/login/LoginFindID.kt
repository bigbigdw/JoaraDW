package bigbigdw.joaradw.login

import bigbigdw.joaradw.base.RegisterActivity
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView
import android.os.Bundle
import bigbigdw.joaradw.R
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import bigbigdw.joaradw.login.LoginFindPW
import java.util.*

class LoginFindID : RegisterActivity() {
    var phone: TextInputLayout? = null
    var num: TextInputLayout? = null
    var id: TextView? = null
    var onclickphone: Button? = null
    var onclicknum: Button? = null
    var btnGoBack: Button? = null
    var btnFindPW: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_findid)
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        phone = findViewById(R.id.Phone)
        num = findViewById(R.id.Num)
        id = findViewById(R.id.ID)
        onclicknum = findViewById(R.id.onClickNum)
        onclickphone = findViewById(R.id.onClickPhone)
        btnGoBack = findViewById(R.id.BtnGoBack)
        btnFindPW = findViewById(R.id.BtnFindPW)
        setLayout()
    }

    fun setLayout() {
        Objects.requireNonNull(phone!!.editText)!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged", "phone")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                phoneCheck(text, phone!!, onclickphone!!)
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("LoginFindID", "phone")
            }
        })
        Objects.requireNonNull(num!!.editText)!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged", "num")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                numCheck(text, num!!, phone!!, onclicknum!!)
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged", "num")
            }
        })
        onclickphone!!.setOnClickListener { v: View? -> sendNumMsg(num!!) }
        onclicknum!!.setOnClickListener { v: View? ->
            Toast.makeText(applicationContext, "인증이 완료되었습니다", Toast.LENGTH_SHORT).show()
            id!!.visibility = View.VISIBLE
        }
        btnGoBack!!.setOnClickListener { v: View? -> goToMain() }
        btnFindPW!!.setOnClickListener { v: View? ->
            val intent = Intent(applicationContext, LoginFindPW::class.java)
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