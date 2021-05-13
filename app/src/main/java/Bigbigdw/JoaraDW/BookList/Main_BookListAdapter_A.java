package Bigbigdw.JoaraDW.BookList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_A extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements onClickAdapterListener_A {

    private ArrayList<Main_BookListData> listData = new ArrayList<>();
    onClickAdapterListener_A listener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_a, parent, false);
        return new Main_BookListAdapter_A.Main_BookListViewHolder_A(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Main_BookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(((Main_BookListAdapter_A.Main_BookListViewHolder_A) holder).Image);

        ((Main_BookListViewHolder_A) holder).Title.setText(listData.get(position).getTitle());
        ((Main_BookListViewHolder_A) holder).Writer.setText(listData.get(position).getWriter());

        if(listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")){
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setText(R.string.NOBLESS);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAAa5c500);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")){
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setText(R.string.PREMIUM);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")){
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setText(R.string.FINISH);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAA767676);
        } else if(listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setText(R.string.ADULT_NOBLESS);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAAF44336);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setText(R.string.ADULT_PREMIUM);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setText(R.string.ADULT_FINISH);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAA767676);
        } else {
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    public Main_BookListData getItem(int position) {
        return listData.get(position);
    }

    @Override
    public void onClickAdapter_A(Main_BookListViewHolder_A holder, View view, int position, String Value) {
        if (listener != null) {
            listener.onClickAdapter_A(holder, view, position, Value);
        }
    }

    public void setOnItemClicklistener(onClickAdapterListener_A listener) {
        this.listener = listener;
    }

    static class Main_BookListViewHolder_A extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;
        ConstraintLayout UnderCover;
        TextView UnderCoverText;
        CardView Img_Wrap;
        TextView BookCode;
        String Book_Code;

        Main_BookListViewHolder_A(@NonNull View itemView, final onClickAdapterListener_A listener) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookA);
            Title = itemView.findViewById(R.id.Text_TitleA);
            Writer = itemView.findViewById(R.id.Text_WriterA);
            UnderCover = itemView.findViewById(R.id.BookImgUnderWrap);
            UnderCoverText = itemView.findViewById(R.id.UnderCoverText);
            BookCode = itemView.findViewById(R.id.BookCodeText);
            Img_Wrap = itemView.findViewById(R.id.Img_Wrap);

            Img_Wrap.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onClickAdapter_A(Main_BookListAdapter_A.Main_BookListViewHolder_A.this, v, position, "BookDetail");
                }
            });

        }

    }

}
