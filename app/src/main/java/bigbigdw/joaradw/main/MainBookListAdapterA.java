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


import bigbigdw.joaradw.R;


public class MainBookListAdapterA extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Main_BookListData> listData = new ArrayList<>();
    String False = "FALSE";
    String True = "True";

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String Value);
    }

    private OnItemClickListener Listener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.Listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_a, parent, false);
        return new MainBookListAdapterA.Main_BookListViewHolder_A(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Main_BookListData item = listData.get(position);
        ConstraintLayout Cover = ((Main_BookListViewHolder_A) holder).UnderCover;
        TextView Text = ((Main_BookListViewHolder_A) holder).UnderCoverText;

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(((MainBookListAdapterA.Main_BookListViewHolder_A) holder).Image);

        ((Main_BookListViewHolder_A) holder).Title.setText(listData.get(position).getTitle());
        ((Main_BookListViewHolder_A) holder).Writer.setText(listData.get(position).getWriter());

        if (listData.get(position).getIsNobless().equals(True) && listData.get(position).getIsAdult().equals(False)) {
            TextSetting(Cover, Text, R.string.NOBLESS, 0xAAa5c500);
        } else if (listData.get(position).getIsPremium().equals(True) && listData.get(position).getIsAdult().equals(False)) {
            TextSetting(Cover, Text, R.string.PREMIUM, 0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals(True) && listData.get(position).getIsAdult().equals(False)) {
            TextSetting(Cover, Text, R.string.FINISH, 0xAA767676);
        } else if (listData.get(position).getIsNobless().equals(True) && listData.get(position).getIsAdult().equals(True)) {
            TextSetting(Cover, Text, R.string.ADULT_NOBLESS, 0xAAF44336);
        } else if (listData.get(position).getIsPremium().equals(True) && listData.get(position).getIsAdult().equals(True)) {
            TextSetting(Cover, Text, R.string.ADULT_PREMIUM, 0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals(True) && listData.get(position).getIsAdult().equals(True)) {
            TextSetting(Cover, Text, R.string.ADULT_FINISH, 0xAA767676);
        } else {
            Cover.setVisibility(View.GONE);
        }

    }

    public void TextSetting(ConstraintLayout Cover, TextView Text, int Title, int Color) {
        Cover.setVisibility(View.VISIBLE);
        Text.setText(Title);
        Text.setTextColor(Color);
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

    public class Main_BookListViewHolder_A extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title, Writer, UnderCoverText, BookCode;
        ConstraintLayout UnderCover;
        CardView Img_Wrap;

        Main_BookListViewHolder_A(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookA);
            Title = itemView.findViewById(R.id.Text_TitleA);
            Writer = itemView.findViewById(R.id.Text_WriterA);
            UnderCover = itemView.findViewById(R.id.BookImgUnderWrap);
            UnderCoverText = itemView.findViewById(R.id.UnderCoverText);
            BookCode = itemView.findViewById(R.id.BookCodeText);
            Img_Wrap = itemView.findViewById(R.id.Img_Wrap);

            Img_Wrap.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Listener.onItemClick(v, pos, "BookDetail");
                }
            });

        }

    }

}
