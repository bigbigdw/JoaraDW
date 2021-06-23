package bigbigdw.joaradw.fragment_main;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;
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

import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.base.BookBaseFragment;
import bigbigdw.joaradw.etc.BookList;
import bigbigdw.joaradw.main.MainBookListAdapterA;
import bigbigdw.joaradw.main.MainBookListAdapterB;
import bigbigdw.joaradw.main.MainBookListAdapterC;
import bigbigdw.joaradw.main.MainBookListAdapterD;
import bigbigdw.joaradw.main.MainBookListData;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.R;


public class FragmentMain extends BookBaseFragment implements InterfaceMainBannerAPI {

    private final MainBookListAdapterA historyAdapter = new MainBookListAdapterA();
    private final MainBookListAdapterA hobbyAdapter = new MainBookListAdapterA();
    private final MainBookListAdapterA mdNovelAdapter = new MainBookListAdapterA();
    private final MainBookListAdapterA mdWebtoonAdapter = new MainBookListAdapterA();
    private final MainBookListAdapterB festivalAdapter = new MainBookListAdapterB();
    private final MainBookListAdapterB promiseAdapter = new MainBookListAdapterB();
    private final MainBookListAdapterB kidamuAdapter = new MainBookListAdapterB();

    private final ArrayList<MainBookListData> items = new ArrayList<>();

    CarouselView mainBanner;
    CarouselView mainBannerMid;
    List<String> mainBannerURLs = new ArrayList<>();
    List<String> mainBannerMidURLs = new ArrayList<>();
    String userToken = "";
    String userStatus = "";
    String paramToken;
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

        checkToken();

        JOARADW app = (JOARADW) requireActivity().getApplicationContext();
        userToken = app.getToken();
        userStatus = app.getStatus();
        userNameCategory.setText(app.getName());
        paramToken = "&token=" + userToken;

        setLayout(root);

