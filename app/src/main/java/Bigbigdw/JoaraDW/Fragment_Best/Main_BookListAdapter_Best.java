package Bigbigdw.JoaraDW.Fragment_Best;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_Best extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Main_BookListData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_best, parent, false);
        return new Main_BookListAdapter_Best.Main_BookListViewHolder_Best(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Main_BookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(((Main_BookListAdapter_Best.Main_BookListViewHolder_Best) holder).Image);

        ((Main_BookListAdapter_Best.Main_BookListViewHolder_Best) holder).Title.setText(listData.get(position).getTitle());
        ((Main_BookListAdapter_Best.Main_BookListViewHolder_Best) holder).Writer.setText(listData.get(position).getWriter());
        ((Main_BookListAdapter_Best.Main_BookListViewHolder_Best) holder).Intro.setText(listData.get(position).getIntro());

        ((Main_BookListAdapter_Best.Main_BookListViewHolder_Best) holder).BestCount.setText(listData.get(position).getBestCount());
        ((Main_BookListAdapter_Best.Main_BookListViewHolder_Best) holder).BestViewCount.setText(listData.get(position).getBookViewed());
        ((Main_BookListAdapter_Best.Main_BookListViewHolder_Best) holder).BestFav.setText(listData.get(position).getBookFavCount());
        ((Main_BookListAdapter_Best.Main_BookListViewHolder_Best) holder).BestRecommend.setText(listData.get(position).getBookRecommend());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    static class Main_BookListViewHolder_Best extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;
        TextView Intro;
        TextView BestCount;
        TextView BestViewCount;
        TextView BestFav;
        TextView BestRecommend;


        Main_BookListViewHolder_Best(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookBest);
            Title = itemView.findViewById(R.id.Text_Title_Best);
            Writer = itemView.findViewById(R.id.Text_Writer_Best);

            BestCount = itemView.findViewById(R.id.Text_BestCount);
            BestViewCount = itemView.findViewById(R.id.Text_BestViewed);
            BestFav = itemView.findViewById(R.id.Text_BestFav);
            BestRecommend = itemView.findViewById(R.id.Text_BestRecommend);

            Intro = itemView.findViewById(R.id.Text_Intro_Best);

        }

    }

}