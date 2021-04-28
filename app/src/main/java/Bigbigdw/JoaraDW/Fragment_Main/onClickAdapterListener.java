package Bigbigdw.JoaraDW.Fragment_Main;

import android.view.View;

import Bigbigdw.JoaraDW.Fragment_Fav.Main_BookListAdapter_Fav;
import Bigbigdw.JoaraDW.Fragment_Finish.Main_BookListAdapter_Finish;
import Bigbigdw.JoaraDW.Fragment_New.Main_BookListAdapter_New;

interface onClickAdapterListener_A {
    void onClickAdapter_A(Main_BookListAdapter_A.Main_BookListViewHolder_A holder, View view, int position, String Value);
}

interface onClickAdapterListenerC {
    void onClickAdapterListener_A(Main_BookListAdapter_A.Main_BookListViewHolder_A holder, View view, int position, String Value);
    void onClickAdapterListener_C(Main_BookListAdapter_New.Main_BookListViewHolder_New holder, View view, int position, String Value);
    void onClickAdapterListener_D(Main_BookListAdapter_New.Main_BookListViewHolder_New holder, View view, int position, String Value);
    void onClickAdapterListener_New(Main_BookListAdapter_New.Main_BookListViewHolder_New holder, View view, int position, String Value);
    void onClickAdapterListener_Finish(Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish holder, View view, int position, String Value);
    void onClickAdapterListener_Fav(Main_BookListAdapter_Fav.Main_BookListViewHolder_New holder, View view, int position, String Value);
}
