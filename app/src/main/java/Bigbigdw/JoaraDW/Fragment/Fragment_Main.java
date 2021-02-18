package Bigbigdw.JoaraDW.Fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
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
import Bigbigdw.JoaraDW.Main.Main_BookData_A_Webtoon;
import Bigbigdw.JoaraDW.Main.Main_BookData_C;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_B;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_A;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.R;


public class Fragment_Main extends Fragment implements Main_Banner {

    private final Main_BookListAdapter_A HistoryAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_A HobbyAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_A MDNovelAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_A MDWebtoonAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_B FestivalAdapter = new Main_BookListAdapter_B();
    private final Main_BookListAdapter_C UserPickedAdapter = new Main_BookListAdapter_C();
    private final Main_BookListAdapter_C NotyAdapter = new Main_BookListAdapter_C();
    private final Main_BookListAdapter_C RecommendAdapter = new Main_BookListAdapter_C();

    CarouselView MainBanner;
    List<String> MainBannerURLs = new ArrayList<>();

    CarouselView MainBannerMid;
    List<String> MainBannerMidURLs = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        AssetManager assetManager = getActivity().getAssets();
        MainBanner = root.findViewById(R.id.Carousel_MainBanner);
        MainBannerMid = root.findViewById(R.id.Carousel_MainBanner_Mid);

        Main_Banner.SetMainBanner(assetManager, MainBanner, imageListener, MainBannerURLs);

        BookHistoryList(root, assetManager, "Main_HistoryBooks.json");
        BookHobbyList(root, assetManager, "Main_HobbyBooks.json");
        BookMDNovelList(root, assetManager, "Main_MDNovel.json");
        BookMDWebtoonList(root, assetManager, "Main_MDWebtoon.json");
        BookFestivalList(root, assetManager, "Main_FestivalBookList.json");
        BookUserPickedList(root, assetManager, "Main_UserPicked.json");
        BookNotyList(root, assetManager, "Main_NotyList.json");
        BookRecommendList(root, assetManager, "Main_Recommended.json");

        Main_Banner.SetMidMainBanner(assetManager, MainBannerMid, imageListenerMid, MainBannerMidURLs);

        return root;
    }

    public void BookHistoryList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerViewHistory = (RecyclerView) root.findViewById(R.id.Main_HistoryBookList);
        LinearLayoutManager managerHistory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHistory.setLayoutManager(managerHistory);
        recyclerViewHistory.setAdapter(HistoryAdapter);
        HistoryAdapter.setItems(new Main_BookData_A().getData(assetManager, BookType));
    }

    public void BookHobbyList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerViewHobby = (RecyclerView) root.findViewById(R.id.Main_HobbyBookList);
        LinearLayoutManager managerHobby = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHobby.setLayoutManager(managerHobby);
        recyclerViewHobby.setAdapter(HobbyAdapter);
        HobbyAdapter.setItems(new Main_BookData_A().getData(assetManager, BookType));
    }

    public void BookMDNovelList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerViewMDNovel = (RecyclerView) root.findViewById(R.id.Main_MDNovelList);
        LinearLayoutManager managerMDNovel = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMDNovel.setLayoutManager(managerMDNovel);
        recyclerViewMDNovel.setAdapter(MDNovelAdapter);
        MDNovelAdapter.setItems(new Main_BookData_A().getData(assetManager, BookType));
    }

    public void BookMDWebtoonList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerViewMDWebtoon = (RecyclerView) root.findViewById(R.id.Main_MDWebtoonList);
        LinearLayoutManager managerMDWebtoon = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMDWebtoon.setLayoutManager(managerMDWebtoon);
        recyclerViewMDWebtoon.setAdapter(MDWebtoonAdapter);
        MDWebtoonAdapter.setItems(new Main_BookData_A_Webtoon().getData(assetManager, BookType));
    }

    public void BookFestivalList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerViewFestival = (RecyclerView) root.findViewById(R.id.Main_FestivalBookList);
        LinearLayoutManager managerFestival = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFestival.setLayoutManager(managerFestival);
        recyclerViewFestival.setAdapter(FestivalAdapter);
        FestivalAdapter.setItems(new Main_BookData_A().getData(assetManager, BookType));
    }

    public void BookUserPickedList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerViewFestival = (RecyclerView) root.findViewById(R.id.Main_UserPickedList);
        LinearLayoutManager managerFestival = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFestival.setLayoutManager(managerFestival);
        recyclerViewFestival.setAdapter(UserPickedAdapter);
        UserPickedAdapter.setItems(new Main_BookData_C().getData(assetManager, BookType));
    }

    public void BookNotyList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerViewFestival = (RecyclerView) root.findViewById(R.id.Main_NotyList);
        LinearLayoutManager managerFestival = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFestival.setLayoutManager(managerFestival);
        recyclerViewFestival.setAdapter(NotyAdapter);
        NotyAdapter.setItems(new Main_BookData_C().getData(assetManager, BookType));
    }

    public void BookRecommendList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerViewFestival = (RecyclerView) root.findViewById(R.id.Main_RecommendedList);
        LinearLayoutManager managerFestival = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFestival.setLayoutManager(managerFestival);
        recyclerViewFestival.setAdapter(RecommendAdapter);
        RecommendAdapter.setItems(new Main_BookData_C().getData(assetManager, BookType));
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

    ImageListener imageListenerMid = (position, imageView) -> {
        imageView.setAdjustViewBounds(true);

        ViewTreeObserver vtoMid = imageView.getViewTreeObserver();
        vtoMid.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);

                double doubled = (imageView.getMeasuredWidth()/6);
                int finalHeight = Integer.parseInt(String.valueOf(Math.round(doubled)));

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        finalHeight);
                MainBannerMid.setLayoutParams(layoutParams);

                return true;
            }
        });

        Glide.with(requireActivity().getApplicationContext()).load(MainBannerMidURLs.get(position))
                .into(imageView);

    };

}
