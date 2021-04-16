package Bigbigdw.JoaraDW.Fragment_New;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_New extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Main_BookListData> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public Main_BookListAdapter_New(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_new, parent, false);
            return new Main_BookListAdapter_New.Main_BookListViewHolder_New(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new Main_BookListAdapter_New.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Main_BookListAdapter_New.Main_BookListViewHolder_New) {
            populateItemRows((Main_BookListAdapter_New.Main_BookListViewHolder_New) holder, position);
        } else if (holder instanceof Main_BookListAdapter_New.LoadingViewHolder) {
            showLoadingView((Main_BookListAdapter_New.LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }



    private void showLoadingView(Main_BookListAdapter_New.LoadingViewHolder holder, int position) {

    }

    private void populateItemRows(Main_BookListAdapter_New.Main_BookListViewHolder_New holder, int position) {
        Main_BookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.Image);

        holder.Title.setText(listData.get(position).getTitle());
        holder.Writer.setText(listData.get(position).getWriter());
        holder.Intro.setText(listData.get(position).getIntro());

        if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            holder.TopText.setText(R.string.NOBLESS);
            holder.TopText.setTextColor(0xAAa5c500);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            holder.TopText.setText(R.string.PREMIUM);
            holder.TopText.setTextColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            holder.TopText.setText(R.string.FINISH);
            holder.TopText.setTextColor(0xAA767676);
        } else if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            holder.TopText.setText(R.string.ADULT_NOBLESS);
            holder.TopText.setTextColor(0xAAF44336);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            holder.TopText.setText(R.string.ADULT_PREMIUM);
            holder.TopText.setTextColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            holder.TopText.setText(R.string.ADULT_FINISH);
            holder.TopText.setTextColor(0xAA767676);
        }

        if (listData.get(position).getIsFav().equals("TRUE")) {
            holder.Favon.setVisibility(View.VISIBLE);
            holder.Favoff.setVisibility(View.GONE);
        } else {
            holder.Favoff.setVisibility(View.VISIBLE);
            holder.Favon.setVisibility(View.GONE);
        }
    }


    static public class Main_BookListViewHolder_New extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;
        TextView Intro;
        TextView TopText;
        ImageView Favon;
        ImageView Favoff;
        LinearLayout Img_Wrap;


        Main_BookListViewHolder_New(@NonNull View itemView) {
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
