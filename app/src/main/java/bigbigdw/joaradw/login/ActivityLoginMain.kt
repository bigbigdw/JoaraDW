package bigbigdw.joaradw.login

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.android.volley.RequestQueue
import android.os.Bundle
import bigbigdw.joaradw.R
import com.android.volley.toolbox.Volley
import android.text.TextWatcher
import android.text.Editable
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import bigbigdw.joaradw.BuildConfig
import bigbigdw.joaradw.novel.ActivityNovel
import bigbigdw.joaradw.util.LoginResult
import bigbigdw.joaradw.writer.ActivityWriter
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class ActivityLoginMain : AppCompatActivity() {
    var idtext: TextInputLayout? = null
    var pwtext: TextInputLayout? = null
    var loginMainFindID: TextView? = null
    var loginMainFIndPW: TextView? = null
    var logo: ImageView? = null
    var queue: RequestQueue? = null
    var loginBtn: Button? = null
    var registerBtn: Button? = null
    var token: String? = null
    var loginFailMsg = "로그인에 실패하였습니다"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        idtext = findViewById(R.id.IDtext)
        pwtext = findViewById(R.id.PWtext)
        loginBtn = findViewById(R.id.LoginBtn)
        queue = Volley.newRequestQueue(this)
        logo = findViewById(R.id.LOGO)
        loginMainFindID = findViewById(R.id.LoginMain_FindID)
        loginMainFIndPW = findViewById(R.id.LoginMain_FIndPW)
        registerBtn = findViewById(R.id.RegisterBtn)
        setLayout()
    }

    fun setLayout() {
        Objects.requireNonNull(idtext!!.editText)?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("idtext", "beforeTextChanged")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                Log.d("idtext", "onTextChanged")
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().isEmpty()) {
                    idtext!!.error = getString(R.string.Login_EmptyID)
                    idtext!!.isErrorEnabled = true
                } else {
                    idtext!!.isErrorEnabled = false
                }
            }
        })
        Objects.requireNonNull(pwtext!!.editText)?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("pwtext", "beforeTextChanged")
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                Log.d("pwtext", "onTextChanged")
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().isEmpty()) {
                    pwtext!!.error = getString(R.string.Login_EmptyPW)
                    pwtext!!.isErrorEnabled = true
                } else {
                    pwtext!!.isErrorEnabled = false
                }
            }
        })
        loginBtn!!.setOnClickListener { v: View? ->
            val idCheck = Objects.requireNonNull(idtext!!.editText)?.text.toString()
            val pwCheck = Objects.requireNonNull(pwtext!!.editText)?.text.toString()

            RetrofitLogin.postLogin(idCheck,pwCheck, this)!!.enqueue(object : Callback<LoginResult?> {
                override fun onResponse(
                    call: Call<LoginResult?>,
                    response: retrofit2.Response<LoginResult?>
                ) {
                    if (response.isSuccessful) {

                        response.body()?.let { it ->
                            val status = it.status
                            val message = it.message
                            val nickname = it.user?.nickname
                            val token = it.user?.token
                            val mana = it.user?.mana
                            val expireCash = it.user?.expireCash
                            val cash = it.user?.cash
                            val manuscriptCoupon = it.user?.manuscriptCoupon
                            val supportCoupon = it.user?.supportCoupon
                            val memberId = it.user?.memberId
                            val profile = it.user?.profile

                            if(status.equals("1")){
                                Toast.makeText(applicationContext,"환영합니다!" + " " + nickname + "님!", Toast.LENGTH_SHORT).show()

                                savePreferences("TOKEN", token!!)
                                savePreferences("NICKNAME", nickname!!)
                                savePreferences("MANA", mana!!)
                                savePreferences("EXPIRECASH", expireCash!!)
                                savePreferences("CASH", cash!!)
                                savePreferences("MANUSCRIPTCOUPON", manuscriptCoupon!!)
                                savePreferences("SUPPORTCOUPON", supportCoupon!!)
                                savePreferences("MEMBERID", memberId!!)
                                savePreferences("STATUS", status!!)
                                savePreferences("PROFILEIMG", profile!!)

                                if (BuildConfig.IS_WRTIER) {
                                    //작품관리 진입
                                    val intent = Intent(applicationContext, ActivityWriter::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                                    startActivityIfNeeded(intent, 0)
                                    finish()
                                } else {
                                    finish()
                                }

                            } else {
                                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(applicationContext, loginFailMsg, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResult?>, t: Throwable) {
                    Toast.makeText(applicationContext, loginFailMsg, Toast.LENGTH_SHORT).show()
                }
            })

        }

        loginMainFindID!!.setOnClickListener { v: View? ->
            Toast.makeText(applicationContext, "아이디 찾기로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, ActivityLoginFindID::class.java)
            startActivity(intent)
        }

        loginMainFIndPW!!.setOnClickListener { v: View? ->
            Toast.makeText(applicationContext, "비밀번호 찾기로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, ActivityLoginFindPW::class.java)
            startActivity(intent)
        }

        registerBtn!!.setOnClickListener { v: View? ->
            Toast.makeText(applicationContext, "회원가입 페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, ActivityLoginRegister::class.java)
            startActivity(intent)
        }

        logo!!.setOnClickListener { v: View? ->
            val intent = Intent(applicationContext, ActivityNovel::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, ActivityNovel::class.java)
        intent.putExtra("IsFirstPage", false)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivityIfNeeded(intent, 0)
        finish()
    }

    fun savePreferences(value: String, token: String) {
        val pref = getSharedPreferences("LOGIN", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(value, token)
        editor.apply()
    }
}