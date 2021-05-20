package Bigbigdw.JoaraDW.Fragment_Best;

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


public class Main_BookListAdapter_Best extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<Main_BookListData> listData;
    private final int VIEW_TYPE_ITEM = 0;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String Value);
    }

    private Main_BookListAdapter_Best.OnItemClickListener Listener = null;

    public void setOnItemClickListener(Main_BookListAdapter_Best.OnItemClickListener listener) {
        this.Listener = listener;
    }

    public Main_BookListAdapter_Best(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_best, parent, false);
            return new Main_BookListAdapter_Best.Main_BookListViewHolder_Best(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new LoadingViewHolder(view);
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
        int VIEW_TYPE_LOADING = 1;
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

        holder.BestViewCount.setText(listData.get(position).getBookViewed());
        holder.BestFav.setText(listData.get(position).getBookFavCount());
        holder.BestRecommend.setText(listData.get(position).getBookRecommend());
        holder.BookCode.setText(listData.get(position).getBookCode());
        holder.Category.setText(listData.get(position).getBookCategory());

        TextView TopText = holder.TopText;
        LinearLayout BookLabel = holder.BookLabel;

        if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            TextSetting(TopText, BookLabel, R.string.NOBLESS, 0xAAa5c500);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            TextSetting(TopText, BookLabel, R.string.PREMIUM, 0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            TextSetting(TopText, BookLabel, R.string.FINISH, 0xAAa5c500);
        } else if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            TextSetting(TopText, BookLabel, R.string.ADULT_NOBLESS, 0xAAF44336);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            TextSetting(TopText, BookLabel, R.string.ADULT_PREMIUM, 0xAAF44336);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            TextSetting(TopText, BookLabel, R.string.ADULT_FINISH, 0xAAF44336);
        }

        if (listData.get(position).getIsFav().equals("TRUE")) {
            holder.FavWrap.setVisibility(View.VISIBLE);
        } else {
            holder.FavWrap.setVisibility(View.GONE);
        }
    }

    public void TextSetting(TextView TopText, LinearLayout BookLabel, int Title, int Color) {
        TopText.setText(Title);
        BookLabel.setBackgroundColor(Color);
    }

    public class Main_BookListViewHolder_Best extends RecyclerView.ViewHolder {

        ImageView Image, BestRankImage;
        TextView Title, Writer, Intro, BestViewCount, BestFav, BestRecommend, BookCode, TopText, Bar,Category;
        String TOKEN = "", BookTitle, Book_Code;
        JSONObject GETUSERINFO;
        LinearLayout BestWrap, FavWrap, BookLabel;

        Main_BookListViewHolder_Best(@NonNull View itemView) {
            super(itemView);
            BestRankImage = itemView.findViewById(R.id.BestRankImg);
            Image = itemView.findViewById(R.id.Img_BookBest);
            Title = itemView.findViewById(R.id.Text_Title_Best);
            Writer = itemView.findViewById(R.id.Text_Writer_Best);
            TopText = itemView.findViewById(R.id.TopText);
            Bar = itemView.findViewById(R.id.Bar);
            Category = itemView.findViewById(R.id.Category);
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
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Listener.onItemClick(v, pos, "BookDetail");
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
                    } else {
                        FavWrap.setVisibility(View.GONE);
                        BookTitle = Title.getText().toString();
                        Book_Code = BookCode.getText().toString();
                        Toast.makeText(itemView.getContext(), "'" + BookTitle + "'을(를) 선호작에서 해제하였습니다",
                                Toast.LENGTH_SHORT).show();
                    }
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Listener.onItemClick(v, pos, "FAV");
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

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            ProgressBar progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public void setItems(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    public Main_BookListData getItem(int position) {
        return listData.get(position);
    }

}