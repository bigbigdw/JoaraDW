package Bigbigdw.JoaraDW.BookList;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_Best extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements onClickAdapterListener_Best{
    ArrayList<Main_BookListData> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    onClickAdapterListener_Best listener;

    public Main_BookListAdapter_Best(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_best, parent, false);
            return new Main_BookListAdapter_Best.Main_BookListViewHolder_Best(view, this);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new Main_BookListAdapter_Best.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Main_BookListAdapter_Best.Main_BookListViewHolder_Best) {
            populateItemRows((Main_BookListAdapter_Best.Main_BookListViewHolder_Best) holder, position);
        } else if (holder instanceof Main_BookListAdapter_Best.LoadingViewHolder) {
            showLoadingView((Main_BookListAdapter_Best.LoadingViewHolder) holder, position);
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

    private void showLoadingView(Main_BookListAdapter_Best.LoadingViewHolder holder, int position) {
    }

    private void populateItemRows(Main_BookListAdapter_Best.Main_BookListViewHolder_Best holder, int position) {
        Main_BookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into((holder).Image);

        holder.BestRankImage.setImageResource(listData.get(position).getBookBestRank());

        holder.Title.setText(listData.get(position).getTitle());
        holder.Writer.setText(listData.get(position).getWriter());
        holder.Intro.setText(listData.get(position).getIntro());

        holder.BestCount.setText(listData.get(position).getBestCount());
        holder.BestViewCount.setText(listData.get(position).getBookViewed());
        holder.BestFav.setText(listData.get(position).getBookFavCount());
        holder.BestRecommend.setText(listData.get(position).getBookRecommend());
        holder.BookCode.setText(listData.get(position).getBookCode());
        holder.Category.setText(listData.get(position).getBookCategory());

        if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            holder.TopText.setText(R.string.NOBLESS);
            holder.BookLabel.setBackgroundColor(0xAAa5c500);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            holder.TopText.setText(R.string.PREMIUM);
            holder.BookLabel.setBackgroundColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            holder.TopText.setText(R.string.FINISH);
            holder.BookLabel.setBackgroundColor(0xAAa5c500);
        } else if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            holder.TopText.setText(R.string.ADULT_NOBLESS);
            holder.BookLabel.setBackgroundColor(0xAAF44336);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            holder.TopText.setText(R.string.ADULT_PREMIUM);
            holder.BookLabel.setBackgroundColor(0xAAF44336);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            holder.TopText.setText(R.string.ADULT_FINISH);
            holder.BookLabel.setBackgroundColor(0xAAF44336);
        }

        if (listData.get(position).getIsFav().equals("TRUE")) {
            holder.FavWrap.setVisibility(View.VISIBLE);
        } else {
            holder.FavWrap.setVisibility(View.GONE);
        }
    }

    public void setOnItemClicklistener(onClickAdapterListener_Best listener) {
        this.listener = listener;
    }

    @Override
    public void onClickAdapter_Best(Main_BookListViewHolder_Best holder, View view, int position, String Value) {
        if (listener != null) {
            listener.onClickAdapter_Best(holder, view, position, Value);
        }
    }

    static class Main_BookListViewHolder_Best extends RecyclerView.ViewHolder {

        ImageView Image;
        ImageView BestRankImage;
        TextView Title;
        TextView Writer;
        TextView Intro;
        TextView BestCount;
        TextView BestViewCount;
        TextView BestFav;
        TextView BestRecommend;
        String TOKEN = "";
        JSONObject GETUSERINFO;
        LinearLayout BestWrap, FavWrap, BookLabel;
        String BookTitle, Book_Code;
        TextView BookCode, TopText, Bar,Category;

        Main_BookListViewHolder_Best(@NonNull View itemView, final onClickAdapterListener_Best listener) {
            super(itemView);
            BestRankImage = itemView.findViewById(R.id.BestRankImg);
            Image = itemView.findViewById(R.id.Img_BookBest);
            Title = itemView.findViewById(R.id.Text_Title_Best);
            Writer = itemView.findViewById(R.id.Text_Writer_Best);
            TopText = itemView.findViewById(R.id.TopText);
            Bar = itemView.findViewById(R.id.Bar);
            Category = itemView.findViewById(R.id.Category);
            BestCount = itemView.findViewById(R.id.Text_BestCount);
            BestViewCount = itemView.findViewById(R.id.Text_BestViewed);
            BestFav = itemView.findViewById(R.id.Text_BestFav);
            BestRecommend = itemView.findViewById(R.id.Text_BestRecommend);
            BestWrap = itemView.findViewById(R.id.BestWrap);
            FavWrap = itemView.findViewById(R.id.FavWrap);
            Intro = itemView.findViewById(R.id.Text_Intro_Best);
            BookCode = itemView.findViewById(R.id.BookCodeText);
            BookLabel = itemView.findViewById(R.id.BookLabel);


            GETUSERINFO = Config.GETUSERINFO();
            if(Config.GETUSERINFO() != null){
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

            BestWrap.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onClickAdapter_Best(Main_BookListAdapter_Best.Main_BookListViewHolder_Best.this, v, position, "BookDetail");
                }
            });

            if (!TOKEN.equals("")) {
                Image.setOnClickListener(v -> {
                    if (FavWrap.getVisibility() == View.GONE) {
                        FavWrap.setVisibility(View.VISIBLE);
                        BookTitle = Title.getText().toString();
                        Book_Code = BookCode.getText().toString();
                        Toast.makeText(itemView.getContext(), "'" + BookTitle + "'이(가) 선호작에 등록되었습니다",
                                Toast.LENGTH_SHORT).show();
                        int position = getAdapterPosition();
                        if (listener != null) {
                            listener.onClickAdapter_Best(Main_BookListAdapter_Best.Main_BookListViewHolder_Best.this, v, position, "FAV");
                        }
                    } else {
                        FavWrap.setVisibility(View.GONE);
                        BookTitle = Title.getText().toString();
                        Book_Code = BookCode.getText().toString();
                        Toast.makeText(itemView.getContext(), "'" + BookTitle + "'을(를) 선호작에서 해제하였습니다",
                                Toast.LENGTH_SHORT).show();
                        int position = getAdapterPosition();
                        if (listener != null) {
                            listener.onClickAdapter_Best(Main_BookListAdapter_Best.Main_BookListViewHolder_Best.this, v, position, "FAV");
                        }
                    }
                });
            } else {
                Image.setOnClickListener(v -> {
                    Toast.makeText(itemView.getContext(), "선호작을 등록하려면 로그인이 필요합니다",
                            Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public void setItems(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    public Main_BookListData getItem(int position) {
        return listData.get(position);
    }

}