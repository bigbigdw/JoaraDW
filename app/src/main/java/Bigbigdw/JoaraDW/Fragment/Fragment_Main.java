package Bigbigdw.JoaraDW.Fragment;

import android.content.res.AssetManager;
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
import java.util.Objects;

import Bigbigdw.JoaraDW.R;

public class Fragment_Main extends Fragment {

    CarouselView MainBanner;
    TextView tv;

    String[] MainBannerUrl = {
            "https://cf.joara.com/banner_file/20200914_211535.jpg",
            "https://cf.joara.com/banner_file/20201015_102516.jpg",
            "https://cf.joara.com/banner_file/20200923_120021.jpg",
            "https://cf.joara.com/banner_file/20200923_110355.jpg",
            "https://cf.joara.com/banner_file/20200922_105806.jpg"
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        tv = root.findViewById(R.id.tv);

        MainBanner = root.findViewById(R.id.Carousel_MainBanner);
        MainBanner.setPageCount(MainBannerUrl.length);
        MainBanner.setSlideInterval(4000);
        MainBanner.setViewListener(viewListener);

        AssetManager assetManager = getActivity().getAssets();

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

            System.out.println("하하" + flag);

            StringBuilder s = new StringBuilder();

            for (int i = 0; i < flag.length(); i++) {
                JSONObject jo = flag.getJSONObject(i);

                String idx = jo.getString("idx");
                String imgfile = jo.getString("imgfile");
                String joaralink = jo.getString("joaralink");
                String is_banner_cnt = jo.getString("is_banner_cnt");
                s.append(idx).append(imgfile).append(joaralink).append(is_banner_cnt).append("\n");
            }
            tv.setText(s.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return root;
    }

    ViewListener viewListener = position -> {

        View customView = getLayoutInflater().inflate(R.layout.main_banner_top, null);

        ImageView fruitImageView = customView.findViewById(R.id.fruitImageView);

        Glide.with(requireActivity().getApplicationContext()).load(MainBannerUrl[position])
                .into(fruitImageView);

        return customView;
    };
}
