package Bigbigdw.JoaraDW.Fragment_Main;

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

    static void SetBanner(CarouselView MainBanner, List<String> MainBannerURLs, RequestQueue queue, String USERTOKEN, String ETC) {
        String ResultURL = HELPER.API + "/v1/banner/home_banner.joa" + HELPER.ETC + USERTOKEN + ETC;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {
            try {
                JSONArray BannerArray = response.getJSONArray("banner");
                for (int i = 0; i < BannerArray.length(); i++) {
                    JSONObject jo = BannerArray.getJSONObject(i);
                    String imgfile = jo.getString("imgfile");
                    String[] BannerUrl = new String[MainBannerURLs.size()];
                    MainBannerURLs.toArray(BannerUrl);
                    MainBannerURLs.add(imgfile);
                }
                MainBanner.setPageCount(MainBannerURLs.size());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonRequest);
    }
}
