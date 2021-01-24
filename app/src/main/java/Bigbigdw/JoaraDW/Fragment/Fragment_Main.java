package Bigbigdw.JoaraDW.Fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import Bigbigdw.JoaraDW.Main.Main_BookData_A;
import Bigbigdw.JoaraDW.Main.Main_BookListAdataper_A;
import Bigbigdw.JoaraDW.R;


public class Fragment_Main extends Fragment implements Main_Banner {

    private Main_BookListAdataper_A adapter = new Main_BookListAdataper_A();

    CarouselView MainBanner;
    List<String> MainBannerURLs = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AssetManager assetManager = getActivity().getAssets();
        MainBanner = root.findViewById(R.id.Carousel_MainBanner);

        Main_Banner.SetMainBanner(assetManager, MainBanner, imageListener, MainBannerURLs);

        BookHistoryList(root, assetManager, "Main_HistoryBooks.json");
        BookHobbyList(root, assetManager, "Main_HobbyBooks.json");


        return root;
    }

    public void BookHistoryList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.Main_HistoryBookList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItems(new Main_BookData_A().getData(assetManager, BookType));
    }

    public void BookHobbyList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.Main_HobbyBookList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItems(new Main_BookData_A().getData(assetManager, BookType));
    }


    ImageListener imageListener = (position, imageView) -> {
        imageView.setAdjustViewBounds(true);

        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);

                double doubled = (imageView.getMeasuredWidth() / 1.704) + 5;
                int finalHeight = Integer.parseInt(String.valueOf(Math.round(doubled)));

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        finalHeight);
                MainBanner.setLayoutParams(layoutParams);

                return true;
            }
        });

        Glide.with(requireActivity().getApplicationContext()).load(MainBannerURLs.get(position))
                .into(imageView);

    };

}
