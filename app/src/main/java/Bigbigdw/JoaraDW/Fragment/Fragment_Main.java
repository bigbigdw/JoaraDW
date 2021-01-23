package Bigbigdw.JoaraDW.Fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import java.util.List;

import Bigbigdw.JoaraDW.R;

import static java.lang.Integer.parseInt;

public class Fragment_Main extends Fragment implements Main_Banner{

    CarouselView MainBanner;
    List<String> MainBannerURLs = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AssetManager assetManager = getActivity().getAssets();
        MainBanner = root.findViewById(R.id.Carousel_MainBanner);

       Main_Banner.SetMainBanner(assetManager, MainBanner, imageListener, MainBannerURLs);

//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
//                1103 );
//        MainBanner.setLayoutParams(layoutParams);

        return root;
    }


//    ViewListener viewListener = position -> {
//        View customView = getLayoutInflater().inflate(R.layout.main_banner_top, null);
//
//        ImageView CarouselImg = customView.findViewById(R.id.CarouselImage);
//
//        Glide.with(requireActivity().getApplicationContext()).load(MainBannerURLs.get(position))
//                .into(CarouselImg);
//        return customView;
//    };



    ImageListener imageListener = (position, imageView) -> {
        imageView.setAdjustViewBounds(true);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                Log.d("", "Height: " + imageView.getMeasuredHeight() + " Width: " + imageView.getMeasuredWidth());

                double doubled = (imageView.getMeasuredWidth()/ 1.704) + 5;
                float density = getContext().getResources().getDisplayMetrics().density;

                Log.d("",  "density: " + density);

                int finalHeight = Integer.parseInt(String.valueOf(Math.round(doubled)));
                Log.d("", "finalHeight: " + finalHeight);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        finalHeight );
                MainBanner.setLayoutParams(layoutParams);

                return true;
            }
        });


            Glide.with(requireActivity().getApplicationContext()).load(MainBannerURLs.get(position))
            .into(imageView);


    };
}
