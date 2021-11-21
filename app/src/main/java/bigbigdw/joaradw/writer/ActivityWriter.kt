package bigbigdw.joaradw.writer

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
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
import bigbigdw.joaradw.novel.ActivityNovel
import bigbigdw.joaradw.novel.RetrofitNovel
import bigbigdw.joaradw.test.ActivityTest
import bigbigdw.joaradw.util.LogoutResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityWriter : AppCompatActivity() {

    private var mContext: Context? = null

    private var appBarConfiguration: AppBarConfiguration? = null
    var drawer: DrawerLayout? = null
    var navigationView: NavigationView? = null
    var navController: NavController? = null
    var navHeaderView: View? = null

    var homeImg: ImageView? = null
    var btnLogout: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.component_writer)

        mContext = this

        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_writer)
        homeImg = findViewById(R.id.HomeImg)

        navHeaderView = navigationView!!.getHeaderView(0)
        btnLogout = navHeaderView!!.findViewById(R.id.Btn_Logout)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_writer)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        setLayout()
    }

    fun setLayout(){
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.Fragment_Writer
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
        }

        btnLogout!!.setOnClickListener {
            onClickLogout()
        }
    }

    private fun onClickLogout() {

        val token = getSharedPreferences("LOGIN", MODE_PRIVATE).getString("TOKEN", "").toString()

        RetrofitNovel.onClickLogout(token, this)!!.enqueue(object : Callback<LogoutResult?> {
            override fun onResponse(call: Call<LogoutResult?>, response: Response<LogoutResult?>) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val status = it.status

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

    //로그아웃
    fun deleteSignedInfo() {
        val alBuilder = AlertDialog.Builder(this)
        alBuilder.setMessage("로그아웃하시겠습니까?")
        alBuilder.setPositiveButton("예") { _: DialogInterface?, _: Int ->
            val editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit()
            editor.clear()
            editor.apply()

            Toast.makeText(applicationContext, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, ActivityLoginMain::class.java)
            startActivity(intent)
            finish()
        }
        alBuilder.setNegativeButton("아니오") { _: DialogInterface?, _: Int -> }
        alBuilder.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.novel_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navControllerUp = Navigation.findNavController(this, R.id.nav_host_fragment_writer)
        return (NavigationUI.navigateUp(navControllerUp, appBarConfiguration!!)
                || super.onSupportNavigateUp())
    }
}