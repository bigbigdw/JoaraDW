package Bigbigdw.JoaraDW.Fragment_Finish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_Finish extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Main_BookListData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_new, parent, false);
        return new Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Main_BookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).Image);

        ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).Title.setText(listData.get(position).getTitle());
        ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).Writer.setText(listData.get(position).getWriter());
        ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).Intro.setText(listData.get(position).getIntro());

        if(listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")){
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setText(R.string.NOBLESS);
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setTextColor(0xAAa5c500);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")){
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setText(R.string.PREMIUM);
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setTextColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")){
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setText(R.string.FINISH);
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setTextColor(0xAA767676);
        } else if(listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setText(R.string.ADULT_NOBLESS);
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setTextColor(0xAAF44336);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setText(R.string.ADULT_PREMIUM);
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setTextColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")){
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setText(R.string.ADULT_FINISH);
            ((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder).TopText.setTextColor(0xAA767676);
        }

        if (listData.get(position).getIsFav().equals("TRUE")) {
            ((Main_BookListViewHolder_Finish) holder).Favon.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_Finish) holder).Favoff.setVisibility(View.GONE);
        } else {
            ((Main_BookListViewHolder_Finish) holder).Favoff.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_Finish) holder).Favon.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    static class Main_BookListViewHolder_Finish extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;
        TextView Intro;
        TextView TopText;
        ImageView Favon;
        ImageView Favoff;
        LinearLayout Img_Wrap;

        Main_BookListViewHolder_Finish(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_Book);
            Title = itemView.findViewById(R.id.Text_Title);
            Intro = itemView.findViewById(R.id.Text_Intro);
            Writer = itemView.findViewById(R.id.Text_Writer);
            TopText = itemView.findViewById(R.id.TopText);
            Favon = itemView.findViewById(R.id.FavON);
            Favoff = itemView.findViewById(R.id.FavOff);
            Img_Wrap = itemView.findViewById(R.id.Img_Wrap);

            Img_Wrap.setOnClickListener(v -> {
                if (Favoff.getVisibility() == View.VISIBLE) {
                    Favoff.setVisibility(View.GONE);
                    Favon.setVisibility(View.VISIBLE);
                    Toast.makeText(itemView.getContext(), "작품이 선호작에 등록되었습니다",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Favoff.setVisibility(View.VISIBLE);
                    Favon.setVisibility(View.GONE);
                    Toast.makeText(itemView.getContext(), "작품을 선호작에서 해제하였습니다",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}