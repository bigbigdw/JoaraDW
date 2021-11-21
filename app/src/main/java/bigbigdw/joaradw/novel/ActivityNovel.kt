package bigbigdw.joaradw.novel

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import bigbigdw.joaradw.R
import bigbigdw.joaradw.login.ActivityLoginMain
import bigbigdw.joaradw.test.ActivityTest
import bigbigdw.joaradw.util.CheckTokenResult
import bigbigdw.joaradw.util.IndexAPIResult
import bigbigdw.joaradw.util.LogoutResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityNovel : AppCompatActivity() {
    private var appBarConfiguration: AppBarConfiguration? = null
    private var dialogBanner: DialogBanner? = null
    var drawerLogout: LinearLayout? = null
    var drawerLogin: LinearLayout? = null
    var viewMana: TextView? = null
    var coupon: TextView? = null
    var viewCash: TextView? = null
    var viewManuscriptCoupon: TextView? = null
    var viewSupportCoupon: TextView? = null
    var userName: TextView? = null
    var btnLogout: ImageView? = null
    var homeImg: ImageView? = null
    var navController: NavController? = null
    var navigationView: NavigationView? = null
    var navHeaderView: View? = null
    var drawer: DrawerLayout? = null
    private var mContext: Context? = null

    val MenuList: MutableList<String> = ArrayList()
    val MenuListPosition: MutableList<String> = ArrayList()

    override fun onResume() {
        super.onResume()
        loginCheck(
            getSharedPreferences("LOGIN", MODE_PRIVATE).getString("TOKEN", ""),
            drawerLogin,
            drawerLogout,
            navigationView
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.component_novel)

        mContext = this

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_main)
        homeImg = findViewById(R.id.HomeImg)

        navHeaderView = navigationView!!.getHeaderView(0)
        drawerLogout = navHeaderView!!.findViewById(R.id.Drawer_LogOut)
        drawerLogin = navHeaderView!!.findViewById(R.id.Drawer_LogIn)
        viewMana = navHeaderView!!.findViewById(R.id.Mana)
        coupon = navHeaderView!!.findViewById(R.id.Coupon)
        viewCash = navHeaderView!!.findViewById(R.id.Cash)
        viewManuscriptCoupon = navHeaderView!!.findViewById(R.id.Manuscript_Coupon)
        viewSupportCoupon = navHeaderView!!.findViewById(R.id.Support_Coupon)
        userName = navHeaderView!!.findViewById(R.id.UserName)
        btnLogout = navHeaderView!!.findViewById(R.id.Btn_Logout)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_novel)

        setLayout()
    }

    fun setLayout() {

        drawerLogout!!.setOnClickListener { v: View? ->
            Toast.makeText(applicationContext, "로그인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, ActivityLoginMain::class.java)
            startActivity(intent)
        }

        btnLogout!!.setOnClickListener {
            onClickLogout()
        }

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.Fragment_Novel
        ).setOpenableLayout(drawer).build()

        NavigationUI.setupActionBarWithNavController(this, navController!!, appBarConfiguration!!)
        NavigationUI.setupWithNavController(navigationView!!, navController!!)
        val navView = findViewById<BottomNavigationView>(R.id.nav_bottom)
        NavigationUI.setupWithNavController(navView, navController!!)

        homeImg!!.setOnClickListener { v: View? ->
            val activityTest = Intent(applicationContext, ActivityTest::class.java)
            startActivity(activityTest)
        }

        navController!!.addOnDestinationChangedListener { _: NavController?, destination: NavDestination, _: Bundle? ->
            //바텀 내비게이션 바 비활성화
            if (destination.id == R.id.Fragment_Novel || destination.id == R.id.Joara_Post_List || destination.id == R.id.Fragment_Fav || destination.id == R.id.Fragment_History) {
                setCheckable(navView, false)
                navView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED
            } else {
                setCheckable(navView, true)
                navView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_AUTO
            }
        }

        getIndexAPI()
    }

    private fun getIndexAPI() {

        val menuVer = getSharedPreferences("INDEX_API", MODE_PRIVATE).getString("MENU_VER", "0")

        RetrofitNovel.getIndexAPI(menuVer, this)!!.enqueue(object : Callback<IndexAPIResult?> {
            override fun onResponse(
                call: Call<IndexAPIResult?>,
                response: Response<IndexAPIResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val banner = it.banner
                        val mainMenu = it.mainMenu

                        //배너 관련
                        if (banner != null) {
                            for (i in banner.indices) {
                                if (banner[i] != "") {
                                    dialogBanner = DialogBanner(
                                        mContext!!,
                                        btnLeftListener,
                                        btnRightListener,
                                        banner[i]
                                    )
                                    dialogBanner!!.show()
                                }

                            }
                        }

                        //버전 관련
                        if (mainMenu != null) {
                            for (i in mainMenu.indices) {

                                if (mainMenu[i].MainTab != null && mainMenu[i].TabInfo != null) {
                                    val MainTab = mainMenu[i].MainTab
                                    val tabname = mainMenu[i].TabInfo!!.tabname
                                    if (tabname.equals("최신작품")) {
                                        for (j in MainTab!!.indices) {
                                            MenuList.add(MainTab[j].title!!)
                                            MenuListPosition.add(MainTab[j].position!!)
                                        }
                                        val editor =
                                            getSharedPreferences("MAIN_MENU", MODE_PRIVATE).edit()
                                        editor.putString("NEW_TITLE", MenuList.toString())
                                        editor.apply()
                                    }
                                }

                                if (mainMenu[i].menuVer != null) {
                                    savePreferences("MENU_VER", mainMenu[i].menuVer!!)
                                }
                            }
                        }

                    }
                }
            }

            override fun onFailure(call: Call<IndexAPIResult?>, t: Throwable) {
                Log.d("Novel: onResponse", "실패")
            }
        })
    }

    private fun onClickLogout() {

        val token = getSharedPreferences("LOGIN", MODE_PRIVATE).getString("TOKEN", "").toString()

        RetrofitNovel.onClickLogout(token, this)!!.enqueue(object : Callback<LogoutResult?> {
            override fun onResponse(call: Call<LogoutResult?>, response: Response<LogoutResult?>) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val status = it.status

                        hideItem(navigationView, status.equals("1"))

                        if (status.equals("1")) {
                            deleteSignedInfo()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LogoutResult?>, t: Throwable) {
                Log.d("Splash: onResponse", "실패")
            }
        })
    }

    //로그인 체크
    private fun loginCheck(
        usertoken: String?,
        drawerLogIn: LinearLayout?,
        drawerLogOut: LinearLayout?,
        navigationView: NavigationView?
    ) {

        RetrofitNovel.loginCheck(usertoken, this)!!.enqueue(object : Callback<CheckTokenResult?> {
            override fun onResponse(
                call: Call<CheckTokenResult?>,
                response: Response<CheckTokenResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val status = it.status
                        hideItem(navigationView, status == 1)

                        if (status == 1) {
                            drawerLogOut!!.visibility = View.GONE
                            drawerLogIn!!.visibility = View.VISIBLE
                            viewMana!!.text =
                                getSharedPreferences("LOGIN", MODE_PRIVATE).getString("MANA", "마나")
                            coupon!!.text = getSharedPreferences("LOGIN", MODE_PRIVATE).getString(
                                "EXPIRECASH",
                                "이용권"
                            )
                            viewCash!!.text =
                                getSharedPreferences("LOGIN", MODE_PRIVATE).getString("CASH", "딱지")
                            userName!!.text = getSharedPreferences("LOGIN", MODE_PRIVATE).getString(
                                "NICKNAME",
                                "NICKNAME"
                            )
                            viewManuscriptCoupon!!.text = getSharedPreferences(
                                "LOGIN",
                                MODE_PRIVATE
                            ).getString("MANUSCRIPTCOUPON", "후원쿠폰")
                            viewSupportCoupon!!.text = getSharedPreferences(
                                "LOGIN",
                                MODE_PRIVATE
                            ).getString("SUPPORTCOUPON", "원고료쿠폰")
                        } else {
                            drawerLogOut!!.visibility = View.VISIBLE
                            drawerLogIn!!.visibility = View.GONE

                        }
                    }
                }
            }

            override fun onFailure(call: Call<CheckTokenResult?>, t: Throwable) {
                Log.d("Novel: onResponse", "실패")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.novel_menu, menu)
        return true
    }

    private fun hideItem(navigationView: NavigationView?, check: Boolean) {
        val navMenu = navigationView!!.menu
        navMenu.findItem(R.id.Menu_Logined).isVisible = check
    }

    override fun onSupportNavigateUp(): Boolean {
        val navControllerUp = Navigation.findNavController(this, R.id.nav_host_fragment_novel)
        return (NavigationUI.navigateUp(navControllerUp, appBarConfiguration!!)
                || super.onSupportNavigateUp())
    }

    private val btnLeftListener = View.OnClickListener { v: View? -> dialogBanner!!.dismiss() }
    private val btnRightListener = View.OnClickListener { v: View? -> dialogBanner!!.dismiss() }

    //로그아웃
    fun deleteSignedInfo() {
        val alBuilder = AlertDialog.Builder(this)
        alBuilder.setMessage("로그아웃하시겠습니까?")
        alBuilder.setPositiveButton("예") { _: DialogInterface?, _: Int ->
            val editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit()
            editor.clear()
            editor.apply()

            Toast.makeText(applicationContext, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, ActivityNovel::class.java)
            startActivity(intent)
            finish()
        }
        alBuilder.setNegativeButton("아니오") { _: DialogInterface?, _: Int -> }
        alBuilder.show()
    }

    companion object {
        fun setCheckable(navView: BottomNavigationView, checkable: Boolean) {
            val menu = navView.menu
            for (i in 0 until menu.size()) {
                menu.getItem(i).isCheckable = checkable
            }
        }
    }

    fun savePreferences(value: String, token: String) {
        val pref = getSharedPreferences("INDEX_API", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(value, token)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = getSharedPreferences("BANNER_HTML", MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }
}