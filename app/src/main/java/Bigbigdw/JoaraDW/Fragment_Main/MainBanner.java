package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

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
import java.util.List;

import Bigbigdw.JoaraDW.Main.Main_BookListData_A;

interface Main_Banner {

    static void SetMainBanner(AssetManager assetManager, CarouselView MainBanner, ImageListener imageListener, List<String> MainBannerURLs, String BannerType)
    {
        try {
            InputStream is = assetManager.open(BannerType);
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
            JSONArray flag = jsonObject.getJSONArray("banner");

                for (int i = 0; i < flag.length(); i++) {
                    JSONObject jo = flag.getJSONObject(i);

                    String imgfile = jo.getString("imgfile");

                    String[] BannerUrl = new String[MainBannerURLs.size()];
                    MainBannerURLs.toArray(BannerUrl);
                    MainBannerURLs.add(imgfile);

                    MainBanner.setPageCount(BannerUrl.length);
                    MainBanner.setSlideInterval(4000);
                    MainBanner.setImageListener(imageListener);
                }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    static void SetMidMainBanner(AssetManager assetManager, CarouselView MainBannerMid, ImageListener imageListenerMid, List<String> MainBannerMidURLs, String BannerType)
    {
        try {
            InputStream is = assetManager.open(BannerType);
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
            JSONArray flag = jsonObject.getJSONArray("banner");

            for (int i = 0; i < flag.length(); i++) {
                JSONObject jo = flag.getJSONObject(i);

                String imgfile = jo.getString("imgfile");

                String[] BannerUrl = new String[MainBannerMidURLs.size()];
                MainBannerMidURLs.toArray(BannerUrl);
                MainBannerMidURLs.add(imgfile);

                MainBannerMid.setPageCount(BannerUrl.length);
                MainBannerMid.setSlideInterval(4000);
                MainBannerMid.setImageListener(imageListenerMid);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    static void SetBanner(CarouselView Banner, ImageListener imageListener, List<String> BannerURLs, String API_URL, String ETC)
    {
        AsyncTask.execute(new Runnable() {
            String result = null;
            String API = "https://api.joara.com";
            String API_KEY = "?api_key=mw_8ba234e7801ba288554ca07ae44c7";
            String VER = "&ver=2.6.3";
            String DEVICE = "&device=mw";
            String DEVICE_ID = "&deviceuid=5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604";
            String DEVICE_TOKEN = "&devicetoken=mw";

            @Override
            public void run() {
                try {
                    URL url = new URL(API + API_URL + API_KEY + VER + DEVICE + DEVICE_ID + DEVICE_TOKEN + ETC);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream is = conn.getInputStream();

                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    result = builder.toString();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray flag = jsonObject.getJSONArray("banner");

                    for (int i = 0; i < flag.length(); i++) {
                        JSONObject jo = flag.getJSONObject(i);

                        String imgfile = jo.getString("imgfile");

                        String[] BannerUrl = new String[BannerURLs.size()];
                        BannerURLs.toArray(BannerUrl);
                        BannerURLs.add(imgfile);

                        Banner.setPageCount(BannerUrl.length);
                        Banner.setSlideInterval(4000);
                        Banner.setImageListener(imageListener);
                    }
                }
                catch (Exception e) {
                    Log.e("REST_API", "GET method failed: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
