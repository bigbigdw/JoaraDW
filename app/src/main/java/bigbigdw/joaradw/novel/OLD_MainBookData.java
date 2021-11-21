
package bigbigdw.joaradw.novel;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.model.BookInfo;

public class OLD_MainBookData {

    ArrayList<OLD_MainBookListData> items = new ArrayList<>();

    public ArrayList<OLD_MainBookListData> getData(String apiUrl, String etc, RequestQueue queue, LinearLayout wrap, String type) {
        String resultURL = HELPER.API + apiUrl + HELPER.ETC + etc;
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {
            try {
                int length;
                int initNum = 0;

                JSONArray flag = response.getJSONArray("books");

                if (type.equals("BEST")) {
                    length = Math.min(flag.length(), 5);
                    initNum = 0;
                } else {
                    length = flag.length();
                }

                for (int i = initNum; i < length; i++) {
                    JSONObject jo = flag.getJSONObject(i);

                    BookInfo tempBookInfo = BookInfo.getParseData(jo);
                    BookInfo tempBookInfoBest = BookInfo.getParseBest(i);

                    items.add(new OLD_MainBookListData(
                            tempBookInfo.getWriter(),
                            tempBookInfo.getTitle(),
                            tempBookInfo.getBookImg(),
                            tempBookInfo.getIsAdult(),
                            tempBookInfo.getIsFinish(),
                            tempBookInfo.getIsPremium(),
                            tempBookInfo.getIsNobless(),
                            tempBookInfo.getIntro(),
                            tempBookInfo.getIsFavorite(),
                            tempBookInfo.getCntPageRead(),
                            tempBookInfo.getCntFavorite(),
                            tempBookInfo.getCntRecom(),
                            tempBookInfoBest.getBookBestRank(),
                            "1",
                            tempBookInfo.getBookCode(),
                            tempBookInfo.getCategoryKoName(),
                            tempBookInfo.getCtnChapter()));
                    wrap.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                wrap.setVisibility(View.GONE);
            }
        }, error -> Log.d("Main_BookData", "에러!"));
        queue.add(jsonRequest);
        return items;
    }
}
