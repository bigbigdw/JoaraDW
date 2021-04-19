package Bigbigdw.JoaraDW.Main;

import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main_BookData_JSON {
    ArrayList<Main_BookListData> items = new ArrayList<>();

    public ArrayList<Main_BookListData> getData(AssetManager assetManager, String BookType) {

        try {
            InputStream is = assetManager.open(BookType);
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
            JSONArray flag = jsonObject.getJSONArray("books");


            for (int i = 0; i < flag.length(); i++) {
                JSONObject jo = flag.getJSONObject(i);

                String BookImg = jo.getString("book_img");
                String Title = jo.getString("subject");
                String Writer = jo.getString("writer_name");
                String IsAdult = jo.getString("is_adult");
                String IsFinish = jo.getString("is_finish");
                String IsPremium = jo.getString("is_premium");
                String IsNobless = jo.getString("is_nobless");
                String Intro = jo.getString("intro");
                String IsFav = jo.getString("is_favorite");

                items.add(new Main_BookListData(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav,"","","","",0,"",""));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}