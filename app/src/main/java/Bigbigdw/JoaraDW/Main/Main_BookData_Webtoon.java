package Bigbigdw.JoaraDW.Main;

        import android.view.View;
        import android.widget.LinearLayout;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.toolbox.JsonObjectRequest;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

        import Bigbigdw.JoaraDW.Etc.HELPER;

public class Main_BookData_Webtoon {
    ArrayList<Main_BookListData> items = new ArrayList<>();

    public ArrayList<Main_BookListData> getData(String API_URL, String ETC, RequestQueue queue, LinearLayout Wrap) {

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

                        items.add(new Main_BookListData("", Title, BookImg, "", "", "", "","","","","","","",0,""));
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