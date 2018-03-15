package iskconbangalore.org.kaoperations;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import iskconbangalore.org.kaoperations.ItemFragment.OnListFragmentInteractionListener;
import iskconbangalore.org.kaoperations.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    public ArrayList<ResUpdate> mValues;
  //  private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(ArrayList<ResUpdate> items) {
        mValues = items;
      //  mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       // holder.mItem = mValues.get(position);
       // holder.mIdView.setText(mValues.get(position).id);
        holder.Residency.setText(mValues.get(position).residency);
        holder.morDel.setText(mValues.get(position).morningDel);
        holder.morFeed.setText(mValues.get(position).morningFeedback);
        holder.eveDel.setText(mValues.get(position).eveningDel);
        holder.EveFeed.setText(mValues.get(position).eveningFeedback);
//        holder.EveFeed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView morDel;
        TextView morFeed;
        TextView eveDel;
        TextView EveFeed,Residency;



        public ViewHolder(View view) {
            super(view);
            cv = (CardView)view.findViewById(R.id.card_view);
          //  mView = view;
            morDel = (TextView) view.findViewById(R.id.morDel);
            morFeed = (TextView) view.findViewById(R.id.morFeed);
            eveDel = (TextView) view.findViewById(R.id.eveDel);
            EveFeed = (TextView) view.findViewById(R.id.eveFeed);
            Residency = (TextView) view.findViewById(R.id.Residency);

                   }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
    public void swapDataSet(ArrayList<ResUpdate> newData){

        this.mValues = newData;

        //now, tell the adapter about the update
        notifyDataSetChanged();

    }
}
