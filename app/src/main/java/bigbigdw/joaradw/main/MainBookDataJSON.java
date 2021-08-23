package bigbigdw.joaradw.main;

import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import bigbigdw.joaradw.model.BookInfo;

public class MainBookDataJSON {
    ArrayList<OLD_MainBookListData> items = new ArrayList<>();

    public ArrayList<OLD_MainBookListData> getData(AssetManager assetManager, String bookType) {

        try {
            try(InputStream is = assetManager.open(bookType)){
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

                    BookInfo tempBookInfo = BookInfo.getParseData(jo);

                    items.add(new OLD_MainBookListData(tempBookInfo.getWriter(), tempBookInfo.getTitle(), tempBookInfo.getBookImg(), tempBookInfo.getIsAdult(), tempBookInfo.getIsFinish(), tempBookInfo.getIsPremium(), tempBookInfo.getIsNobless(), tempBookInfo.getIntro(), tempBookInfo.getIsFavorite(),"","","",0,"","","",tempBookInfo.getCtnChapter()));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}