package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main_More_NoticeData {
    ArrayList<Main_More_ListData> items = new ArrayList<>();

    public ArrayList<Main_More_ListData> getData(AssetManager assetManager, String data) {

        try {
            InputStream is = assetManager.open(data);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuilder buffer = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line).append("\n");
                line = reader.readLine();
            }

            String jsonData = buffer.toString();

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray flag = jsonObject.getJSONArray("notices");

            for (int i = 0; i < 5; i++) {
                JSONObject jo = flag.getJSONObject(i);

                String Title = jo.getString("title");
                String StartDate = jo.getString("created");

                items.add(new Main_More_ListData(Title, StartDate));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}



