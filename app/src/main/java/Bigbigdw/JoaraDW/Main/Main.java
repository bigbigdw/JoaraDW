package Bigbigdw.JoaraDW.Main;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Etc.Popup;
import Bigbigdw.JoaraDW.Etc.Splash;
import Bigbigdw.JoaraDW.JOARADW;
import Bigbigdw.JoaraDW.Login.Login_Main;
import Bigbigdw.JoaraDW.R;


public class Main extends AppCompatActivity {
    private AppBarConfiguration AppBarConfiguration;
    private Popup Popup;
    boolean IsFirstPage = true;
    String USERTOKEN = "", STATUS = "";
    LinearLayout Drawer_LogOut, Drawer_LogIn;
    RequestQueue queue;
    TextView Mana, Coupon, Cash, Manuscript_Coupon,Support_Coupon, UserName;
    JSONObject GETUSERINFO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_vie);
        View nav_header_view = navigationView.getHeaderView(0);

        Drawer_LogOut = nav_header_view.findViewById(R.id.Drawer_LogOut);
        Drawer_LogIn = nav_header_view.findViewById(R.id.Drawer_LogIn);
        Mana = nav_header_view.findViewById(R.id.Mana);
        Coupon = nav_header_view.findViewById(R.id.Coupon);
        Cash = nav_header_view.findViewById(R.id.Cash);
        Manuscript_Coupon = nav_header_view.findViewById(R.id.Manuscript_Coupon);
        Support_Coupon = nav_header_view.findViewById(R.id.Support_Coupon);
        UserName = nav_header_view.findViewById(R.id.UserName);

        Intent intent = getIntent();
        IsFirstPage = intent.getBooleanExtra("IsFirstPage", true);
        if (IsFirstPage) {
            Intent intentSplash = new Intent(this, Splash.class);
            startActivity(intentSplash);
        }

        GETUSERINFO = Config.GETUSERINFO();
        try {
            if(GETUSERINFO != null){
                JSONObject UserInfo = GETUSERINFO.getJSONObject("user");
                STATUS = GETUSERINFO.getString("status");
                USERTOKEN = "&token=" + UserInfo.getString("token");
                String mana = UserInfo.getString("mana");
                Mana.setText(mana);
                String expire_cash = UserInfo.getString("expire_cash");
                Coupon.setText(expire_cash);
                String cash = UserInfo.getString("cash");
                Cash.setText(cash);
                String usernamed = new String(UserInfo.getString("nickname").getBytes(), StandardCharsets.UTF_8);
                UserName.setText(usernamed);
                String manuscript_coupon = UserInfo.getString("manuscript_coupon");
                Manuscript_Coupon.setText(manuscript_coupon);
                String support_coupon = UserInfo.getString("support_coupon");
                Support_Coupon.setText(support_coupon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LoginCheck(queue, USERTOKEN, Drawer_LogIn, Drawer_LogOut, navigationView);

        AppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.Fragment_Main
        ).setOpenableLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, AppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        BottomNavigationView navView = findViewById(R.id.nav_bottom);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.Fragment_Main) {
                setCheckable(navView, false);
                navView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);
            } else {
                setCheckable(navView, true);
                navView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_AUTO);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, HELPER.API + "/api/info/index.joa" + HELPER.ETC + "&category=22%2C2&menu_ver=43", null, response -> {
            try {
                JSONArray BannerArray = response.getJSONArray("banner");
                if (BannerArray.length() != 0) {
                    Popup = new Popup(this, BtnLeftListener, BtnRightListener, BannerArray.getString(0));
                    Popup.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Popup.hide();
        });
        queue.add(jsonRequest);
    }

    void LoginCheck(RequestQueue queue, String USERTOKEN, LinearLayout Drawer_LogIn, LinearLayout Drawer_LogOut, NavigationView navigationView) {
        String ResultURL = HELPER.API + "/v1/user/token_check.joa" + HELPER.ETC + USERTOKEN;
        JOARADW myApp = (JOARADW) getApplicationContext();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {
            try {
                if (response.getString("status").equals("1")) {
                    Drawer_LogOut.setVisibility(View.GONE);
                    Drawer_LogIn.setVisibility(View.VISIBLE);
                } else {
                    Drawer_LogOut.setVisibility(View.VISIBLE);
                    Drawer_LogIn.setVisibility(View.GONE);
                    Config.DeleteJSON();
                }
                hideItem(navigationView, response.getString("status").equals("1"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Main", "에러!"));

        queue.add(jsonRequest);
    }

    public static void setCheckable(BottomNavigationView navView, boolean checkable) {
        final Menu menu = navView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setCheckable(checkable);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void hideItem(NavigationView navigationView, boolean check)
    {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.Menu_Logined).setVisible(check);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, AppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private final View.OnClickListener BtnLeftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Popup.dismiss();
        }
    };

    private final View.OnClickListener BtnRightListener = new View.OnClickListener() {
        public void onClick(View v) {
            Popup.dismiss();
        }
    };

    public void onClickLogin(View v) {
        Toast.makeText(getApplicationContext(), "로그인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Login_Main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        finish();
    }

    void DeleteSignedInfo(String filename) {

        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("로그아웃하시겠습니까?");

        alBuilder.setPositiveButton("예", (dialog, which) -> {
            String filePath = filename + "userInfo.json";
            File deleteFile = new File(filePath);
            if (deleteFile.exists()) {
                deleteFile.delete();
                Toast.makeText(getApplicationContext(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Main.class);
                intent.putExtra("IsFirstPage", false);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(intent, 0);
                finishAffinity();
                startActivity(intent);
                System.exit(0);
            } else {
                Log.d("Logout", "파일이 없음");
            }
            finish();
        });
        alBuilder.setNegativeButton("아니오", (dialog, which) -> {
            return;
        });
        alBuilder.show();

    }

    public void onClickLogout(View v) {

        String LogoutURL = "/v1/user/deauth.joa";
        String filename = getDataDir() + "/";

        final StringRequest jsonRequest = new StringRequest(Request.Method.GET, HELPER.API + LogoutURL + HELPER.ETC + "&category=22%2C2" + USERTOKEN, response -> {
            try {
                JSONObject reader = new JSONObject(response);
                STATUS = reader.getString("status");
                if (STATUS.equals("1")) {
                    DeleteSignedInfo(filename);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(jsonRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
