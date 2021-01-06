package Bigbigdw.JoaraDW.Main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import Bigbigdw.JoaraDW.R;

public class MainBanner_ListAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MainBanner_ListData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainbanners, parent, false);
        return new MainBanner_ListViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MainBanner_ListViewholder)holder).onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(MainBanner_ListData data) {
        listData.add(data);
    }

    static class MainBanner_ListViewholder extends  RecyclerView.ViewHolder {

        ImageView Image;

        public MainBanner_ListViewholder(@NonNull View itemView) {
            super(itemView);

            Image = itemView.findViewById(R.id.Img);
        }

        public void onBind(MainBanner_ListData data){
            Image.setImageResource(data.getImg());
        }
    }
}


