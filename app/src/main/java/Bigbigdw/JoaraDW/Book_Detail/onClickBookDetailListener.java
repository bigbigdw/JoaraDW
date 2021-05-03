package Bigbigdw.JoaraDW.Book_Detail;

import android.view.View;

interface onClickBookDetailListener {
    void onClickBookList(Detail_BookListAdapter.Detail_BookList_ViewHolder holder, View view, int position, String Value);
}