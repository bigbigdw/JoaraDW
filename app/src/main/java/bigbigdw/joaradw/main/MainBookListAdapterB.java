package bigbigdw.joaradw.main;

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
import java.util.List;

import bigbigdw.joaradw.R;


public class MainBookListAdapterB extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<OLD_MainBookListData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_b, parent, false);
        return new MainBooklistviewholderB(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        OLD_MainBookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(((MainBooklistviewholderB) holder).image);

        ((MainBooklistviewholderB) holder).title.setText(listData.get(position).getTitle());
        ((MainBooklistviewholderB) holder).writer.setText(listData.get(position).getWriter());

        if (listData.get(position).getIsAdult().equals("TRUE")) {
            ((MainBooklistviewholderB) holder).underCover.setVisibility(View.VISIBLE);
        } else {
            ((MainBooklistviewholderB) holder).underCover.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(List<OLD_MainBookListData> items) {
        this.listData = (ArrayList<OLD_MainBookListData>) items;
    }

    static class MainBooklistviewholderB extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView writer;
        ConstraintLayout underCover;

        MainBooklistviewholderB(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Img_BookB);
            title = itemView.findViewById(R.id.Text_TitleB);
            writer = itemView.findViewById(R.id.Text_WriterB);
            underCover = itemView.findViewById(R.id.BookImgUnderWrap);
        }

    }

}
