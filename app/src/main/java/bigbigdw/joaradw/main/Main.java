package bigbigdw.joaradw.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.base.BookBaseActivity;
import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.etc.PopupBanner;
import bigbigdw.joaradw.etc.Splash;
import bigbigdw.joaradw.login.LoginMain;
import bigbigdw.joaradw.R;
import bigbigdw.joaradw.test.ActivityTest;


public class Main extends BookBaseActivity {
    private AppBarConfiguration appBarConfiguration;
    private PopupBanner popupBanner;
    boolean isFirstPage = true;
    String usertoken = "";
    String userStatus = "";
    LinearLayout drawerLogout;
    LinearLayout drawerLogin;
    RequestQueue queue;
    TextView viewMana;
    TextView coupon;
    TextView viewCash;
    TextView viewManuscriptCoupon;
    TextView viewSupportCoupon;
    TextView userName;
    ImageView btnLogout;
    ImageView homeImg;
    NavController navController;
    NavigationView navigationView;
    View navHeaderView;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_vie);
        homeImg = findViewById(R.id.HomeImg);
        navHeaderView = navigationView.getHeaderView(0);
        drawerLogout = navHeaderView.findViewById(R.id.Drawer_LogOut);
        drawerLogin = navHeaderView.findViewById(R.id.Drawer_LogIn);
        viewMana = navHeaderView.findViewById(R.id.Mana);
        coupon = navHeaderView.findViewById(R.id.Coupon);
        viewCash = navHeaderView.findViewById(R.id.Cash);
        viewManuscriptCoupon = navHeaderView.findViewById(R.id.Manuscript_Coupon);
        viewSupportCoupon = navHeaderView.findViewById(R.id.Support_Coupon);
        userName = navHeaderView.findViewById(R.id.UserName);
        btnLogout = navHeaderView.findViewById(R.id.Btn_Logout);
        JOARADW myapp = (JOARADW) getApplicationContext();
        userStatus = myapp.getStatus();
        usertoken = "&token=" + myapp.getToken();
        viewMana.setText(myapp.getMana());
        coupon.setText(myapp.getExpireCash());
        viewCash.setText(myapp.getCash());
        userName.setText(myapp.getName());
        viewManuscriptCoupon.setText(myapp.getManuscriptCoupon());
        viewSupportCoupon.setText(myapp.getSupportCoupon());
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        checkToken();

        loginCheck(queue, usertoken, drawerLogin, drawerLogout, navigationView);

        setLayout();

    }

    public void setLayout() {

        drawerLogout.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "로그인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginMain.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            String logoutURL = "/v1/user/deauth.joa";

            final StringRequest jsonRequest = new StringRequest(Request.Method.GET, HELPER.API + logoutURL + HELPER.ETC + "&category=22%2C2" + usertoken, response -> {
                try {
                    JSONObject reader = new JSONObject(response);
                    userStatus = reader.getString("status");
                    if (userStatus.equals("1")) {
                        deleteSignedInfo();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {

            });
            queue.add(jsonRequest);
        });

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.Fragment_Main
        ).setOpenableLayout(drawer).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        BottomNavigationView navView = findViewById(R.id.nav_bottom);
        NavigationUI.setupWithNavController(navView, navController);

        homeImg.setOnClickListener(v -> {
            Intent activityTest = new Intent(getApplicationContext(), ActivityTest.class);
            startActivity(activityTest);
        });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.Fragment_Main) {
                setCheckable(navView, false);
                navView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);
            } else {
                setCheckable(navView, true);
                navView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_AUTO);
            }
        });

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, HELPER.API + "/api/info/index.joa" + HELPER.ETC + "&category=22%2C2&menu_ver=43", null, response -> {
            try {
                JSONArray bannerArray = response.getJSONArray("banner");
                if (bannerArray.length() != 0) {
                    popupBanner = new PopupBanner(this, btnLeftListener, btnRightListener, bannerArray.getString(0));
                    popupBanner.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> popupBanner.hide());
        queue.add(jsonRequest);
    }

    void loginCheck(RequestQueue queue, String usertoken, LinearLayout drawerLogIn, LinearLayout drawerLogOut, NavigationView navigationView) {
        String resultURL = HELPER.API + "/v1/user/token_check.joa" + HELPER.ETC + usertoken;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {
            try {
                String status = response.getString("status");
                if (status.equals("1")) {
                    drawerLogOut.setVisibility(View.GONE);
                    drawerLogIn.setVisibility(View.VISIBLE);
                } else {
                    drawerLogOut.setVisibility(View.VISIBLE);
                    drawerLogIn.setVisibility(View.GONE);
                    Config.deleteJSON();
                }
                hideItem(navigationView, status.equals("1"));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void hideItem(NavigationView navigationView, boolean check) {
        Menu navMenu = navigationView.getMenu();
        navMenu.findItem(R.id.Menu_Logined).setVisible(check);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navControllerUp = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navControllerUp, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private final View.OnClickListener btnLeftListener = (View v) -> popupBanner.dismiss();
    private final View.OnClickListener btnRightListener = (View v) -> popupBanner.dismiss();

    void deleteSignedInfo() {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("로그아웃하시겠습니까?");
        alBuilder.setPositiveButton("예", (dialog, which) -> {
            Config.deleteJSON();
            Toast.makeText(getApplicationContext(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Main.class);
            finishAffinity();
            startActivity(intent);
            System.exit(0);
            finish();
        });
        alBuilder.setNegativeButton("아니오", (dialog, which) -> {
        });
        alBuilder.show();

    }

}
