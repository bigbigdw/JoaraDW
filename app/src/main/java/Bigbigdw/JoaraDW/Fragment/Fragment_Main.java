package Bigbigdw.JoaraDW.Fragment;

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

import java.util.Objects;

import Bigbigdw.JoaraDW.R;

public class Fragment_Main extends Fragment {

    CarouselView MainBanner;

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

        MainBanner = root.findViewById(R.id.Carousel_MainBanner);
        MainBanner.setPageCount(MainBannerUrl.length);
        MainBanner.setSlideInterval(4000);
        MainBanner.setViewListener(viewListener);

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
