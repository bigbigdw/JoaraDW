package Bigbigdw.JoaraDW.BookList;

import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.synnapps.carouselview.CarouselView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import Bigbigdw.JoaraDW.Etc.HELPER;

interface Main_BannerAPI {

    static void SetBanner(CarouselView Banner, List<String> BannerURLs, RequestQueue queue, String USERTOKEN, String ETC) {
        String ResultURL = HELPER.API + "/v1/banner/home_banner.joa" + HELPER.ETC + USERTOKEN + ETC;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {
            try {
                JSONArray BannerArray = response.getJSONArray("banner");
                for (int i = 0; i < BannerArray.length(); i++) {
                    JSONObject jo = BannerArray.getJSONObject(i);
                    String imgfile = jo.getString("imgfile");
                    String[] BannerUrl = new String[BannerURLs.size()];
                    BannerURLs.toArray(BannerUrl);
                    BannerURLs.add(imgfile);
                }
                Banner.setPageCount(BannerURLs.size());
                Banner.setSlideInterval(4000);
                Banner.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(jsonRequest);
    }
}
