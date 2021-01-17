package Bigbigdw.JoaraDW.Fragment;

import android.content.res.AssetManager;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import Bigbigdw.JoaraDW.R;

public class Fragment_Main extends Fragment implements SetMainBanner{

    CarouselView MainBanner;

//    String[] MainBannerUrl = new String[0];
    String[] MainBannerUrl = new String[7];

    List<String> testList = new ArrayList<>(Arrays.asList(MainBannerUrl));

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AssetManager assetManager = getActivity().getAssets();
        MainBanner = root.findViewById(R.id.Carousel_MainBanner);

//        MainBanner(assetManager, MainBanner);

        SetMainBanner.MainBanner(assetManager, MainBannerUrl, MainBanner, viewListener);

        return root;
    }

    private void MainBanner(AssetManager assetManager, CarouselView MainBanner)
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


                MainBannerUrl = new String[testList.size()];
                testList.toArray(MainBannerUrl);

                MainBanner.setPageCount(MainBannerUrl.length);
                MainBanner.setSlideInterval(4000);
                MainBanner.setViewListener(viewListener);

                testList.add(imgfile);
            }
            System.out.println("하하하" + Arrays.toString(MainBannerUrl));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }



    ViewListener viewListener = position -> {
        View customView = getLayoutInflater().inflate(R.layout.main_banner_top, null);

        ImageView fruitImageView = customView.findViewById(R.id.fruitImageView);

        Glide.with(requireActivity().getApplicationContext()).load(MainBannerUrl[position])
                .into(fruitImageView);
        return customView;
    };
}
