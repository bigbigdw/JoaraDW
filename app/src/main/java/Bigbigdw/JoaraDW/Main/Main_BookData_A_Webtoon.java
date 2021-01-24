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

public class Main_BookData_A_Webtoon {
    ArrayList<Main_BookListData_A> items = new ArrayList<>();

    public ArrayList<Main_BookListData_A> getData(AssetManager assetManager, String BookType) {

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
            JSONArray flag = jsonObject.getJSONArray("webtoons");

            for (int i = 0; i < flag.length(); i++) {
                JSONObject jo = flag.getJSONObject(i);

                String BookImg = jo.getString("webtoon_img");
                String Title = jo.getString("webtoon_title");

                items.add(new Main_BookListData_A("", Title, BookImg));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}