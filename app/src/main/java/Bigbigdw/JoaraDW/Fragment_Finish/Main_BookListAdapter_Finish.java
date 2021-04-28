package Bigbigdw.JoaraDW.Fragment_Finish;

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


public class Main_BookListAdapter_Finish extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnClickFinishListener {
    ArrayList<Main_BookListData> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    OnClickFinishListener listener;

    public Main_BookListAdapter_Finish(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_finish, parent, false);
            return new Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish(view, this);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new Main_BookListAdapter_Finish.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) {
            populateItemRows((Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish) holder, position);
        } else if (holder instanceof Main_BookListAdapter_Finish.LoadingViewHolder) {
            showLoadingView((Main_BookListAdapter_Finish.LoadingViewHolder) holder, position);
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

    private void showLoadingView(Main_BookListAdapter_Finish.LoadingViewHolder holder, int position) {}

    private void populateItemRows(Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish holder, int position) {
        Main_BookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.Image);

        holder.Title.setText(listData.get(position).getTitle());
        holder.Writer.setText(listData.get(position).getWriter());
        holder.Intro.setText(listData.get(position).getIntro());
        holder.BookCode.setText(listData.get(position).getBookCode());
        holder.BookFav.setText(listData.get(position).getIsFav());

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

    @Override
    public void onItemClick(Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish holder, View view, int position, String Value) {
        if (listener != null) {
            listener.onItemClick(holder, view, position, Value);
        }
    }

    public void setOnItemClicklistener(OnClickFinishListener listener) {
        this.listener = listener;
    }


    static public class Main_BookListViewHolder_Finish extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;
        TextView Intro;
        TextView TopText;
        TextView BookCode;
        TextView BookFav;
        ImageView Favon;
        ImageView Favoff;
        CardView Img_Wrap;
        String BookTitle,Book_Code;
        String TOKEN = "";

        Main_BookListViewHolder_Finish(@NonNull View itemView, final OnClickFinishListener listener) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_Book);
            Title = itemView.findViewById(R.id.Text_Title);
            Intro = itemView.findViewById(R.id.Text_Intro);
            Writer = itemView.findViewById(R.id.Text_Writer);
            TopText = itemView.findViewById(R.id.TopText);
            Favon = itemView.findViewById(R.id.FavON);
            Favoff = itemView.findViewById(R.id.FavOff);
            Img_Wrap = itemView.findViewById(R.id.Img_Wrap);
            BookCode = itemView.findViewById(R.id.BookCodeText);
            BookFav = itemView.findViewById(R.id.TextBookFav);

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
                Log.d("USERINFO", "읽기 완료");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.d("USERINFO", "읽기 실패");
            }

            if(!TOKEN.equals("")){
                Img_Wrap.setOnClickListener(v -> {
                    if (Favoff.getVisibility() == View.VISIBLE) {
                        Favoff.setVisibility(View.GONE);
                        Favon.setVisibility(View.VISIBLE);
                        BookTitle = Title.getText().toString();
                        Book_Code = BookCode.getText().toString();
                        Toast.makeText(itemView.getContext(), "'" + BookTitle + "'이(가) 선호작에 등록되었습니다",
                                Toast.LENGTH_SHORT).show();
                        int position = getAdapterPosition();
                        if (listener != null) {
                            listener.onItemClick(Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish.this, v, position, Book_Code);
                        }
                    } else {
                        Favoff.setVisibility(View.VISIBLE);
                        Favon.setVisibility(View.GONE);
                        BookTitle = Title.getText().toString();
                        Book_Code = BookCode.getText().toString();
                        Toast.makeText(itemView.getContext(), "'" + BookTitle + "'을(를) 선호작에서 해제하였습니다",
                                Toast.LENGTH_SHORT).show();
                        int position = getAdapterPosition();
                        if (listener != null) {
                            listener.onItemClick(Main_BookListAdapter_Finish.Main_BookListViewHolder_Finish.this, v, position, Book_Code);
                        }
                    }
                });
            } else {
                Img_Wrap.setOnClickListener(v -> {
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

    public void setItem(int position, Main_BookListData item) {
        listData.set(position, item);
    }
}