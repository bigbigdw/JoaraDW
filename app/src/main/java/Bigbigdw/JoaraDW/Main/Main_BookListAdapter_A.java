package Bigbigdw.JoaraDW.Main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_A extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Main_BookListData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_a, parent, false);
        return new Main_BookListAdapter_A.Main_BookListViewHolder_A(view);
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
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAAa5c500);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")){
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setText(R.string.FINISH);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAAa5c500);
        } else if(listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setText(R.string.ADULT_NOBLESS);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAAF44336);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setText(R.string.ADULT_PREMIUM);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAAF44336);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListViewHolder_A) holder).UnderCover.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setText(R.string.ADULT_FINISH);
            ((Main_BookListViewHolder_A) holder).UnderCoverText.setTextColor(0xAAF44336);
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

    static class Main_BookListViewHolder_A extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;
        ConstraintLayout UnderCover;
        TextView UnderCoverText;

        Main_BookListViewHolder_A(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookA);
            Title = itemView.findViewById(R.id.Text_TitleA);
            Writer = itemView.findViewById(R.id.Text_WriterA);
            UnderCover = itemView.findViewById(R.id.BookImgUnderWrap);
            UnderCoverText = itemView.findViewById(R.id.UnderCoverText);
        }

    }

}
