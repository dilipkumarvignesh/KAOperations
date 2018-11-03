package iskconbangalore.org.kaoperations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class OperationFragment extends Fragment {
    private static final String TAG ="Profile";
    private String SelectedDate,MealType;
    private Button btnTest;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    ListView lView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_operations_feedback,container,false);
        lView  = (ListView)view.findViewById(R.id.operations_list);
        String[] operationItems = {"Delivery Time","Hygiene","Behaviour"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.menu_list,operationItems);
        lView.setAdapter(adapter);
        SelectedDate = getArguments().getString("SelectedDate");
        MealType = getArguments().getString("MealType");
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String selItem = (String) lView.getItemAtPosition(position);
                Log.d("info","SelectedItem:"+selItem);
                Bundle args = new Bundle();
                args.putString("Item", selItem);
                args.putString("SelectedDate",SelectedDate);
                args.putString("Meal",MealType);
                if(position == 0)
                {

                    DialogOperationsDeliveryUpdate Delivery_Update = new DialogOperationsDeliveryUpdate();
                Delivery_Update.setArguments(args);
                Delivery_Update.show(getActivity().getFragmentManager(), "DeliveryUpdate");
                }
                else if(position == 1)
                {
                    DialogOperationsHygieneUpdate Hygiene_Update = new DialogOperationsHygieneUpdate();
                    Hygiene_Update.setArguments(args);
                    Hygiene_Update.show(getActivity().getFragmentManager(), "DeliveryUpdate");
                }
                else
                {
                    DialogOperationsBehaviourUpdate Behaviour_Update = new DialogOperationsBehaviourUpdate();
                    Behaviour_Update.setArguments(args);
                    Behaviour_Update.show(getActivity().getFragmentManager(), "DeliveryUpdate");
                }

//                DialogPrasadamUpdate prasadam_Update = new DialogPrasadamUpdate();
//                Bundle args = new Bundle();
//                args.putString("Item", selItem);
//                prasadam_Update.setArguments(args);
//                prasadam_Update.show(getActivity().getFragmentManager(), "PrasadamUpdate");
            }
        });
        return view;
    }

//    public void goToFeedback(View v)
//    {
//        Toast.makeText(getActivity(),"Hello",Toast.LENGTH_LONG).show();
//    }


}
