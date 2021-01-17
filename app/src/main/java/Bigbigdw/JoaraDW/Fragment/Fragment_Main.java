package Bigbigdw.JoaraDW.Fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
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

import Bigbigdw.JoaraDW.R;

public class Fragment_Main extends Fragment implements SetMainBanner{

    CarouselView MainBanner;
    List<String> testList = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AssetManager assetManager = getActivity().getAssets();
        MainBanner = root.findViewById(R.id.Carousel_MainBanner);

        SetMainBanner.MainBanner(assetManager, MainBanner, viewListener, testList);

        return root;
    }


    ViewListener viewListener = position -> {
        View customView = getLayoutInflater().inflate(R.layout.main_banner_top, null);

        ImageView fruitImageView = customView.findViewById(R.id.fruitImageView);

        Glide.with(requireActivity().getApplicationContext()).load(testList.get(position))
                .into(fruitImageView);
        return customView;
    };
}
