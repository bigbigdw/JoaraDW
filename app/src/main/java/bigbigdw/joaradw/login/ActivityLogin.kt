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
import androidx.recyclerview.widget.LinearLayoutManager
import bigbigdw.joaradw.BuildConfig
import bigbigdw.joaradw.databinding.ActivityLoginBinding
import bigbigdw.joaradw.databinding.ActivitySplashBinding
import bigbigdw.joaradw.novel.ActivityNovel
import bigbigdw.joaradw.util.LoginResult
import bigbigdw.joaradw.util.Param
import bigbigdw.joaradw.writer.ActivityWriter
import com.example.moavara.Retrofit.JoaraEventsResult
import com.example.moavara.Retrofit.RetrofitDataListener
import com.example.moavara.Retrofit.RetrofitJoara
import com.example.moavara.Search.AnayzeData
import com.example.moavara.Search.EventDetailData
import com.example.moavara.Search.UserInfo
import com.example.moavara.Soon.Event.ActivityEventDetail
import com.example.moavara.Soon.Event.AdapterEventDetail
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class ActivityLogin : AppCompatActivity() {
    var token: String? = null
    var queue: RequestQueue? = null
    var loginFailMsg = "로그인에 실패하였습니다"
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        queue = Volley.newRequestQueue(this)
        setLayout()
    }

    fun setLayout() {
        
        with(binding){
            Objects.requireNonNull(tviewID.editText)?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                    Log.d("tviewID", "beforeTextChanged")
                }

                override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                    Log.d("tviewID", "onTextChanged")
                }

                override fun afterTextChanged(s: Editable) {
                    if (s.toString().isEmpty()) {
                        tviewID.error = getString(R.string.Login_EmptyID)
                        tviewID.isErrorEnabled = true
                    } else {
                        tviewID.isErrorEnabled = false
                    }
                }
            })
            Objects.requireNonNull(tviewPW.editText)?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                    Log.d("tviewPW", "beforeTextChanged")
                }

                override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                    Log.d("tviewPW", "onTextChanged")
                }

                override fun afterTextChanged(s: Editable) {
                    if (s.toString().isEmpty()) {
                        tviewPW.error = getString(R.string.Login_EmptyPW)
                        tviewPW.isErrorEnabled = true
                    } else {
                        tviewPW.isErrorEnabled = false
                    }
                }
            })

            loginBtn.setOnClickListener { v: View? ->

//                if(BuildConfig.IS_LABS){
//                    FirebaseDatabase.getInstance().reference.child("Login").addListenerForSingleValueEvent(object :
//                        ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            val group: UserInfo? =
//                                dataSnapshot.getValue(UserInfo::class.java)
//
//                            if (group != null) {
//                                Login(group.id, group.pw)
//                            }
//                        }
//
//                        override fun onCancelled(databaseError: DatabaseError) {}
//                    })
//                } else {
//                    val idCheck = Objects.requireNonNull(tviewID.editText)?.text.toString()
//                    val pwCheck = Objects.requireNonNull(tviewPW.editText)?.text.toString()
//
//                    Login(idCheck, pwCheck)
//                }

                val idCheck = Objects.requireNonNull(tviewID.editText)?.text.toString()
                val pwCheck = Objects.requireNonNull(tviewPW.editText)?.text.toString()

                Login(idCheck, pwCheck)
            }

            tviewFindID.setOnClickListener {
                Toast.makeText(applicationContext, "아이디 찾기로 이동합니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, ActivityLoginFindID::class.java)
                startActivity(intent)
            }

            tviewFindPW.setOnClickListener {
                Toast.makeText(applicationContext, "비밀번호 찾기로 이동합니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, ActivityLoginFindPW::class.java)
                startActivity(intent)
            }

            registerBtn.setOnClickListener {
                Toast.makeText(applicationContext, "회원가입 페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, ActivityLoginRegister::class.java)
                startActivity(intent)
            }

            iviewLogo.setOnClickListener {
                val intent = Intent(applicationContext, ActivityNovel::class.java)
                startActivity(intent)
            }
        }
    }

    fun Login(idCheck : String, pwCheck : String ){
        RetrofitLogin.postLogin(idCheck,pwCheck, this@ActivityLogin)!!.enqueue(object : Callback<LoginResult?> {
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
                        val grade = it.user?.grade

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
                            savePreferences("STATUS", status)
                            savePreferences("PROFILEIMG", profile!!)
                            savePreferences("GRADE", grade!!)

//                            if (BuildConfig.IS_WRITER) {
//                                //작품관리 진입
//                                val intent = Intent(applicationContext, ActivityWriter::class.java)
//                                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//                                startActivityIfNeeded(intent, 0)
//                                finish()
//                            } else if (BuildConfig.IS_LABS) {
//                                //작품관리 진입
//                                val intent =
//                                    Intent(applicationContext, ActivityEventDetail::class.java)
//                                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//                                startActivityIfNeeded(intent, 0)
//                                finish()
//                            } else {
//                                finish()
//                            }

                            finish()

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

    override fun onBackPressed() {
//        if (BuildConfig.IS_WRITER) {
//            finish()
//        } else {
//            val intent = Intent(applicationContext, ActivityNovel::class.java)
//            intent.putExtra("IsFirstPage", false)
//            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//            startActivityIfNeeded(intent, 0)
//            finish()
//        }

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