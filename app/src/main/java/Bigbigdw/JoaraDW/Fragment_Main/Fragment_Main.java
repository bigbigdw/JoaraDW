package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import Bigbigdw.JoaraDW.Etc.BookList;
import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Book_Pagination;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_A;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_B;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_D;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Fragment_Main extends Fragment implements Main_BannerAPI {

    private final Main_BookListAdapter_A HistoryAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_A HobbyAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_A MDNovelAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_A MDWebtoonAdapter = new Main_BookListAdapter_A();
    private final Main_BookListAdapter_B FestivalAdapter = new Main_BookListAdapter_B();
    private final Main_BookListAdapter_B PromiseAdapter = new Main_BookListAdapter_B();
    private final Main_BookListAdapter_B KidamuAdapter = new Main_BookListAdapter_B();

    private RequestQueue queue;
    private final ArrayList<Main_BookListData> items = new ArrayList<>();

    CarouselView MainBanner, MainBannerMid;
    List<String> MainBannerURLs = new ArrayList<>();
    List<String> MainBannerMidURLs = new ArrayList<>();
    String TOKEN = "", STATUS = "", ETC = "&page=1&offset=10", ShowType = "&show_type=home";
    TextView UserNameCategory, GoToHistory, GoToFes, GoToPromised, GoToKidamu, GoToNoty, BookSnipe, UserPicked, BookRecommend;
    LinearLayout Wrap77Fes, WrapKidamu, WrapNOTY, WrapPromised;
    JSONObject GETUSERINFO = null;
    Bundle bundle;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        UserNameCategory = root.findViewById(R.id.UserNameCategory);
        BookSnipe = root.findViewById(R.id.BookSnipe);
        UserPicked = root.findViewById(R.id.UserPicked);
        BookRecommend = root.findViewById(R.id.BookRecommend);
        MainBanner = root.findViewById(R.id.Carousel_MainBanner);
        MainBannerMid = root.findViewById(R.id.Carousel_MainBanner_Mid);
        Wrap77Fes = root.findViewById(R.id.Wrap77Fes);
        WrapKidamu = root.findViewById(R.id.WrapKidamu);
        WrapNOTY = root.findViewById(R.id.WrapNOTY);
        WrapPromised = root.findViewById(R.id.WrapPromised);
        GoToHistory = root.findViewById(R.id.GoToHistory);
        GoToFes = root.findViewById(R.id.GoToFes);
        GoToPromised = root.findViewById(R.id.GoToPromised);
        GoToKidamu = root.findViewById(R.id.GoToKidamu);
        GoToNoty = root.findViewById(R.id.GoToNoty);


        if (Config.GETUSERINFO() != null) {
            GETUSERINFO = Config.GETUSERINFO();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
                STATUS = GETUSERINFO.getString("status");
                String usernamed = new String(UserInfo.getString("nickname").getBytes(), StandardCharsets.UTF_8);
                UserNameCategory.setText(usernamed);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        AssetManager assetManager = getActivity().getAssets();
        queue = Volley.newRequestQueue(getActivity());

        Main_BannerAPI.SetBanner(MainBannerMid, MainBannerMidURLs, queue, "&token=" + TOKEN, "&page=&banner_type=app_main2016_event");
        Main_BannerAPI.SetBanner(MainBanner, MainBannerURLs, queue, "&token=" + TOKEN, "&page=0&banner_type=app_home_top_banner");
        MainBanner.setImageListener(imageListener);
        MainBannerMid.setImageListener(imageListenerMid);

        if (STATUS.equals("1")) {
            BookList_A(root, "/v1/user/historybooks.joa", "&token=" + TOKEN + "&mem_time=0" + ETC, R.id.Main_HistoryBookList, HistoryAdapter, R.id.main_booklist_history);
            BookList_A(root, "/v1/book/recommend_list_api.joa", "&token=" + TOKEN + "&book_code=", R.id.Main_HobbyBookList, HobbyAdapter, R.id.main_booklist_hobby);
        }
        BookList_A(root, "/v1/home/list.joa", "&token=" + TOKEN + "&page=1&section_mode=recommend_book" + ETC, R.id.Main_MDNovelList, MDNovelAdapter, R.id.main_booklist_mdnovel);
        BookList.BookList_A_WebToon(root, "/v1/home/webtoon_list.joa", TOKEN, R.id.Main_MDWebtoonList, MDWebtoonAdapter, queue, R.id.main_booklist_mdwebtoon);

        BookList.BookList_B(root, assetManager, "Main_FestivalBookList.json", R.id.Main_FestivalBookList, FestivalAdapter);
        BookList.BookList_B(root, assetManager, "Main_PromisedBookList.json", R.id.Main_PromisedBookList, PromiseAdapter);
        BookList.BookList_B(root, assetManager, "Main_KidamuBookList.json", R.id.Main_KidamuBookList, KidamuAdapter);

        Main_BookListAdapter_C UserPickedAdapter = new Main_BookListAdapter_C(items);
        Main_BookListAdapter_C NotyAdapter = new Main_BookListAdapter_C(items);
        Main_BookListAdapter_C RecommendAdapter = new Main_BookListAdapter_C(items);
        BookList_C(root, "/v1/book/list.joa", "&token=" + TOKEN + "&section_mode=contest_free_award" + ETC + ShowType, R.id.Main_UserPickedList, UserPickedAdapter, queue, R.id.main_booklist_userpicked);
        BookList_C(root, "/v1/home/list.joa", "&token=" + TOKEN + "1&section_mode=contest_free_award" + ETC + ShowType, R.id.Main_NotyList, NotyAdapter, queue, R.id.main_booklist_noty);
        BookList_C(root, "/v1/home/list.joa", "&token=" + TOKEN + "&section_mode=page_read_book" + ETC + ShowType, R.id.Main_RecommendedList, RecommendAdapter, queue, R.id.main_booklist_recommeded);

        Main_BookListAdapter_D NoblessTodayBestAdapter = new Main_BookListAdapter_D(items);
        Main_BookListAdapter_D PremiumToadyBestAdapter = new Main_BookListAdapter_D(items);
        Main_BookListAdapter_D CouponToadyBestAdapter = new Main_BookListAdapter_D(items);
        BookList_D(root, "/v1/home/list.joa", "&token=" + TOKEN + "&section_mode=todaybest&store=nobless&orderby=cnt_best" + ETC + ShowType, R.id.Main_NoblessTodayBestList, NoblessTodayBestAdapter, queue, R.id.main_nobelsstodaybest);
        BookList_D(root, "/v1/home/list.joa", "&token=" + TOKEN + "&section_mode=todaybest&store=premium&orderby=cnt_best" + ETC + ShowType, R.id.Main_PremiumTodayBestList, PremiumToadyBestAdapter, queue, R.id.main_premiumtodaybest);
        BookList_D(root, "/v1/home/list.joa", "&token=" + TOKEN + "&section_mode=support_coupon&orderby=cnt_best" + ETC + ShowType, R.id.Main_CouponTodayBestList, CouponToadyBestAdapter, queue, R.id.main_coupontodaybest);

        bundle = new Bundle();

        Wrap77Fes.setOnClickListener(v -> GotoMore(1, R.id.action_Fragment_Main_to_Fragment_New));
        WrapKidamu.setOnClickListener(v -> GotoMore(2, R.id.action_Fragment_Main_to_Fragment_New));
        WrapNOTY.setOnClickListener(v -> GotoMore(3, R.id.action_Fragment_Main_to_Fragment_New));
        WrapPromised.setOnClickListener(v -> GotoMore(4, R.id.action_Fragment_Main_to_Fragment_New));
        GoToHistory.setOnClickListener(v -> GotoMore(1, R.id.action_Fragment_Main_to_Fragment_Fav));
        GoToFes.setOnClickListener(v -> GotoMore(1, R.id.action_Fragment_Main_to_Fragment_New));
        GoToPromised.setOnClickListener(v -> GotoMore(4, R.id.action_Fragment_Main_to_Fragment_New));
        GoToKidamu.setOnClickListener(v -> GotoMore(2, R.id.action_Fragment_Main_to_Fragment_New));
        GoToNoty.setOnClickListener(v -> GotoMore(3, R.id.action_Fragment_Main_to_Fragment_New));

        BookSnipe.setOnClickListener(v -> GotoActivity("취향 저격", "/v1/book/recommend_list_api.joa", "&token=" + TOKEN + "&book_code=&offset=50"));
        UserPicked.setOnClickListener(v -> GotoActivity("수상작", "/v1/book/list.joa", "&token=" + TOKEN + "&section_mode=page_read_book" + "&page=1&offset=50" + ShowType));
        BookRecommend.setOnClickListener(v -> GotoActivity("천만 인증", "/v1/home/list.joa", "&token=" + TOKEN + "&section_mode=page_read_book" + "&page=1&offset=50" + ShowType));

        return root;
    }

    public void BookList_A(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_A Adapter, Integer Wrap) {
        BookList.BookList_A(root, API_URL, ETC, RecylerView, Adapter, queue, Wrap);

        Adapter.setOnItemClickListener((v, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            AdapterListener(item, Value, Book_Detail_Cover.class);
        });
    }

    public void BookList_C(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_C Adapter, RequestQueue queue, Integer Wrap) {
        BookList.BookList_C(root, API_URL, ETC, RecylerView, Adapter, queue, Wrap);

        Adapter.setOnItemClickListener((v, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            AdapterListener(item, Value, Book_Detail_Cover.class);
        });
    }

    public void BookList_D(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_D Adapter, RequestQueue queue, Integer Wrap) {
        BookList.BookList_D(root, API_URL, ETC, RecylerView, Adapter, queue, Wrap);

        Adapter.setOnItemClickListener((v, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            AdapterListener(item, Value, Book_Detail_Cover.class);
        });
    }

    public void GotoActivity(String Title, String API_URL, String ETC_URL) {
        Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
        intent.putExtra("Title", String.format("%s", Title));
        intent.putExtra("API_URL", String.format("%s", API_URL));
        intent.putExtra("ETC_URL", String.format("%s", ETC_URL));
        startActivity(intent);
    }

    public void GotoMore(int Num, int Nav) {
        bundle.putInt("TabNum", Num);
        NavHostFragment.findNavController(Fragment_Main.this)
                .navigate(Nav, bundle);
    }

    public void AdapterListener(Main_BookListData item, String Value, Class TYPE) {
        if (Value.equals("FAV")) {
            Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
        } else if (Value.equals("BookDetail")) {
            Intent intent = new Intent(requireContext().getApplicationContext(), TYPE);
            intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
            intent.putExtra("TOKEN", String.format("%s", TOKEN));
            startActivity(intent);
        }
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

                double doubled = (imageView.getMeasuredWidth() / 6);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainBanner.removeAllViews();
        MainBannerURLs = new ArrayList<>();
    }
}