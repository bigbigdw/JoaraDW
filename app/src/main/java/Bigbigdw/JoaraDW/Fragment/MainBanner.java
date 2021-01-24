package Bigbigdw.JoaraDW.Fragment;

import android.content.res.AssetManager;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

interface Main_Banner {

    static void SetMainBanner(AssetManager assetManager, CarouselView MainBanner, ImageListener imageListener, List<String> MainBannerURLs)
    {
        try {
            InputStream is = assetManager.open("Main_Banner.json");
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

    static void SetMidMainBanner(AssetManager assetManager, CarouselView MainBanner, ViewListener viewListener, List<String> MainBannerURLs)
    {
        try {
            InputStream is = assetManager.open("Main_Banner.json");
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
                MainBanner.setViewListener(viewListener);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
