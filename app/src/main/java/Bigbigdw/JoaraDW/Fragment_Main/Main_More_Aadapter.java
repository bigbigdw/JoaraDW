package Bigbigdw.JoaraDW.Fragment_Main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.R;

public class Main_More_Aadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Main_More_ListData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_more_list, parent, false);
        return new Bigbigdw.JoaraDW.Fragment_Main.Main_More_Aadapter.Main_More_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((Main_More_ViewHolder) holder).Contents.setText(listData.get(position).getContents());
        ((Main_More_ViewHolder) holder).Date.setText(listData.get(position).getStartDate());

        ((Main_More_ViewHolder) holder).SortNo.setText(listData.get(position).getBookSortNo());
        if(((Main_More_ViewHolder) holder).SortNo != null){
            ((Main_More_ViewHolder) holder).SortNo.setVisibility(View.VISIBLE);
        }

        ((Main_More_ViewHolder) holder).BookSurveyStatus.setText(listData.get(position).getBookSurveyFinish());
        if(((Main_More_ViewHolder) holder).BookSurveyStatus != null){
            ((Main_More_ViewHolder) holder).BookSurveyStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(ArrayList<Main_More_ListData> items) {
        this.listData = items;
    }

    static class Main_More_ViewHolder extends RecyclerView.ViewHolder {

        TextView Contents, Date, SortNo, BookSurveyStatus;

        Main_More_ViewHolder(@NonNull View itemView) {
            super(itemView);
            Contents = itemView.findViewById(R.id.Contents);
            Date = itemView.findViewById(R.id.Date);
            SortNo = itemView.findViewById(R.id.BookSortNo);
            BookSurveyStatus = itemView.findViewById(R.id.BookSurveyStatus);
        }

    }

}
