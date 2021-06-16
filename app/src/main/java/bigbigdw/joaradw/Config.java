package bigbigdw.joaradw;

import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {

    private Config() { throw new IllegalStateException("Config class"); }

    private static JSONObject jsonobject;

    //유저 정보 가져오기
    public static JSONObject getuserinfo() {
        try {
            try (FileReader fr = new FileReader("/data/user/0/Bigbigdw.JoaraDW" + "/userInfo.json")) {
                try (BufferedReader br = new BufferedReader(fr)) {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    while (line != null) {
                        sb.append(line).append("\n");
                        line = br.readLine();
                    }
                    String result = sb.toString();
                    jsonobject = new JSONObject(result);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("USERINFO", "읽기 실패");
        }
        return jsonobject;
    }

    //뷰어
    public static JSONObject getfakeviewer(AssetManager assetManager) {
        try {
            try(InputStream is = assetManager.open("ViewerContents.json")){
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);

                StringBuilder buffer = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    buffer.append(line).append("\n");
                    line = reader.readLine();
                }
                String result = buffer.toString();
                jsonobject = new JSONObject(result);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("GETFAKEVIEWER", "읽기 실패");
        }
        return jsonobject;
    }

    //유저 정보 지우기
    public static JSONObject deleteJSON() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("/data/user/0/Bigbigdw.JoaraDW" + "userInfo.json");
            BufferedWriter bufwr = new BufferedWriter(fw);
            bufwr.close();
            jsonobject = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonobject;
    }
}
