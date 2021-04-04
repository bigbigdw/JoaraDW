package Bigbigdw.JoaraDW.Main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_main);
        queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        boolean IsFirstPage = intent.getBooleanExtra("IsFirstPage", true);
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
            System.out.println("USERINFO 읽기 완료");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            System.out.println("USERINFO 읽기 실패");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_vie);
        View nav_header_view = navigationView.getHeaderView(0);

        Drawer_LogOut = nav_header_view.findViewById(R.id.Drawer_LogOut);
        Drawer_LogIn = nav_header_view.findViewById(R.id.Drawer_LogIn);


        if (STATUS.equals("1")) {
            System.out.println("로그인 성공");
            Drawer_LogOut.setVisibility(View.GONE);
            Drawer_LogIn.setVisibility(View.VISIBLE);
        } else {
            System.out.println("로그인 안됨");
            Drawer_LogOut.setVisibility(View.VISIBLE);
            Drawer_LogIn.setVisibility(View.GONE);
        }

        AppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.Fragment_main
        ).setOpenableLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, AppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        BottomNavigationView navView = findViewById(R.id.nav_bottom);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.Fragment_main) {
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
                    Popup = new Popup(this, BtnLeftListener, BtnRightListener);
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


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "앱을 종료합니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

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


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, AppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private View.OnClickListener BtnLeftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Popup.dismiss();
        }
    };

    private View.OnClickListener BtnRightListener = new View.OnClickListener() {
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
                    System.out.println("파일을 삭제하였습니다.");
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    intent.putExtra("IsFirstPage", false);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(intent, 0);
                    finish();
                    startActivity(getIntent());
                } else {
                    System.out.println("파일이 존재하지 않습니다.");
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
        System.out.println(HELPER.API + LogoutURL + HELPER.ETC + "&category=22%2C2" + USERTOKEN);

        String filename = getDataDir() + "/";

        final StringRequest jsonRequest = new StringRequest(Request.Method.GET, HELPER.API + LogoutURL + HELPER.ETC + "&category=22%2C2" + USERTOKEN, response -> {
            System.out.println(response);
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
