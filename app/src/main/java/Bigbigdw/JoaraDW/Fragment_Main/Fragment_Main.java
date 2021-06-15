package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import java.util.ArrayList;
import java.util.List;

import Bigbigdw.JoaraDW.Base.BookBaseFragment;
import Bigbigdw.JoaraDW.Etc.BookList;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_A;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_B;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_D;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.JOARADW;
import Bigbigdw.JoaraDW.R;


public class Fragment_Main extends BookBaseFragment implements Main_BannerAPI {

    private final Main_BookListAdapter_A historyAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_A hobbyAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_A mdNovelAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_A mdWebtoonAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_B festivalAdapter = new Main_BookListAdapter_B();
    private final Main_BookListAdapter_B promiseAdapter = new Main_BookListAdapter_B();
    private final Main_BookListAdapter_B kidamuAdapter = new Main_BookListAdapter_B();

    private final ArrayList<Main_BookListData> items = new ArrayList<>();

    CarouselView mainBanner;
    CarouselView mainBannerMid;
    List<String> mainBannerURLs = new ArrayList<>();
    List<String> mainBannerMidURLs = new ArrayList<>();
    String userToken = "";
    String userStatus = "";
    String etc = "&page=1&offset=10";
    String showType = "&show_type=home";
    TextView userNameCategory;
    TextView goToHistory;
    TextView goToFes;
    TextView goToPromised;
    TextView goToKidamu;
    TextView goToNoty;
    TextView bookSnipe;
    TextView userPicked;
    TextView bookRecommend;
    LinearLayout wrap77Fes;
    LinearLayout wrapKidamu;
    LinearLayout wrapNOTY;
    LinearLayout wrapPromised;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        userNameCategory = root.findViewById(R.id.UserNameCategory);
        bookSnipe = root.findViewById(R.id.BookSnipe);
        userPicked = root.findViewById(R.id.UserPicked);
        bookRecommend = root.findViewById(R.id.BookRecommend);
        mainBanner = root.findViewById(R.id.Carousel_MainBanner);
        mainBannerMid = root.findViewById(R.id.Carousel_MainBanner_Mid);
        wrap77Fes = root.findViewById(R.id.Wrap77Fes);
        wrapKidamu = root.findViewById(R.id.WrapKidamu);
        wrapNOTY = root.findViewById(R.id.WrapNOTY);
        wrapPromised = root.findViewById(R.id.WrapPromised);
        goToHistory = root.findViewById(R.id.GoToHistory);
        goToFes = root.findViewById(R.id.GoToFes);
        goToPromised = root.findViewById(R.id.GoToPromised);
        goToKidamu = root.findViewById(R.id.GoToKidamu);
        goToNoty = root.findViewById(R.id.GoToNoty);

        CheckToken();

        AssetManager assetManager = getActivity().getAssets();
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JOARADW app = (JOARADW) requireActivity().getApplicationContext();
        userToken = app.getTOKEN();
        userStatus = app.getUSERSTATUS();
        userNameCategory.setText(app.getUserName());

        Main_BannerAPI.SetBanner(mainBannerMid, mainBannerMidURLs, queue, "&token=" + userToken, "&page=&banner_type=app_main2016_event");
        Main_BannerAPI.SetBanner(mainBanner, mainBannerURLs, queue, "&token=" + userToken, "&page=0&banner_type=app_home_top_banner");
        mainBanner.setImageListener(imageListener);
        mainBannerMid.setImageListener(imageListenerMid);

        if (userStatus.equals("1")) {
            BookList_A(root, "/v1/user/historybooks.joa", "&token=" + userToken + "&mem_time=0" + etc, R.id.Main_HistoryBookList, historyAdapter, R.id.main_booklist_history);
            BookList_A(root, "/v1/book/recommend_list_api.joa", "&token=" + userToken + "&book_code=", R.id.Main_HobbyBookList, hobbyAdapter, R.id.main_booklist_hobby);
        }
        BookList_A(root, "/v1/home/list.joa", "&token=" + userToken + "&page=1&section_mode=recommend_book" + etc, R.id.Main_MDNovelList, mdNovelAdapter, R.id.main_booklist_mdnovel);
        BookList.BookList_A_WebToon(root, "/v1/home/webtoon_list.joa", userToken, R.id.Main_MDWebtoonList, mdWebtoonAdapter, queue, R.id.main_booklist_mdwebtoon);

        BookList.BookList_B(root, assetManager, "Main_FestivalBookList.json", R.id.Main_FestivalBookList, festivalAdapter);
        BookList.BookList_B(root, assetManager, "Main_PromisedBookList.json", R.id.Main_PromisedBookList, promiseAdapter);
        BookList.BookList_B(root, assetManager, "Main_KidamuBookList.json", R.id.Main_KidamuBookList, kidamuAdapter);

