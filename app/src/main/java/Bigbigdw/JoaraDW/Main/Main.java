package Bigbigdw.JoaraDW.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Bigbigdw.JoaraDW.HELPER;
import Bigbigdw.JoaraDW.Etc.Popup;
import Bigbigdw.JoaraDW.Etc.Splash;
import Bigbigdw.JoaraDW.R;


public class Main extends AppCompatActivity {
    private AppBarConfiguration AppBarConfiguration;
    private Popup Popup;
    String USERTOKEN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = getIntent();
        boolean IsFirstPage = intent.getBooleanExtra("IsFirstPage", true);
        if (IsFirstPage) {
            Intent intentSplash = new Intent(this, Splash.class);
            startActivity(intentSplash);
        }

        try {
            // 파일읽어오기 : 내부 디랙토리, 파일이름
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
            USERTOKEN = "&token=" + UserInfo.getString("token");

//            System.out.println(result);// 결과 로그찍기

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_vie);

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

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, HELPER.API + "/api/info/index.joa" + HELPER.ETC +  USERTOKEN + "&category=22%2C2&menu_ver=43", null, response -> {
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
}
