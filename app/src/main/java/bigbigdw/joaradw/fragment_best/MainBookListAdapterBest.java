package bigbigdw.joaradw.fragment_best;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.main.MainBookListData;
import bigbigdw.joaradw.R;


public class MainBookListAdapterBest extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<MainBookListData> listData;
    private static final int VIEW_TYPE_ITEM = 0;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String value);
    }

    private MainBookListAdapterBest.OnItemClickListener listener = null;

    public void setOnItemClickListener(MainBookListAdapterBest.OnItemClickListener listener) {
        this.listener = listener;
    }

    public MainBookListAdapterBest(List<MainBookListData> items) {
        this.listData = (ArrayList<MainBookListData>) items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_best, parent, false);
            return new MainBookListViewHolderBest(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainBookListViewHolderBest) {
            populateItemRows((MainBookListViewHolderBest) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewTypeLoading = 1;
        return listData.get(position) == null ? viewTypeLoading : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }


    private void populateItemRows(MainBookListViewHolderBest holder, int position) {
        MainBookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into((holder).image);

        holder.bestRankImage.setImageResource(listData.get(position).getBookBestRank());

        holder.title.setText(listData.get(position).getTitle());
        holder.writer.setText(listData.get(position).getWriter());
        holder.intro.setText(listData.get(position).getIntro());

        holder.bestViewCount.setText(listData.get(position).getBookViewed());
        holder.bestFav.setText(listData.get(position).getBookFavCount());
        holder.bestRecommend.setText(listData.get(position).getBookRecommend());
        holder.bookCode.setText(listData.get(position).getBookCode());
        holder.category.setText(listData.get(position).getBookCategory());
        holder.textCntChapter.setText(listData.get(position).getCntChapter());

        TextView topText = holder.topText;
        LinearLayout bookLabel = holder.bookLabel;
        String f = "FALSE";
        String t = "TRUE";

        if (listData.get(position).getIsNobless().equals(t) && listData.get(position).getIsAdult().equals(f)) {
            textSetting(topText, bookLabel, R.string.NOBLESS, 0xAAa5c500);
        } else if (listData.get(position).getIsPremium().equals(t) && listData.get(position).getIsAdult().equals(f)) {
            textSetting(topText, bookLabel, R.string.PREMIUM, 0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals(t) && listData.get(position).getIsAdult().equals(f)) {
            textSetting(topText, bookLabel, R.string.FINISH, 0xAAa5c500);
        } else if (listData.get(position).getIsNobless().equals(t) && listData.get(position).getIsAdult().equals(t)) {
            textSetting(topText, bookLabel, R.string.ADULT_NOBLESS, 0xAAF44336);
        } else if (listData.get(position).getIsPremium().equals(t) && listData.get(position).getIsAdult().equals(t)) {
            textSetting(topText, bookLabel, R.string.ADULT_PREMIUM, 0xAAF44336);
        } else if (listData.get(position).getIsFinish().equals(t) && listData.get(position).getIsAdult().equals(t)) {
            textSetting(topText, bookLabel, R.string.ADULT_FINISH, 0xAAF44336);
        }

        if (listData.get(position).getIsFav().equals("TRUE")) {
            holder.favwrap.setVisibility(View.VISIBLE);
        } else {
            holder.favwrap.setVisibility(View.GONE);
        }
    }

    public void textSetting(TextView topText, LinearLayout bookLabel, int title, int color) {
        topText.setText(title);
        bookLabel.setBackgroundColor(color);
    }

    public class MainBookListViewHolderBest extends RecyclerView.ViewHolder {

        ImageView image;
        ImageView bestRankImage;
        TextView title;
        TextView writer;
        TextView intro;
        TextView bestViewCount;
        TextView bestFav;
        TextView bestRecommend;
        TextView bookCode;
        TextView topText;
        TextView bar;
        TextView category;
        TextView textCntChapter;
        String token = "";
        String bookTitle;
        String stringBookCode;
        JSONObject getuserinfo;
        LinearLayout bestWrap;
        LinearLayout favwrap;
        LinearLayout bookLabel;

        MainBookListViewHolderBest(@NonNull View itemView) {
            super(itemView);
            bestRankImage = itemView.findViewById(R.id.BestRankImg);
            image = itemView.findViewById(R.id.Img_BookBest);
            title = itemView.findViewById(R.id.Text_Title_Best);
            writer = itemView.findViewById(R.id.Text_Writer_Best);
            topText = itemView.findViewById(R.id.TopText);
            bar = itemView.findViewById(R.id.Bar);
            category = itemView.findViewById(R.id.Category);
            bestViewCount = itemView.findViewById(R.id.Text_BestViewed);
            bestFav = itemView.findViewById(R.id.Text_BestFav);
            bestRecommend = itemView.findViewById(R.id.Text_BestRecommend);
            bestWrap = itemView.findViewById(R.id.BestWrap);
            favwrap = itemView.findViewById(R.id.FavWrap);
            intro = itemView.findViewById(R.id.Text_Intro_Best);
            bookCode = itemView.findViewById(R.id.BookCodeText);
            bookLabel = itemView.findViewById(R.id.BookLabel);
            textCntChapter = itemView.findViewById(R.id.Text_CntChapter);

            getuserinfo = Config.getuserinfo();
            if(Config.getuserinfo() != null){
                getuserinfo = Config.getuserinfo();
                JSONObject userInfo;
                try {
                    userInfo = getuserinfo.getJSONObject("user");
                    token = userInfo.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                    token = "";
                }
            }

            bestWrap.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(v, pos, "BookDetail");
                }
            });

            if (!token.equals("")) {
                image.setOnClickListener(v -> {
                    if (favwrap.getVisibility() == View.GONE) {
                        favwrap.setVisibility(View.VISIBLE);
                        bookTitle = title.getText().toString();
                        stringBookCode = bookCode.getText().toString();
                        Toast.makeText(itemView.getContext(), "'" + bookTitle + "'이(가) 선호작에 등록되었습니다",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        favwrap.setVisibility(View.GONE);
                        bookTitle = title.getText().toString();
                        stringBookCode = bookCode.getText().toString();
                        Toast.makeText(itemView.getContext(), "'" + bookTitle + "'을(를) 선호작에서 해제하였습니다",
                                Toast.LENGTH_SHORT).show();
                    }
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, pos, "FAV");
                    }
                });
            } else {
                image.setOnClickListener(v -> Toast.makeText(itemView.getContext(), "선호작을 등록하려면 로그인이 필요합니다", Toast.LENGTH_SHORT).show());
            }
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.progressBar);
        }
    }

    public void setItems(List<MainBookListData> items) {
        this.listData = (ArrayList<MainBookListData>) items;
    }

    public MainBookListData getItem(int position) {
        return listData.get(position);
    }

}