        Main_BookListAdapter_C UserPickedAdapter = new Main_BookListAdapter_C(items);
        Main_BookListAdapter_C NotyAdapter = new Main_BookListAdapter_C(items);
        Main_BookListAdapter_C RecommendAdapter = new Main_BookListAdapter_C(items);
        BookList_C(root, "/v1/book/list.joa", "&token=" + userToken + "&section_mode=contest_free_award" + etc + showType, R.id.Main_UserPickedList, UserPickedAdapter, R.id.main_booklist_userpicked);
        BookList_C(root, "/v1/home/list.joa", "&token=" + userToken + "1&section_mode=contest_free_award" + etc + showType, R.id.Main_NotyList, NotyAdapter, R.id.main_booklist_noty);
        BookList_C(root, "/v1/home/list.joa", "&token=" + userToken + "&section_mode=page_read_book" + etc + showType, R.id.Main_RecommendedList, RecommendAdapter, R.id.main_booklist_recommeded);

        Main_BookListAdapter_D NoblessTodayBestAdapter = new Main_BookListAdapter_D(items);
        Main_BookListAdapter_D PremiumToadyBestAdapter = new Main_BookListAdapter_D(items);
        Main_BookListAdapter_D CouponToadyBestAdapter = new Main_BookListAdapter_D(items);
        BookList_D(root, "/v1/home/list.joa", "&token=" + userToken + "&section_mode=todaybest&store=nobless&orderby=cnt_best" + etc + showType, R.id.Main_NoblessTodayBestList, NoblessTodayBestAdapter, R.id.main_nobelsstodaybest);
        BookList_D(root, "/v1/home/list.joa", "&token=" + userToken + "&section_mode=todaybest&store=premium&orderby=cnt_best" + etc + showType, R.id.Main_PremiumTodayBestList, PremiumToadyBestAdapter, R.id.main_premiumtodaybest);
        BookList_D(root, "/v1/home/list.joa", "&token=" + userToken + "&section_mode=support_coupon&orderby=cnt_best" + etc + showType, R.id.Main_CouponTodayBestList, CouponToadyBestAdapter, R.id.main_coupontodaybest);

        Fragment_Main fragmentMain = new Fragment_Main();

        wrap77Fes.setOnClickListener(v -> GotoMore(1, R.id.action_Fragment_Main_to_Fragment_New, fragmentMain));
        wrapKidamu.setOnClickListener(v -> GotoMore(2, R.id.action_Fragment_Main_to_Fragment_New, fragmentMain));
        wrapNOTY.setOnClickListener(v -> GotoMore(3, R.id.action_Fragment_Main_to_Fragment_New, fragmentMain));
        wrapPromised.setOnClickListener(v -> GotoMore(4, R.id.action_Fragment_Main_to_Fragment_New, fragmentMain));
        goToHistory.setOnClickListener(v -> GotoMore(1, R.id.action_Fragment_Main_to_Fragment_Fav, fragmentMain));
        goToFes.setOnClickListener(v -> GotoMore(1, R.id.action_Fragment_Main_to_Fragment_New, fragmentMain));
        goToPromised.setOnClickListener(v -> GotoMore(4, R.id.action_Fragment_Main_to_Fragment_New, fragmentMain));
        goToKidamu.setOnClickListener(v -> GotoMore(2, R.id.action_Fragment_Main_to_Fragment_New, fragmentMain));
        goToNoty.setOnClickListener(v -> GotoMore(3, R.id.action_Fragment_Main_to_Fragment_New, fragmentMain));

        bookSnipe.setOnClickListener(v -> GotoActivity("취향 저격", "/v1/book/recommend_list_api.joa", "&token=" + userToken + "&book_code=&offset=50"));
        userPicked.setOnClickListener(v -> GotoActivity("수상작", "/v1/book/list.joa", "&token=" + userToken + "&section_mode=page_read_book" + "&page=1&offset=50" + showType));
        bookRecommend.setOnClickListener(v -> GotoActivity("천만 인증", "/v1/home/list.joa", "&token=" + userToken + "&section_mode=page_read_book" + "&page=1&offset=50" + showType));

        return root;
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
                mainBanner.setLayoutParams(layoutParams);

                return true;
            }
        });

        Glide.with(requireActivity().getApplicationContext()).load(mainBannerURLs.get(position))
                .into(imageView);

    };

    ImageListener imageListenerMid = (position, imageView) -> {

        imageView.setAdjustViewBounds(true);

        ViewTreeObserver vtoMid = imageView.getViewTreeObserver();
        vtoMid.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);

                double doubled = (imageView.getMeasuredWidth() / 6);
                int finalHeight = Integer.parseInt(String.valueOf(Math.round(doubled)));

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        finalHeight);
                mainBannerMid.setLayoutParams(layoutParams);

                return true;
            }
        });

        Glide.with(requireActivity().getApplicationContext()).load(mainBannerMidURLs.get(position))
                .into(imageView);

    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainBanner.removeAllViews();
        mainBannerURLs = new ArrayList<>();
    }
}