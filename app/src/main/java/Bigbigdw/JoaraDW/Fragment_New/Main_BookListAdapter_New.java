package Bigbigdw.JoaraDW.Fragment_New;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Main.Main_BookListData_A;
import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_New extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Main_BookListData_A> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklist_new, parent, false);
        return new Main_BookListAdapter_New.Main_BookListViewHolder_New(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Main_BookListData_A item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(((Main_BookListAdapter_New.Main_BookListViewHolder_New) holder).Image);

        ((Main_BookListViewHolder_New) holder).Title.setText(listData.get(position).getTitle());
        ((Main_BookListViewHolder_New) holder).Writer.setText(listData.get(position).getWriter());
        ((Main_BookListViewHolder_New) holder).Intro.setText(listData.get(position).getIntro());

        if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            ((Main_BookListViewHolder_New) holder).TopText.setText(R.string.NOBLESS);
            ((Main_BookListViewHolder_New) holder).TopText.setTextColor(0xAAa5c500);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            ((Main_BookListViewHolder_New) holder).TopText.setText(R.string.PREMIUM);
            ((Main_BookListViewHolder_New) holder).TopText.setTextColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            ((Main_BookListViewHolder_New) holder).TopText.setText(R.string.FINISH);
            ((Main_BookListViewHolder_New) holder).TopText.setTextColor(0xAA767676);
        } else if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            ((Main_BookListViewHolder_New) holder).TopText.setText(R.string.ADULT_NOBLESS);
            ((Main_BookListViewHolder_New) holder).TopText.setTextColor(0xAAF44336);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            ((Main_BookListViewHolder_New) holder).TopText.setText(R.string.ADULT_PREMIUM);
            ((Main_BookListViewHolder_New) holder).TopText.setTextColor(0xAA4971EF);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            ((Main_BookListViewHolder_New) holder).TopText.setText(R.string.ADULT_FINISH);
            ((Main_BookListViewHolder_New) holder).TopText.setTextColor(0xAA767676);
        }
//        System.out.println(listData.get(position).getIsFav());

        if(listData.get(position).getIsFav().equals("TRUE")){
            ((Main_BookListViewHolder_New) holder).Favon.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_New) holder).Favoff.setVisibility(View.GONE);
        } else {
            ((Main_BookListViewHolder_New) holder).Favoff.setVisibility(View.VISIBLE);
            ((Main_BookListViewHolder_New) holder).Favon.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(ArrayList<Main_BookListData_A> items) {
        this.listData = items;
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
                if(Favoff.getVisibility() == View.VISIBLE){
                    Favoff.setVisibility(View.GONE);
                    Favon.setVisibility(View.VISIBLE);
                    Toast.makeText(itemView.getContext(),"작품이 선호작에 등록되었습니다",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Favoff.setVisibility(View.VISIBLE);
                    Favon.setVisibility(View.GONE);
                    Toast.makeText(itemView.getContext(),"작품을 선호작에서 해제하였습니다",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
