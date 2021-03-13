package Bigbigdw.JoaraDW.Main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_C extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Main_BookListData_A> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklist_c, parent, false);
        return new Main_BookListAdapter_C.Main_BookListViewHolder_C(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Main_BookListData_A item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(((Main_BookListAdapter_C.Main_BookListViewHolder_C) holder).Image);

        ((Main_BookListViewHolder_C) holder).Title.setText(listData.get(position).getTitle());
        ((Main_BookListViewHolder_C) holder).Writer.setText(listData.get(position).getWriter());
        ((Main_BookListViewHolder_C) holder).Intro.setText(listData.get(position).getIntro());

        if(listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")){
            ((Main_BookListViewHolder_C) holder).TopText.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_C) holder).TopText.setText(R.string.NOBLESS);
            ((Main_BookListViewHolder_C) holder).TopText.setTextColor(0xAAa5c500);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")){
            ((Main_BookListViewHolder_C) holder).TopText.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_C) holder).TopText.setText(R.string.PREMIUM);
            ((Main_BookListViewHolder_C) holder).TopText.setTextColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")){
            ((Main_BookListViewHolder_C) holder).TopText.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_C) holder).TopText.setText(R.string.FINISH);
            ((Main_BookListViewHolder_C) holder).TopText.setTextColor(0xAA767676);
        } else if(listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListViewHolder_C) holder).TopText.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_C) holder).TopText.setText(R.string.ADULT_NOBLESS);
            ((Main_BookListViewHolder_C) holder).TopText.setTextColor(0xAAF44336);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListViewHolder_C) holder).TopText.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_C) holder).TopText.setText(R.string.ADULT_PREMIUM);
            ((Main_BookListViewHolder_C) holder).TopText.setTextColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListViewHolder_C) holder).TopText.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_C) holder).TopText.setText(R.string.ADULT_FINISH);
            ((Main_BookListViewHolder_C) holder).TopText.setTextColor(0xAA767676);
        } else {
            ((Main_BookListViewHolder_C) holder).TopText.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(ArrayList<Main_BookListData_A> items) {
        this.listData = items;
    }

    static class Main_BookListViewHolder_C extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;
        TextView Intro;
        TextView TopText;

        Main_BookListViewHolder_C(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookC);
            Title = itemView.findViewById(R.id.Text_TitleC);
            Intro = itemView.findViewById(R.id.Text_IntroC);
            Writer = itemView.findViewById(R.id.Text_WriterC);
            TopText = itemView.findViewById(R.id.TopText);
        }

    }

}
