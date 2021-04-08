package Bigbigdw.JoaraDW.Main;

        import android.content.res.AssetManager;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.view.View;
        import android.widget.LinearLayout;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.toolbox.JsonObjectRequest;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;

        import Bigbigdw.JoaraDW.Etc.HELPER;

public class Main_BookData_Webtoon {
    ArrayList<Main_BookListData_A> items = new ArrayList<>();

    public ArrayList<Main_BookListData_A> getData(String API_URL, String ETC, RequestQueue queue, LinearLayout Wrap) {

        String ResultURL = HELPER.API + API_URL + HELPER.ETC + ETC;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray flag = response.getJSONArray("webtoons");

                    for (int i = 0; i < flag.length(); i++) {
                        JSONObject jo = flag.getJSONObject(i);

                        String BookImg = jo.getString("webtoon_img");
                        String Title = jo.getString("webtoon_title");

                        items.add(new Main_BookListData_A("", Title, BookImg, "", "", "", "","",""));
                    }

                    Wrap.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {

        });
        queue.add(jsonRequest);
        return items;
    }
}