package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail;
import Bigbigdw.JoaraDW.Main.Main_BookData_JSON;
import Bigbigdw.JoaraDW.Main.Main_BookData_Webtoon;
import Bigbigdw.JoaraDW.Main.Main_BookData;
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
    private final Main_BookListAdapter_C UserPickedAdapter = new Main_BookListAdapter_C();
    private final Main_BookListAdapter_C NotyAdapter = new Main_BookListAdapter_C();
    private final Main_BookListAdapter_C RecommendAdapter = new Main_BookListAdapter_C();
    private final Main_BookListAdapter_D NoblessTodayBestAdapter = new Main_BookListAdapter_D();
    private final Main_BookListAdapter_D PremiumToadyBestAdapter = new Main_BookListAdapter_D();
    private final Main_BookListAdapter_D CouponToadyBestAdapter = new Main_BookListAdapter_D();
    private RequestQueue queue;

    CarouselView MainBanner;
    List<String> MainBannerURLs = new ArrayList<>();
    CarouselView MainBannerMid;
    List<String> MainBannerMidURLs = new ArrayList<>();
    String USERTOKEN = "&token=";
    String STATUS = "";
    String ETC = "&page=1&offset=10";
    String ShowType = "&show_type=home";
    String Category = "&category=";
    TextView UserNameCategory, GoToHistory, GoToFes, GoToPromised, GoToKidamu, GoToNoty;
    LinearLayout Wrap77Fes, WrapKidamu, WrapNOTY, WrapPromised;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        UserNameCategory = root.findViewById(R.id.UserNameCategory);

        try {
            FileReader fr = new FileReader(getActivity().getDataDir() + "/userInfo.json");
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            br.close();
            String result = sb.toString();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject UserInfo = jsonObject.getJSONObject("user");
            USERTOKEN = "&token=" + UserInfo.getString("token");
            Category = "&token=" + UserInfo.getString("category");
            STATUS = jsonObject.getString("status");
            String usernamed = new String(UserInfo.getString("nickname").getBytes(), StandardCharsets.UTF_8);
            UserNameCategory.setText(usernamed);
            Log.d("USERINFO", "읽기 완료");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("USERINFO", "읽기 실패");
        }


        AssetManager assetManager = getActivity().getAssets();
        queue = Volley.newRequestQueue(getActivity());

        MainBanner = root.findViewById(R.id.Carousel_MainBanner);
        MainBannerMid = root.findViewById(R.id.Carousel_MainBanner_Mid);

        Main_BannerAPI.SetBanner(MainBannerMid, MainBannerMidURLs, queue, USERTOKEN, "&page=&banner_type=app_main2016_event");
        Main_BannerAPI.SetBanner(MainBanner, MainBannerURLs, queue, USERTOKEN, "&page=0&banner_type=app_home_top_banner");

        MainBanner.setSlideInterval(4000);
        MainBanner.setImageListener(imageListener);
        MainBanner.setVisibility(View.VISIBLE);

        MainBannerMid.setSlideInterval(4000);
        MainBannerMid.setImageListener(imageListenerMid);
        MainBannerMid.setVisibility(View.VISIBLE);


        if (STATUS.equals("1")) {
            BookList_A(root, "/v1/user/historybooks.joa", USERTOKEN + "&mem_time=0" + ETC, R.id.Main_HistoryBookList, HistoryAdapter, queue, R.id.main_booklist_history);
            BookList_A(root, "/v1/book/recommend_list_api.joa", USERTOKEN + "&book_code=", R.id.Main_HobbyBookList, HobbyAdapter, queue, R.id.main_booklist_hobby);
        }

        BookList_A(root, "/v1/home/list.joa", USERTOKEN + "&page=1&section_mode=recommend_book" + ETC, R.id.Main_MDNovelList, MDNovelAdapter, queue, R.id.main_booklist_mdnovel);
        BookList_A_WebToon(root, "/v1/home/webtoon_list.joa", USERTOKEN, R.id.Main_MDWebtoonList, MDWebtoonAdapter, queue, R.id.main_booklist_mdwebtoon);
        BookList_B(root, assetManager, "Main_FestivalBookList.json", R.id.Main_FestivalBookList, FestivalAdapter);
        BookList_B(root, assetManager, "Main_PromisedBookList.json" , R.id.Main_PromisedBookList, PromiseAdapter);
        BookList_B(root, assetManager, "Main_KidamuBookList.json" , R.id.Main_KidamuBookList, KidamuAdapter);
        BookList_C(root, "/v1/book/list.joa", USERTOKEN + "&section_mode=contest_free_award" + ETC + ShowType, R.id.Main_UserPickedList, UserPickedAdapter, queue, R.id.main_booklist_userpicked);
        BookList_C(root, "/v1/home/list.joa", USERTOKEN + "1&section_mode=contest_free_award" + ETC + ShowType, R.id.Main_NotyList, NotyAdapter, queue, R.id.main_booklist_noty);
        BookList_C(root, "/v1/home/list.joa", USERTOKEN + "&section_mode=page_read_book" + ETC + ShowType, R.id.Main_RecommendedList, RecommendAdapter, queue, R.id.main_booklist_recommeded);
        BookList_D(root, "/v1/home/list.joa", USERTOKEN + "&section_mode=todaybest&store=nobless&orderby=cnt_best" + ETC + ShowType, R.id.Main_NoblessTodayBestList, NoblessTodayBestAdapter, queue, R.id.main_nobelsstodaybest);
        BookList_D(root, "/v1/home/list.joa", USERTOKEN + "&section_mode=todaybest&store=premium&orderby=cnt_best" + ETC + ShowType, R.id.Main_PremiumTodayBestList, PremiumToadyBestAdapter, queue, R.id.main_premiumtodaybest);
        BookList_D(root, "/v1/home/list.joa", USERTOKEN + "&section_mode=support_coupon&orderby=cnt_best" + ETC + ShowType, R.id.Main_CouponTodayBestList, CouponToadyBestAdapter, queue, R.id.main_coupontodaybest);

        Wrap77Fes = root.findViewById(R.id.Wrap77Fes);
        WrapKidamu = root.findViewById(R.id.WrapKidamu);
        WrapNOTY = root.findViewById(R.id.WrapNOTY);
        WrapPromised = root.findViewById(R.id.WrapPromised);
        GoToHistory = root.findViewById(R.id.GoToHistory);
        GoToFes = root.findViewById(R.id.GoToFes);
        GoToPromised = root.findViewById(R.id.GoToPromised);
        GoToKidamu = root.findViewById(R.id.GoToKidamu);
        GoToNoty = root.findViewById(R.id.GoToNoty);


        Bundle bundle = new Bundle();

        Wrap77Fes.setOnClickListener(v -> {
            bundle.putInt("TabNum", 1);
            NavHostFragment.findNavController(Fragment_Main.this)
                    .navigate(R.id.action_Fragment_Main_to_Fragment_New, bundle);
        });

        WrapKidamu.setOnClickListener(v -> {
            bundle.putInt("TabNum", 2);
            NavHostFragment.findNavController(Fragment_Main.this)
                    .navigate(R.id.action_Fragment_Main_to_Fragment_New, bundle);
        });

        WrapNOTY.setOnClickListener(v -> {
            bundle.putInt("TabNum", 3);
            NavHostFragment.findNavController(Fragment_Main.this)
                    .navigate(R.id.action_Fragment_Main_to_Fragment_New, bundle);
        });

        WrapPromised.setOnClickListener(v -> {
            bundle.putInt("TabNum", 4);
            NavHostFragment.findNavController(Fragment_Main.this)
                    .navigate(R.id.action_Fragment_Main_to_Fragment_New, bundle);
        });

        GoToHistory.setOnClickListener(v -> {
            bundle.putInt("TabNum", 1);
            NavHostFragment.findNavController(Fragment_Main.this)
                    .navigate(R.id.action_Fragment_Main_to_Fragment_Fav, bundle);
        });

        GoToFes.setOnClickListener(v -> {
            bundle.putInt("TabNum", 1);
            NavHostFragment.findNavController(Fragment_Main.this)
                    .navigate(R.id.action_Fragment_Main_to_Fragment_New, bundle);
        });

        GoToPromised.setOnClickListener(v -> {
            bundle.putInt("TabNum", 4);
            NavHostFragment.findNavController(Fragment_Main.this)
                    .navigate(R.id.action_Fragment_Main_to_Fragment_New, bundle);
        });

        GoToKidamu.setOnClickListener(v -> {
            bundle.putInt("TabNum", 4);
            NavHostFragment.findNavController(Fragment_Main.this)
                    .navigate(R.id.action_Fragment_Main_to_Fragment_New, bundle);
        });

        GoToNoty.setOnClickListener(v -> {
            bundle.putInt("TabNum", 3);
            NavHostFragment.findNavController(Fragment_Main.this)
                    .navigate(R.id.action_Fragment_Main_to_Fragment_New, bundle);
        });


        return root;
    }

    public void BookList_A(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_A Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC, queue, wrap));
        Adapter.notifyDataSetChanged();

        Adapter.setOnItemClicklistener((holder, view, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Detail.class);
            intent.putExtra("BookCode",String.format("%s", item.getBookCode()));
            intent.putExtra("TOKEN",String.format("%s", USERTOKEN));
            startActivity(intent);
        });
    }

    public void BookList_B(View root, AssetManager assetManager, String BookType,  Integer RecylerView, Main_BookListAdapter_B Adapter) {
        RecyclerView recyclerViewFestival = root.findViewById(RecylerView);
        LinearLayoutManager managerFestival = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFestival.setLayoutManager(managerFestival);
        recyclerViewFestival.setAdapter(Adapter);
        Adapter.setItems(new Main_BookData_JSON().getData(assetManager, BookType));
        Adapter.notifyDataSetChanged();
    }

    public void BookList_C(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_C Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC, queue, wrap));
        Adapter.notifyDataSetChanged();
    }

    public void BookList_D(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_D Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC, queue, wrap));
        Adapter.notifyDataSetChanged();
    }

    public void BookList_A_WebToon(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_A Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Main_BookData_Webtoon().getData(API_URL, ETC, queue, wrap));
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
        Log.d("onDestroyView", "파괴!");
        MainBanner.removeAllViews();
        MainBannerURLs = new ArrayList<>();
    }
}
