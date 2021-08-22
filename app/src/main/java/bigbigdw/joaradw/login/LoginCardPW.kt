package bigbigdw.joaradw.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bigbigdw.joaradw.R
import android.text.TextWatcher
import android.view.View.OnFocusChangeListener
import android.text.Editable
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.Toolbar
import bigbigdw.joaradw.login.LoginMain
import java.util.*

class LoginCardPW : AppCompatActivity() {
    var pwFirst: EditText? = null
    var pwSecond: EditText? = null
    var pwText1: TextView? = null
    var pwText2: TextView? = null
    var onClickDone: Button? = null
    var onClickNext: Button? = null
    var done: LinearLayout? = null
    var before: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_cardpw)
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        pwFirst = findViewById(R.id.pwFirst)
        pwSecond = findViewById(R.id.pwSecond)
        pwText1 = findViewById(R.id.PwText1)
        pwText2 = findViewById(R.id.PwText2)
        done = findViewById(R.id.RegisterDone)
        before = findViewById(R.id.RegisterBefore)
        onClickNext = findViewById(R.id.onClickNext)
        onClickDone = findViewById(R.id.onClickDone)
        setLayout()
    }

    fun setLayout() {
        Objects.requireNonNull(pwFirst)!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged_pwFirst", "pwFirst")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                if (text.length == 6) {
                    pwSecond!!.onFocusChangeListener =
                        OnFocusChangeListener { v: View?, hasFocus: Boolean ->
                            if (hasFocus) {
                                pwText1!!.visibility = View.GONE
                                pwFirst!!.visibility = View.GONE
                                pwText2!!.visibility = View.VISIBLE
                            }
                        }
                }
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged_pwFirst", "pwFirst")
            }
        })
        Objects.requireNonNull(pwSecond)!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged_pwSecond", "pwSecond")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                if (text.length == 6 && text.toString() == pwFirst!!.text.toString()) {
                    onClickNext!!.visibility = View.VISIBLE
                } else if (text.length == 6) {
                    Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                }
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged_pwSecond", "pwSecond")
            }
        })
        pwSecond!!.setOnKeyListener { v: View?, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val imm =
                    this@LoginCardPW.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(pwSecond!!.windowToken, 0)
                return@setOnKeyListener true
            }
            false
        }
        onClickNext!!.setOnClickListener { v: View? ->
            before!!.visibility = View.GONE
            onClickNext!!.visibility = View.GONE
            done!!.visibility = View.VISIBLE
            onClickDone!!.visibility = View.VISIBLE
        }
        onClickDone!!.setOnClickListener { v: View? ->
            Toast.makeText(applicationContext, "조아라에 가입하신 것을 환영합니다!", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, LoginMain::class.java)
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