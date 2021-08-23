package bigbigdw.joaradw.main;

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
import java.util.List;


import bigbigdw.joaradw.R;


public class OLD_MainBookListAdapterA extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<OLD_MainBookListData> listData = new ArrayList<>();
    String f = "FALSE";
    String t = "True";

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String value);
    }

    private OnItemClickListener listener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booklist_a, parent, false);
        return new OLD_MainBookListViewHolderA(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        OLD_MainBookListData item = listData.get(position);
        ConstraintLayout cover = ((OLD_MainBookListViewHolderA) holder).underCover;
        TextView text = ((OLD_MainBookListViewHolderA) holder).underCoverText;

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(((OLD_MainBookListViewHolderA) holder).image);

        ((OLD_MainBookListViewHolderA) holder).title.setText(listData.get(position).getTitle());
        ((OLD_MainBookListViewHolderA) holder).writer.setText(listData.get(position).getWriter());

        if (listData.get(position).getIsNobless().equals(t) && listData.get(position).getIsAdult().equals(f)) {
            textSetting(cover, text, R.string.NOBLESS, 0xAAa5c500);
            cover.setVisibility(View.VISIBLE);
        } else if (listData.get(position).getIsPremium().equals(t) && listData.get(position).getIsAdult().equals(f)) {
            textSetting(cover, text, R.string.PREMIUM, 0xAA4971EF);
            cover.setVisibility(View.VISIBLE);
        } else if (listData.get(position).getIsFinish().equals(t) && listData.get(position).getIsAdult().equals(f)) {
            textSetting(cover, text, R.string.FINISH, 0xAA767676);
            cover.setVisibility(View.VISIBLE);
        } else if (listData.get(position).getIsNobless().equals(t) && listData.get(position).getIsAdult().equals(t)) {
            textSetting(cover, text, R.string.ADULT_NOBLESS, 0xAAF44336);
            cover.setVisibility(View.VISIBLE);
        } else if (listData.get(position).getIsPremium().equals(t) && listData.get(position).getIsAdult().equals(t)) {
            textSetting(cover, text, R.string.ADULT_PREMIUM, 0xAA4971EF);
            cover.setVisibility(View.VISIBLE);
        } else if (listData.get(position).getIsFinish().equals(t) && listData.get(position).getIsAdult().equals(t)) {
            textSetting(cover, text, R.string.ADULT_FINISH, 0xAA767676);
            cover.setVisibility(View.VISIBLE);
        } else {
            cover.setVisibility(View.GONE);
        }

    }

    public void textSetting(ConstraintLayout cover, TextView text, int title, int color) {
        cover.setVisibility(View.VISIBLE);
        text.setText(title);
        text.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(List<OLD_MainBookListData> items) {
        this.listData = (ArrayList<OLD_MainBookListData>) items;
    }

    public OLD_MainBookListData getItem(int position) {
        return listData.get(position);
    }

    public class OLD_MainBookListViewHolderA extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView writer;
        TextView underCoverText;
        TextView bookCode;
        ConstraintLayout underCover;
        CardView imgWrap;

        OLD_MainBookListViewHolderA(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Img_BookA);
            title = itemView.findViewById(R.id.Text_TitleA);
            writer = itemView.findViewById(R.id.Text_WriterA);
            underCover = itemView.findViewById(R.id.BookImgUnderWrap);
            underCoverText = itemView.findViewById(R.id.UnderCoverText);
            bookCode = itemView.findViewById(R.id.BookCodeText);
            imgWrap = itemView.findViewById(R.id.Img_Wrap);

            imgWrap.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(v, pos, "BookDetail");
                }
            });

        }

    }

}
