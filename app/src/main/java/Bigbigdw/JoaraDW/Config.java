package Bigbigdw.JoaraDW;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import Bigbigdw.JoaraDW.Etc.HELPER;

public class Config {

    private static JSONObject JSONObject;
    String jsonData;

    //유저 정보 가져오기
    public static JSONObject GETUSERINFO() {
        try {
            FileReader fr = new FileReader("/data/user/0/Bigbigdw.JoaraDW" + "/userInfo.json");
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            br.close();
            String result = sb.toString();
            JSONObject = new JSONObject(result);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("USERINFO", "읽기 실패");
        }
        return JSONObject;
    }

    //뷰어
    public static JSONObject GETFAKEVIEWER(AssetManager assetManager) {
        try {
            InputStream is = assetManager.open("ViewerContents.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuilder buffer = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line).append("\n");
                line = reader.readLine();
            }

            String result = buffer.toString();
            JSONObject = new JSONObject(result);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("GETFAKEVIEWER", "읽기 실패");
        }
        return JSONObject;
    }

    //유저 정보 지우기
    public static JSONObject DeleteJSON() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("/data/user/0/Bigbigdw.JoaraDW" + "userInfo.json");
            BufferedWriter bufwr = new BufferedWriter(fw);
            bufwr.close();
            JSONObject = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject;
    }
}
