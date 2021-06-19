package bigbigdw.joaradw.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import bigbigdw.joaradw.book_detail.BookDetailCover;
import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.etc.BookList;
import bigbigdw.joaradw.fragment_main.BookPageEtc;
import bigbigdw.joaradw.main.MainBookListAdapterA;
import bigbigdw.joaradw.main.MainBookListAdapterC;
import bigbigdw.joaradw.main.MainBookListAdapterD;
import bigbigdw.joaradw.main.MainBookListData;
import bigbigdw.joaradw.JOARADW;

public class BookBaseFragment extends Fragment {

    String token = "";
    String status = "";
    String name;
    String mana;
    String expireCash;
    String cash;
    String manuscriptCoupon;
    String supportCoupon;
    Bundle bundle;

    public void checkToken() {
        if (Config.getuserinfo() != null) {
            JSONObject getUserInfo = Config.getuserinfo();
            JSONObject userInfo;
            try {
                userInfo = getUserInfo.getJSONObject("user");
                token = userInfo.getString("token");
                status = getUserInfo.getString("status");
                name = new String(userInfo.getString("nickname").getBytes(), StandardCharsets.UTF_8);
                mana = userInfo.getString("mana");
                expireCash = userInfo.getString("expire_cash");
                cash = userInfo.getString("cash");
                manuscriptCoupon = userInfo.getString("manuscript_coupon");
                supportCoupon = userInfo.getString("support_coupon");

                JOARADW app = (JOARADW) requireActivity().getApplicationContext();
                app.setStatus(status);
                app.setToken(token);
                app.setName(name);
                app.setMana(mana);
                app.setExpireCash(expireCash);
                app.setCash(cash);
                app.setManuscriptCoupon(manuscriptCoupon);
                app.setSupportCoupon(supportCoupon);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void bookListA(View root, String apiUrl, String etc, Integer recylerView, MainBookListAdapterA adapter, Integer wrap) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        BookList.bookListA(root, apiUrl, etc, recylerView, adapter, queue, wrap);
        adapter.setOnItemClickListener((v, position, value) -> {
            MainBookListData item = adapter.getItem(position);
            adapterListener(item, value, queue);
        });
    }

    public void bookListC(View root, String apiUrl, String etc, Integer recylerView, MainBookListAdapterC adapter, Integer wrap) {
        RequestQueue queue2 = Volley.newRequestQueue(requireActivity());
        BookList.bookListC(root, apiUrl, etc, recylerView, adapter, queue2, wrap);
        adapter.setOnItemClickListener((v, position, value) -> {
            MainBookListData item = adapter.getItem(position);
            adapterListener(item, value, queue2);
        });
    }

    public void bookListD(View root, String apiUrl, String etc, Integer recylerView, MainBookListAdapterD adapter, Integer wrap) {
        RequestQueue queue3 = Volley.newRequestQueue(requireActivity());
        BookList.bookListD(root, apiUrl, etc, recylerView, adapter, queue3, wrap);
        adapter.setOnItemClickListener((v, position, value) -> {
            MainBookListData item = adapter.getItem(position);
            adapterListener(item, value, queue3);
        });
    }

    public void adapterListener(MainBookListData item, String value, RequestQueue queue) {
        if (value.equals("FAV")) {
            BookPagination.favToggle(queue, item.getBookCode(), token);
        } else if (value.equals("BookDetail")) {
            Intent intent = new Intent(requireContext().getApplicationContext(), BookDetailCover.class);
            intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
            intent.putExtra("token", String.format("%s", token));
            startActivity(intent);
        }
    }

    public void goToBookPageEtc(String title, String apiUrl, String etcURL) {
        Intent intent = new Intent(requireContext().getApplicationContext(), BookPageEtc.class);
        intent.putExtra("Title", String.format("%s", title));
        intent.putExtra("API_URL", String.format("%s", apiUrl));
        intent.putExtra("ETC_URL", String.format("%s", etcURL));
        startActivity(intent);
    }

    public void gotoMore(int num, int nav, Fragment fragment) {
        bundle = new Bundle();
        bundle.putInt("TabNum", num);
        NavHostFragment.findNavController(fragment)
                .navigate(nav, bundle);
    }

    public void gotoURL(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }


}
