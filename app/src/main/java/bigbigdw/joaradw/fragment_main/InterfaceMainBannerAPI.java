package bigbigdw.joaradw.fragment_main;

import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.synnapps.carouselview.CarouselView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import bigbigdw.joaradw.etc.HELPER;

interface InterfaceMainBannerAPI {

    static void setBanner(CarouselView banner, List<String> bannerURLs, RequestQueue queue, String usertoken, String etc) {
        String resultURL = HELPER.API + "/v1/banner/home_banner.joa" + HELPER.ETC + usertoken + etc;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {
            try {
                JSONArray bannerArray = response.getJSONArray("banner");
                for (int i = 0; i < bannerArray.length(); i++) {
                    JSONObject jo = bannerArray.getJSONObject(i);
                    String imgfile = jo.getString("imgfile");
                    String[] bannerUrl = new String[bannerURLs.size()];
                    bannerURLs.toArray(bannerUrl);
                    bannerURLs.add(imgfile);
                }
                banner.setPageCount(bannerURLs.size());
                banner.setSlideInterval(4000);
                banner.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(jsonRequest);
    }
}
