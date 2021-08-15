package bigbigdw.joaradw.login

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.android.volley.RequestQueue
import android.os.Bundle
import bigbigdw.joaradw.R
import com.android.volley.toolbox.Volley
import android.text.TextWatcher
import android.text.Editable
import bigbigdw.joaradw.etc.HELPER
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import bigbigdw.joaradw.login.LoginMain
import bigbigdw.joaradw.JOARADW
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.*
import bigbigdw.joaradw.joara_post.AdapterPostList
import bigbigdw.joaradw.joara_post.PostListData
import bigbigdw.joaradw.joara_post.PostService
import bigbigdw.joaradw.joara_post.PostTabResult
import org.json.JSONException
import com.android.volley.VolleyError
import bigbigdw.joaradw.login.LoginFindID
import bigbigdw.joaradw.login.LoginFindPW
import bigbigdw.joaradw.login.LoginRegister
import bigbigdw.joaradw.main.Main
import bigbigdw.joaradw.util.LoginResult
import bigbigdw.joaradw.util.LoginService
import com.android.volley.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.util.*

class LoginMain : AppCompatActivity() {
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
        setContentView(R.layout.login)
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
                if (s.toString().length < 1) {
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
                if (s.toString().length < 1) {
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

//            if (idCheck.length > 1 && pwCheck.length > 1) {
//                val stringRequest: StringRequest = object :
//                    StringRequest(Method.POST, resultURL, Response.Listener { response: String? ->
//                        try {
//                            val reader = JSONObject(response)
//                            val userInfo = reader.getJSONObject("user")
//                            val userName = userInfo.getString("nickname")
//                            val dir = "/"
//                            val filename = dataDir.toString() + dir
//                            writeJson(filename, response)
//                            token = userInfo.getString("token")
//                            val myApp = applicationContext as JOARADW
//                            myApp.token = token
//                            Toast.makeText(
//                                applicationContext,
//                                "환영합니다!" + " " + userName + "님!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            val intent = Intent(applicationContext, Main::class.java)
//                            intent.putExtra("IsFirstPage", false)
//                            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//                            startActivityIfNeeded(intent, 0)
//                            finish()
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//                            Toast.makeText(applicationContext, loginFailMsg, Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }, Response.ErrorListener { error: VolleyError? ->
//                        Toast.makeText(
//                            applicationContext, loginFailMsg, Toast.LENGTH_SHORT
//                        ).show()
//                    }) {
//                    override fun getParams(): Map<String, String>? {
//                        val params: MutableMap<String, String> = HashMap()
//                        params["member_id"] = idCheck
//                        params["passwd"] = pwCheck
//                        params["api_key"] = HELPER.API_KEY
//                        params["ver"] = HELPER.VER
//                        params["device"] = HELPER.DEVICE
//                        params["deviceuid"] = HELPER.DEVICE_ID
//                        params["devicetoken"] = HELPER.DEVICE_TOKEN
//                        return@setOnClickListener params
//                    }
//                }
//                queue!!.add(stringRequest)
//            } else {
//                Toast.makeText(applicationContext, loginFailMsg, Toast.LENGTH_SHORT).show()
//            }


            val call = Retrofit.Builder()
                .baseUrl(HELPER.API)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(LoginService::class.java)
                .postRetrofit(
                    idCheck,
                    pwCheck,
                    HELPER.API_KEY,
                    HELPER.VER,
                    HELPER.DEVICE,
                    HELPER.DEVICE_ID,
                    HELPER.DEVICE_TOKEN
                )

            call!!.enqueue(object : Callback<LoginResult?> {
                override fun onResponse(
                    call: Call<LoginResult?>,
                    response: retrofit2.Response<LoginResult?>
                ) {
                    if (response.isSuccessful) {

                        response.body()?.let { it ->
                            val nickname = it.user?.nickname
                            val token = it.user?.token

                            Toast.makeText(applicationContext,"환영합니다!" + " " + nickname + "님!", Toast.LENGTH_SHORT).show()

                            savePreferences("LOGIN_TOKEN", token!!)
                            savePreferences("LOGIN_NICKNAME", nickname!!)

                            val intent = Intent(applicationContext, Main::class.java)
                            intent.putExtra("IsFirstPage", false)
                            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                            startActivityIfNeeded(intent, 0)
                            finish()
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
            val intent = Intent(applicationContext, LoginFindID::class.java)
            startActivity(intent)
        }

        loginMainFIndPW!!.setOnClickListener { v: View? ->
            Toast.makeText(applicationContext, "비밀번호 찾기로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, LoginFindPW::class.java)
            startActivity(intent)
        }

        registerBtn!!.setOnClickListener { v: View? ->
            Toast.makeText(applicationContext, "회원가입 페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, LoginRegister::class.java)
            startActivity(intent)
        }

        logo!!.setOnClickListener { v: View? ->
            val intent = Intent(applicationContext, Main::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, Main::class.java)
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