        return root;
    }

    public void setLayout(View root) {

        AssetManager assetManager = requireActivity().getAssets();
        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        String bookC = paramToken + "&section_mode=contest_free_award" + etc + showType;
        String resultEtcUrl = paramToken + "&book_code=&offset=50";
        String resultEtcUrlSection = paramToken + API.SECTION_MODE + "&page=1&offset=50" + showType;

        InterfaceMainBannerAPI.setBanner(mainBannerMid, mainBannerMidURLs, queue, paramToken, "&page=&banner_type=app_main2016_event");
        InterfaceMainBannerAPI.setBanner(mainBanner, mainBannerURLs, queue, paramToken, "&page=0&banner_type=app_home_top_banner");
        mainBanner.setImageListener(imageListener);
        mainBannerMid.setImageListener(imageListenerMid);

        if (userStatus.equals("1")) {
            bookListA(root, API.USER_HISTORYBOOKS_JOA, paramToken + "&mem_time=0" + etc, R.id.Main_HistoryBookList, historyAdapter, R.id.main_booklist_history);
            bookListA(root, API.BOOK_RECOMMEND_LIST_API_JOA, paramToken + "&book_code=", R.id.Main_HobbyBookList, hobbyAdapter, R.id.main_booklist_hobby);
        }
        bookListA(root, API.HOME_LIST_JOA, paramToken + "&page=1&section_mode=recommend_book" + etc, R.id.Main_MDNovelList, mdNovelAdapter, R.id.main_booklist_mdnovel);
        BookList.bookListAWebToon(root, API.HOME_WEBTOON_LIST_JOA, userToken, R.id.Main_MDWebtoonList, mdWebtoonAdapter, queue, R.id.main_booklist_mdwebtoon);

        BookList.bookListB(root, assetManager, "Main_FestivalBookList.json", R.id.Main_FestivalBookList, festivalAdapter);
        BookList.bookListB(root, assetManager, "Main_PromisedBookList.json", R.id.Main_PromisedBookList, promiseAdapter);
        BookList.bookListB(root, assetManager, "Main_KidamuBookList.json", R.id.Main_KidamuBookList, kidamuAdapter);

        MainBookListAdapterC userPickedAdapter = new MainBookListAdapterC(items);
        MainBookListAdapterC notyAdapter = new MainBookListAdapterC(items);
        MainBookListAdapterC recommendAdapter = new MainBookListAdapterC(items);

        bookListC(root, API.HOME_LIST_JOA, paramToken + "&section_mode=contest_free_award" + etc + showType, R.id.Main_UserPickedList, userPickedAdapter, R.id.main_booklist_userpicked);
        bookListC(root, API.HOME_LIST_JOA, paramToken + "&section_mode=noty" + etc + showType, R.id.Main_NotyList, notyAdapter, R.id.main_booklist_noty);
        bookListC(root, API.HOME_LIST_JOA, paramToken + API.SECTION_MODE + etc + showType, R.id.Main_RecommendedList, recommendAdapter, R.id.main_booklist_recommeded);

        MainBookListAdapterD noblessTodayBestAdapter = new MainBookListAdapterD(items);
        MainBookListAdapterD premiumToadyBestAdapter = new MainBookListAdapterD(items);
        MainBookListAdapterD couponToadyBestAdapter = new MainBookListAdapterD(items);


        bookListD(root, API.HOME_LIST_JOA, paramToken + "&section_mode=todaybest&store=nobless&orderby=cnt_best" + etc + showType, R.id.Main_NoblessTodayBestList, noblessTodayBestAdapter, R.id.main_nobelsstodaybest);
        bookListD(root, API.HOME_LIST_JOA, paramToken + "&section_mode=todaybest&store=premium&orderby=cnt_best" + etc + showType, R.id.Main_PremiumTodayBestList, premiumToadyBestAdapter, R.id.main_premiumtodaybest);
        bookListD(root, API.HOME_LIST_JOA, paramToken + "&section_mode=support_coupon&orderby=cnt_best" + etc + showType, R.id.Main_CouponTodayBestList, couponToadyBestAdapter, R.id.main_coupontodaybest);

        wrap77Fes.setOnClickListener(v -> gotoMore(1, R.id.action_Fragment_Main_to_Fragment_New, FragmentMain.this));
        wrapKidamu.setOnClickListener(v -> gotoMore(2, R.id.action_Fragment_Main_to_Fragment_New, FragmentMain.this));
        wrapNOTY.setOnClickListener(v -> gotoMore(3, R.id.action_Fragment_Main_to_Fragment_New, FragmentMain.this));
        wrapPromised.setOnClickListener(v -> gotoMore(4, R.id.action_Fragment_Main_to_Fragment_New, FragmentMain.this));
        goToHistory.setOnClickListener(v -> gotoMore(1, R.id.action_Fragment_Main_to_Fragment_Fav, FragmentMain.this));
        goToFes.setOnClickListener(v -> gotoMore(1, R.id.action_Fragment_Main_to_Fragment_New, FragmentMain.this));
        goToPromised.setOnClickListener(v -> gotoMore(4, R.id.action_Fragment_Main_to_Fragment_New, FragmentMain.this));
        goToKidamu.setOnClickListener(v -> gotoMore(2, R.id.action_Fragment_Main_to_Fragment_New, FragmentMain.this));
        goToNoty.setOnClickListener(v -> gotoMore(3, R.id.action_Fragment_Main_to_Fragment_New, FragmentMain.this));

        bookSnipe.setOnClickListener(v -> goToBookPageEtc("취향 저격", API.BOOK_RECOMMEND_LIST_API_JOA, resultEtcUrl));
        userPicked.setOnClickListener(v -> goToBookPageEtc("수상작", API.HOME_LIST_JOA, resultEtcUrlSection));
        bookRecommend.setOnClickListener(v -> goToBookPageEtc("천만 인증", API.HOME_LIST_JOA, resultEtcUrlSection));
    }

    ImageListener imageListener = (position, imageView) -> {
        imageView.setAdjustViewBounds(true);

        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);

                double doubled = (imageView.getMeasuredWidth() / 1.704) + 5;
                int finalHeight = Integer.parseInt(String.valueOf(Math.round(doubled)));

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
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

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
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