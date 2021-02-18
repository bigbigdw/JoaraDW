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

    private ArrayList<Main_BookListData_C> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklist_c, parent, false);
        return new Main_BookListAdapter_C.Main_BookListViewHolder_C(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Main_BookListData_C item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(((Main_BookListAdapter_C.Main_BookListViewHolder_C) holder).Image);

        ((Main_BookListViewHolder_C) holder).Title.setText(listData.get(position).getTitle());
        ((Main_BookListViewHolder_C) holder).Writer.setText(listData.get(position).getWriter());
        ((Main_BookListViewHolder_C) holder).Intro.setText(listData.get(position).getIntro());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(ArrayList<Main_BookListData_C> items) {
        this.listData = items;
    }

    static class Main_BookListViewHolder_C extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;
        TextView Intro;

        Main_BookListViewHolder_C(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookC);
            Title = itemView.findViewById(R.id.Text_TitleC);
            Intro = itemView.findViewById(R.id.Text_IntroC);
            Writer = itemView.findViewById(R.id.Text_WriterC);
        }

    }

}
