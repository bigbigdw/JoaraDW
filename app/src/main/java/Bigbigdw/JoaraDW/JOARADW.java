package Bigbigdw.JoaraDW;

import android.app.Application;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JOARADW extends Application {

    String BOOKCODE;
    String TOKEN;
    String API_URL;

    @Override
    public void onCreate() {
        BOOKCODE = "";
        API_URL = "";
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setBookCode(String BOOKCODE) {
        this.BOOKCODE = BOOKCODE;
    }

    public String getBookCode() {
        return BOOKCODE;
    }

    public void setToken(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getToken() {
        return TOKEN;
    }

    public void setAPI_URL(String API_URL) {
        this.API_URL = API_URL;
    }

    public String getAPI_URL() {
        return API_URL;
    }

    public String GetTokenJSON() {
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
            String result = sb.toString();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject UserInfo = jsonObject.getJSONObject("user");
            TOKEN = UserInfo.getString("token");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("USERINFO", "읽기 실패");
        }
        return TOKEN;
    }
}