package iskconbangalore.org.kaoperations;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<notificationMessages> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textTime;
        TextView textTitle;
        TextView textBody;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textTime = (TextView) itemView.findViewById(R.id.notificationTime);
            this.textTitle = (TextView) itemView.findViewById(R.id.notificationTitle);
            this.textBody = (TextView) itemView.findViewById(R.id.notificationBody);
        }
    }

    public CustomAdapter(ArrayList<notificationMessages> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

        // view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewtime= holder.textTime;
        TextView textViewTitle = holder.textTitle;
        TextView textViewBody = holder.textBody;

        textViewtime.setText(dataSet.get(listPosition).getTmstmp());
        textViewTitle.setText(dataSet.get(listPosition).getTitle());
        textViewBody.setText(dataSet.get(listPosition).getBody());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
