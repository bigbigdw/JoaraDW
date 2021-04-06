package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Etc.Popup;
import Bigbigdw.JoaraDW.Main.Main_BookListData_A;

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
