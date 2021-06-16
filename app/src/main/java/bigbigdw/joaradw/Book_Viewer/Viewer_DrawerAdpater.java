package bigbigdw.joaradw.Book_Viewer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bigbigdw.joaradw.Book_Detail.Detail_BookPageData;
import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.R;


public class Viewer_DrawerAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Detail_BookPageData> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String Value);
    }

    private bigbigdw.joaradw.Book_Detail.Detail_BookListAdapter.OnItemClickListener Listener = null;

    public void setOnItemClickListener(bigbigdw.joaradw.Book_Detail.Detail_BookListAdapter.OnItemClickListener listener) {
        this.Listener = listener;
    }


    public Viewer_DrawerAdpater(ArrayList<Detail_BookPageData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewer_drawer_item, parent, false);
            return new Viewer_DrawerAdpater.Detail_BookList_ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new Viewer_DrawerAdpater.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Viewer_DrawerAdpater.Detail_BookList_ViewHolder) {
            populateItemRows((Viewer_DrawerAdpater.Detail_BookList_ViewHolder) holder, position);
        } else if (holder instanceof Viewer_DrawerAdpater.LoadingViewHolder) {
            showLoadingView((Viewer_DrawerAdpater.LoadingViewHolder) holder, position);
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

    private void showLoadingView(Viewer_DrawerAdpater.LoadingViewHolder holder, int position) {
    }

    private void populateItemRows(Viewer_DrawerAdpater.Detail_BookList_ViewHolder holder, int position) {
        Detail_BookPageData item = listData.get(position);

        holder.Title.setText(listData.get(position).getBookChapter());
        holder.BookList.setText(listData.get(position).getBookListNum());
        holder.BookListRecommend.setText(listData.get(position).getBookListRecommend());
        holder.BookCid.setText(listData.get(position).getCid());
        holder.BookListComment.setText(listData.get(position).getBookListComment());

    }

    public class Detail_BookList_ViewHolder extends RecyclerView.ViewHolder {

        String TOKEN;
        LinearLayout Img_Wrap;
        TextView BookCid, Title, BookList, BookListRecommend, BookListComment;
        JSONObject GETUSERINFO;

        Detail_BookList_ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.ChapterTitle);
            BookListRecommend = itemView.findViewById(R.id.BookListRecommend);
            BookListComment = itemView.findViewById(R.id.BookListComment);
            BookList = itemView.findViewById(R.id.BookListNum);
            Img_Wrap = itemView.findViewById(R.id.Img_Wrap);
            BookCid = itemView.findViewById(R.id.Cid);

            if(Config.getuserinfo() != null) {
                GETUSERINFO = Config.getuserinfo();
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