package Bigbigdw.JoaraDW.Book_Detail;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Etc.BookList;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_A;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Detail_BookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Detail_BookPageData> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String Value);
    }

    private Detail_BookListAdapter.OnItemClickListener Listener = null;

    public void setOnItemClickListener(Detail_BookListAdapter.OnItemClickListener listener) {
        this.Listener = listener;
    }


    public Detail_BookListAdapter(ArrayList<Detail_BookPageData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_booklist, parent, false);
            return new Detail_BookListAdapter.Detail_BookList_ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Detail_BookListAdapter.Detail_BookList_ViewHolder) {
            populateItemRows((Detail_BookListAdapter.Detail_BookList_ViewHolder) holder, position);
        } else if (holder instanceof Detail_BookListAdapter.LoadingViewHolder) {
            showLoadingView((Detail_BookListAdapter.LoadingViewHolder) holder, position);
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

    private void showLoadingView(Detail_BookListAdapter.LoadingViewHolder holder, int position) {
    }

    private void populateItemRows(Detail_BookListAdapter.Detail_BookList_ViewHolder holder, int position) {
        Detail_BookPageData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.Image);

        holder.Title.setText(listData.get(position).getBookChapter());
        holder.BookList.setText(listData.get(position).getBookListNum());
        holder.BookListRecommend.setText(listData.get(position).getBookListRecommend());
        holder.BookCid.setText(listData.get(position).getCid());
        holder.BookListComment.setText(listData.get(position).getBookListComment());

    }


    public class Detail_BookList_ViewHolder extends RecyclerView.ViewHolder {

        ImageView Image;
        String TOKEN;
        LinearLayout Img_Wrap;
        TextView BookCid, Title, BookList, BookListRecommend, BookListComment;
        JSONObject GETUSERINFO;

        Detail_BookList_ViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.BookListImg);
            Title = itemView.findViewById(R.id.ChapterTitle);
            BookListRecommend = itemView.findViewById(R.id.BookListRecommend);
            BookListComment = itemView.findViewById(R.id.BookListComment);
            BookList = itemView.findViewById(R.id.BookListNum);
            Img_Wrap = itemView.findViewById(R.id.Img_Wrap);
            BookCid = itemView.findViewById(R.id.Cid);

            if(Config.GETUSERINFO() != null) {
                GETUSERINFO = Config.GETUSERINFO();
                JSONObject UserInfo;
                try {
                    UserInfo = GETUSERINFO.getJSONObject("user");
                    TOKEN = UserInfo.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                    TOKEN = "";
                }
            }

            Img_Wrap.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Listener.onItemClick(v, pos, "BookDetail");
                }
            });

        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            ProgressBar progressBar = itemView.findViewById(R.id.progressBar);
        }
    }


    public void setItems(ArrayList<Detail_BookPageData> items) {
        this.listData = items;
    }

    public Detail_BookPageData getItem(int position) {
        return listData.get(position);
    }
}