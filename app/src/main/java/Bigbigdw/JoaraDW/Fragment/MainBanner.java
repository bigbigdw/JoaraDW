package Bigbigdw.JoaraDW.Fragment;

import android.content.res.AssetManager;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

interface SetMainBanner {

    static void MainBanner(AssetManager assetManager, CarouselView MainBanner, ViewListener viewListener, List<String> testList)
    {
        try {
            InputStream is = assetManager.open("Main-Banner.Json");
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

                String[] MainBannerUrl = new String[testList.size()];
                testList.toArray(MainBannerUrl);

                MainBanner.setPageCount(MainBannerUrl.length);
                MainBanner.setSlideInterval(4000);
                MainBanner.setViewListener(viewListener);

                testList.add(imgfile);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


}
