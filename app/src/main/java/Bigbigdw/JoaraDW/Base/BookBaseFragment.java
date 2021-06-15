package Bigbigdw.JoaraDW.Base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Book_Pagination;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Etc.BookList;
import Bigbigdw.JoaraDW.Fragment_Main.Book_Page_Etc;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_A;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_D;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.JOARADW;

public class BookBaseFragment extends Fragment {

    String token = "";
    String status = "";
    String userName;
    Bundle bundle;

    public void CheckToken() {
        if (Config.GETUSERINFO() != null) {
            JSONObject GETUSERINFO = Config.GETUSERINFO();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                token = UserInfo.getString("token");
                status = GETUSERINFO.getString("status");
                userName = new String(UserInfo.getString("nickname").getBytes(), StandardCharsets.UTF_8);

                JOARADW app = (JOARADW) getActivity().getApplicationContext();
                app.setUSERSTATUS(status);
                app.setTOKEN(token);
                app.setUserName(userName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void BookList_A(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_A Adapter, Integer Wrap) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        BookList.BookList_A(root, API_URL, ETC, RecylerView, Adapter, queue, Wrap);
        Adapter.setOnItemClickListener((v, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            adapterListener(item, Value, Book_Detail_Cover.class, queue);
        });
    }

    public void BookList_C(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_C Adapter, Integer Wrap) {
        RequestQueue queue2 = Volley.newRequestQueue(getActivity());
        BookList.BookList_C(root, API_URL, ETC, RecylerView, Adapter, queue2, Wrap);
        Adapter.setOnItemClickListener((v, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            adapterListener(item, Value, Book_Detail_Cover.class, queue2);
        });
    }

    public void BookList_D(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_D Adapter, Integer Wrap) {
        RequestQueue queue3 = Volley.newRequestQueue(getActivity());
        BookList.BookList_D(root, API_URL, ETC, RecylerView, Adapter, queue3, Wrap);
        Adapter.setOnItemClickListener((v, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            adapterListener(item, Value, Book_Detail_Cover.class, queue3);
        });
    }

    public void adapterListener(Main_BookListData item, String Value, Class TYPE, RequestQueue queue) {
        if (Value.equals("FAV")) {
            Book_Pagination.FavToggle(queue, item.getBookCode(), token);
        } else if (Value.equals("BookDetail")) {
            Intent intent = new Intent(requireContext().getApplicationContext(), TYPE);
            intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
            intent.putExtra("token", String.format("%s", token));
            startActivity(intent);
        }
    }

    public void GotoActivity(String Title, String API_URL, String ETC_URL) {
        Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
        intent.putExtra("Title", String.format("%s", Title));
        intent.putExtra("API_URL", String.format("%s", API_URL));
        intent.putExtra("ETC_URL", String.format("%s", ETC_URL));
        startActivity(intent);
    }

    public void GotoMore(int Num, int Nav, Fragment fragment) {
        bundle = new Bundle();
        bundle.putInt("TabNum", Num);
        NavHostFragment.findNavController(fragment)
                .navigate(Nav, bundle);
    }


}
