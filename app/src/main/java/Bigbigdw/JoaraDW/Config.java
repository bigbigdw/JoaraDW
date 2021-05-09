package Bigbigdw.JoaraDW;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Bigbigdw.JoaraDW.Etc.HELPER;

public class Config {

    private static JSONObject JSONObject;
    String LoginStatus;
    RequestQueue queue;

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
}
