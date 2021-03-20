package Bigbigdw.JoaraDW.Fragment_Main;

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

import Bigbigdw.JoaraDW.Main.Main_BookData_JSON;
import Bigbigdw.JoaraDW.Main.Main_BookData_Webtoon;
import Bigbigdw.JoaraDW.Main.Main_BookData;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_B;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_A;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_D;
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
    private final Main_BookListAdapter_D NoblessTodayBestAdapter = new Main_BookListAdapter_D();
    private final Main_BookListAdapter_D PremiumToadyBestAdapter = new Main_BookListAdapter_D();
    private final Main_BookListAdapter_D CouponToadyBestAdapter = new Main_BookListAdapter_D();

    CarouselView MainBanner;
    List<String> MainBannerURLs = new ArrayList<>();
    LinearLayout LoadingLayoutWrap, AfterLoading;

    CarouselView MainBannerMid;
    List<String> MainBannerMidURLs = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        AssetManager assetManager = getActivity().getAssets();

        MainBanner = root.findViewById(R.id.Carousel_MainBanner);
        MainBannerMid = root.findViewById(R.id.Carousel_MainBanner_Mid);

        Main_Banner.SetMainBanner(assetManager, MainBanner, imageListener, MainBannerURLs, "Main_Banner.json");
//        Main_Banner.SetBanner(MainBanner, imageListener, MainBannerURLs, "/v1/banner/home_banner.joa","&token=da7e03d618b8689fc8bed38ee8c99273&page=0&banner_type=app_home_top_banner");
//        Main_Banner.SetBanner(MainBannerMid, imageListener, MainBannerMidURLs, "/v1/banner/home_banner.joa","&token=da7e03d618b8689fc8bed38ee8c99273&page=0&banner_type=app_home_top_banner");
        Main_Banner.SetMidMainBanner(assetManager, MainBannerMid, imageListenerMid, MainBannerMidURLs, "Main_Banner_Mid.json");

        MainBanner.setPageCount(15);
        MainBannerMid.setPageCount(10);

        BookList_A(root, "/v1/user/historybooks.joa", "&token=da7e03d618b8689fc8bed38ee8c99273&category=22%2C2&page=1&mem_time=0",  R.id.Main_HistoryBookList,  HistoryAdapter);
        BookList_A(root, "/v1/book/recommend_list_api.joa", "&token=da7e03d618b8689fc8bed38ee8c99273&page=1&book_code=&category=22%2C2&offset=20",  R.id.Main_HobbyBookList,  HobbyAdapter);
        BookList_A(root, "/v1/home/list.joa", "&token=da7e03d618b8689fc8bed38ee8c99273&page=1&section_mode=recommend_book&category=22%2C2&offset=10",  R.id.Main_MDNovelList,  MDNovelAdapter);
        BookList_A_WebToon(root, "/v1/home/webtoon_list.joa", "&token=da7e03d618b8689fc8bed38ee8c99273&page=1&offset=10",  R.id.Main_MDWebtoonList,  MDWebtoonAdapter);
        BookFestivalList(root, assetManager, "Main_FestivalBookList.json");
        BookList_C(root, "/v1/book/list.joa", "&page=1&section_mode=contest_free_award&show_type=home&category=22%2C2&offset=10",  R.id.Main_UserPickedList,  UserPickedAdapter);
        BookList_C(root, "/v1/home/list.joa", "&token=da7e03d618b8689fc8bed38ee8c99273&page=1&section_mode=contest_free_award&show_type=home&category=22%2C2&offset=10",  R.id.Main_NotyList,  NotyAdapter);
        BookList_C(root, "/v1/home/list.joa", "&token=da7e03d618b8689fc8bed38ee8c99273&page=1&section_mode=page_read_book&show_type=home&category=22%2C2&offset=10",  R.id.Main_RecommendedList,  RecommendAdapter);
        BookList_D(root, "/v1/home/list.joa", "&token=da7e03d618b8689fc8bed38ee8c99273&page=1&section_mode=todaybest&store=nobless&orderby=cnt_best&show_type=home&category=22%2C2&offset=10",  R.id.Main_NoblessTodayBestList,  NoblessTodayBestAdapter);
        BookList_D(root, "/v1/home/list.joa", "&token=da7e03d618b8689fc8bed38ee8c99273&page=1&section_mode=todaybest&store=premium&orderby=cnt_best&show_type=home&category=22%2C2&offset=10",  R.id.Main_PremiumTodayBestList,  PremiumToadyBestAdapter);
        BookList_D(root, "/v1/home/list.joa", "&token=da7e03d618b8689fc8bed38ee8c99273&page=1&section_mode=support_coupon&orderby=cnt_best&show_type=home&category=22%2C2&offset=10",  R.id.Main_CouponTodayBestList,  CouponToadyBestAdapter);

        LoadingLayoutWrap = root.findViewById(R.id.LoadingLayoutWrap);
        AfterLoading = root.findViewById(R.id.AfterLoading);

        new android.os.Handler().postDelayed(
                () -> {
                    LoadingLayoutWrap.setVisibility(View.GONE);
                    AfterLoading.setVisibility(View.VISIBLE);
                },
                1000);

        return root;
    }

    public void BookList_A(View root, String API_URL , String ETC, Integer RecylerView, Main_BookListAdapter_A Adapter)
    {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC));
        Adapter.notifyDataSetChanged();
    }

    public void BookList_A_WebToon(View root, String API_URL , String ETC, Integer RecylerView, Main_BookListAdapter_A Adapter)
    {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        Adapter.setItems(new Main_BookData_Webtoon().getData(API_URL, ETC));
        Adapter.notifyDataSetChanged();
    }


    public void BookFestivalList(View root, AssetManager assetManager, String BookType)
    {
        RecyclerView recyclerViewFestival = root.findViewById(R.id.Main_FestivalBookList);
        LinearLayoutManager managerFestival = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFestival.setLayoutManager(managerFestival);
        recyclerViewFestival.setAdapter(FestivalAdapter);
        FestivalAdapter.setItems(new Main_BookData_JSON().getData(assetManager, BookType));
        FestivalAdapter.notifyDataSetChanged();
    }

    public void BookList_C(View root, String API_URL , String ETC, Integer RecylerView, Main_BookListAdapter_C Adapter)
    {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC));
        Adapter.notifyDataSetChanged();
    }

    public void BookList_D(View root, String API_URL , String ETC, Integer RecylerView, Main_BookListAdapter_D Adapter)
    {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC));
        Adapter.notifyDataSetChanged();
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
