package Bigbigdw.JoaraDW.BookList;

import android.view.View;

import Bigbigdw.JoaraDW.Fragment_Fav.Main_BookListAdapter_Fav;

interface onClickAdapterListener_A {
    void onClickAdapter_A(Main_BookListAdapter_A.Main_BookListViewHolder_A holder, View view, int position, String Value);
}

interface onClickAdapterListener_C {
    void onClickAdapter_C(Main_BookListAdapter_C.Main_BookListViewHolder_C holder, View view, int position, String Value);
}

interface onClickAdapterListenerZ {
    void onClickAdapterListener_A(Main_BookListAdapter_A.Main_BookListViewHolder_A holder, View view, int position, String Value);
    void onClickAdapterListener_C(Main_BookListAdapter_C.Main_BookListViewHolder_C holder, View view, int position, String Value);
    void onClickAdapterListener_D(Main_BookListAdapter_C.Main_BookListViewHolder_C holder, View view, int position, String Value);
    void onClickAdapterListener_New(Main_BookListAdapter_C.Main_BookListViewHolder_C holder, View view, int position, String Value);
    void onClickAdapterListener_Finish(Main_BookListAdapter_C.Main_BookListViewHolder_C holder, View view, int position, String Value);
    void onClickAdapterListener_Fav(Main_BookListAdapter_Fav.Main_BookListViewHolder_New holder, View view, int position, String Value);
}