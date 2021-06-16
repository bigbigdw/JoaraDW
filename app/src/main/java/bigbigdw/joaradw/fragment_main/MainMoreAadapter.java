package bigbigdw.joaradw.fragment_main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bigbigdw.joaradw.R;

public class MainMoreAadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MainMoreListData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_more_list, parent, false);
        return new MainMoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((MainMoreViewHolder) holder).contents.setText(listData.get(position).getContents());
        ((MainMoreViewHolder) holder).date.setText(listData.get(position).getStartDate());

        ((MainMoreViewHolder) holder).sortNo.setText(listData.get(position).getBookSortNo());
        if(((MainMoreViewHolder) holder).sortNo != null){
            ((MainMoreViewHolder) holder).sortNo.setVisibility(View.VISIBLE);
        }

        ((MainMoreViewHolder) holder).bookSurveyStatus.setText(listData.get(position).getBookSurveyFinish());
        if(((MainMoreViewHolder) holder).bookSurveyStatus != null){
            ((MainMoreViewHolder) holder).bookSurveyStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(List<MainMoreListData> items) {
        this.listData = (ArrayList<MainMoreListData>) items;
    }

    static class MainMoreViewHolder extends RecyclerView.ViewHolder {

        TextView contents;
        TextView date;
        TextView sortNo;
        TextView bookSurveyStatus;

        MainMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.Contents);
            date = itemView.findViewById(R.id.Date);
            sortNo = itemView.findViewById(R.id.BookSortNo);
            bookSurveyStatus = itemView.findViewById(R.id.BookSurveyStatus);
        }

    }

}
