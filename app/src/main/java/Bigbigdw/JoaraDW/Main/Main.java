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

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Etc.Popup;
import Bigbigdw.JoaraDW.Etc.Splash;
import Bigbigdw.JoaraDW.Login.Login_Main;
import Bigbigdw.JoaraDW.R;


public class Main extends AppCompatActivity {
    private AppBarConfiguration AppBarConfiguration;
    private Popup Popup;
    String USERTOKEN = "";
    String STATUS = "";
    LinearLayout Drawer_LogOut;
    LinearLayout Drawer_LogIn;
    RequestQueue queue;
    TextView Mana, Coupon, Cash, Manuscript_Coupon,Support_Coupon, UserName;
    boolean IsFirstPage = true;

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

        try {
            FileReader fr = new FileReader(getDataDir() + "/userInfo.json");
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            br.close();
            String result = sb.toString();//
            JSONObject jsonObject = new JSONObject(result);
            JSONObject UserInfo = jsonObject.getJSONObject("user");
            STATUS = jsonObject.getString("status");
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
            Log.d("USERINFO", "읽기 완료");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("USERINFO", "읽기 실패");
        }

        if (STATUS.equals("1")) {
            Log.d("Login", "로그인 성공");
            Drawer_LogOut.setVisibility(View.GONE);
            Drawer_LogIn.setVisibility(View.VISIBLE);
            hideItem(navigationView);
        } else {
            Log.d("Login", "로그인 실패");
            Drawer_LogOut.setVisibility(View.VISIBLE);
            Drawer_LogIn.setVisibility(View.GONE);
            hideItem(navigationView);
        }

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




//    @Override
//    public void onBackPressed() {
//        Toast.makeText(getApplicationContext(), "앱을 종료합니다.", Toast.LENGTH_SHORT).show();
//        finish();
//    }

    public static void setCheckable(BottomNavigationView navView, boolean checkable) {
        final Menu menu = navView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setCheckable(checkable);
        }
    }

    //추가된 소스, ToolBar에 menu.xml을 인플레이트함

    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수

    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
//        if (item.getItemId() == R.id.action_settings) {
//            Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
//            return true;
//        } else {
//            Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
//        }
        return super.onOptionsItemSelected(item);
    }

    private void hideItem(NavigationView navigationView)
    {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.Menu_Logined).setVisible(STATUS.equals("1"));
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

        // AlertDialog 빌더를 이용해 종료시 발생시킬 창을 띄운다
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("로그아웃하시겠습니까?");

        // "예" 버튼을 누르면 실행되는 리스너
        alBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 파일의 경로 + 파일명
                String filePath = filename + "userInfo.json";
                File deleteFile = new File(filePath);
                // 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
                if (deleteFile.exists()) {
                    // 파일을 삭제합니다.
                    deleteFile.delete();
                    Toast.makeText(getApplicationContext(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("Logout", "파일 삭제 성공");
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    intent.putExtra("IsFirstPage", false);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(intent, 0);
                    finish();
                    startActivity(getIntent());
                } else {
                    Log.d("Logout", "파일이 없음");
                }
                finish(); // 현재 액티비티를 종료한다. (MainActivity에서 작동하기 때문에 애플리케이션을 종료한다.)
            }
        });
        // "아니오" 버튼을 누르면 실행되는 리스너
        alBuilder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return; // 아무런 작업도 하지 않고 돌아간다
            }
        });
//        alBuilder.setTitle("로그아웃");
        alBuilder.show(); // AlertDialog.Bulider로 만든 AlertDialog를 보여준다.

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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
