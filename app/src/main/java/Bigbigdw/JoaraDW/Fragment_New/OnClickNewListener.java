package Bigbigdw.JoaraDW.Fragment_New;

import android.view.View;

import Bigbigdw.JoaraDW.BookList.Main_BookListAdapter_C;

public interface OnClickNewListener {
    void onItemClick(Main_BookListAdapter_C.Main_BookListViewHolder_C holder, View view, int position, String Value);
}


