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

import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Detail_BookLIstAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements onClickBookDetailListener {
    ArrayList<Detail_BookPageData> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    onClickBookDetailListener listener;

    public Detail_BookLIstAdapter(ArrayList<Detail_BookPageData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_booklist, parent, false);
            return new Detail_BookLIstAdapter.Detail_BookLIst_ViewHolder(view, this);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new Detail_BookLIstAdapter.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Detail_BookLIstAdapter.Detail_BookLIst_ViewHolder) {
            populateItemRows((Detail_BookLIstAdapter.Detail_BookLIst_ViewHolder) holder, position);
        } else if (holder instanceof Detail_BookLIstAdapter.LoadingViewHolder) {
            showLoadingView((Detail_BookLIstAdapter.LoadingViewHolder) holder, position);
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

    private void showLoadingView(Detail_BookLIstAdapter.LoadingViewHolder holder, int position) {
    }

    private void populateItemRows(Detail_BookLIstAdapter.Detail_BookLIst_ViewHolder holder, int position) {
        Detail_BookPageData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.Image);

        holder.Title.setText(listData.get(position).getBookChapter());
        holder.BookList.setText(listData.get(position).getBookListNum());
        holder.BookListRecommend.setText(listData.get(position).getBookListRecommend());

    }

    @Override
    public void onClickBookList(Detail_BookLIstAdapter.Detail_BookLIst_ViewHolder holder, View view, int position, String Value) {
        if (listener != null) {
            listener.onClickBookList(holder, view, position, Value);
        }
    }

    public void setOnItemClicklistener(onClickBookDetailListener listener) {
        this.listener = listener;
    }

    static public class Detail_BookLIst_ViewHolder extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView BookList;
        TextView BookListRecommend;
        String TOKEN;
        LinearLayout Img_Wrap;

        Detail_BookLIst_ViewHolder(@NonNull View itemView, final onClickBookDetailListener listener) {
            super(itemView);
            Image = itemView.findViewById(R.id.BookListImg);
            Title = itemView.findViewById(R.id.ChapterTitle);
            BookListRecommend = itemView.findViewById(R.id.BookListRecommend);
            BookList = itemView.findViewById(R.id.BookListNum);
            Img_Wrap = itemView.findViewById(R.id.Img_Wrap);

            try {
                FileReader fr = new FileReader("/data/user/0/Bigbigdw.JoaraDW" + "/userInfo.json");
                BufferedReader br = new BufferedReader(fr);
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line).append("\n");
                    line = br.readLine();
                }
                br.close();
                String result = sb.toString();
                JSONObject jsonObject = new JSONObject(result);
                JSONObject UserInfo = jsonObject.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                TOKEN = "";
            }

            Img_Wrap.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onClickBookList(Detail_BookLIstAdapter.Detail_BookLIst_ViewHolder.this, v, position, "BookDetail");
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


    public void setItems(ArrayList<Detail_BookPageData> items) {
        this.listData = items;
    }

    public Detail_BookPageData getItem(int position) {
        return listData.get(position);
    }
}