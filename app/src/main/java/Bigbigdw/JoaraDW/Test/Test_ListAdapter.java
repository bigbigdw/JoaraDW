package Bigbigdw.JoaraDW.Test;

import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.R;

public class Test_ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Test_ListData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainbanners, parent, false);
        return new Test_ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Test_ListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(((Test_ListViewHolder) holder).Image);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(ArrayList<Test_ListData> items) {
        this.listData = items;
    }

    public void addItem(Test_ListData data) {
        listData.add(data);
    }

    class Test_ListViewHolder extends  RecyclerView.ViewHolder {

        ImageView Image;

        Test_ListViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.MainBanner_Img);
        }

    }

}

