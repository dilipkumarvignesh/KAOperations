package iskconbangalore.org.kaoperations;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuFeedbackFragment extends Fragment implements DialogPrasadamUpdate.NoticeDialogListener {
    private static final String TAG ="Profile";
    private Button btnTest;
    ListView lView;
    private String SelectedDate,MealType;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_menu_feedback,container,false);


         SelectedDate = getArguments().getString("SelectedDate");
         MealType = getArguments().getString("MealType");
         Log.d("info","FeedbackFragData:"+SelectedDate+MealType);
        displayMenu(SelectedDate,MealType);
        lView  = (ListView)view.findViewById(R.id.mobile_list);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String selItem = (String) lView.getItemAtPosition(position);
                Log.d("info","SelectedItem:"+selItem);


                DialogPrasadamUpdate prasadam_Update = new DialogPrasadamUpdate();
                Bundle args = new Bundle();
                args.putString("Item", selItem);
                args.putString("SelectedDate",SelectedDate);
                args.putString("Meal",MealType);
                prasadam_Update.setArguments(args);
                prasadam_Update.show(getActivity().getFragmentManager(), "PrasadamUpdate");
            }
        });
        return view;
    }


//    public void enableCheckBox(View v)
//    {
//        Toast.makeText(getActivity(),"Button Clicked",Toast.LENGTH_LONG).show();
//    }
//    public void goToFeedback(View v)
//    {
//        Toast.makeText(getActivity(),"Hello",Toast.LENGTH_LONG).show();
//    }

    public void displayMenu(String SelectedDate,String MealType)
    {
        final String Meal = MealType;
        Log.d("info","DBRef:"+FirebaseDatabase.getInstance().getReference("Menu"));
        SharedPreferences userInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        Log.d("info","MenuSelectedDate"+SelectedDate);
        String MenuType =  userInfo.getString("MenuType","NA").toString();
        DatabaseReference todayDateNode = FirebaseDatabase.getInstance().getReference("Menu").child(SelectedDate).child(MenuType);
        final Context delActivity = getActivity();
        final ProgressDialog progress = new ProgressDialog(delActivity);
        progress.setTitle("Loading");
        progress.setMessage("Please Wait...");
        progress.setCancelable(true); // disable dismiss by tapping outside of the dialog
        progress.show();

        todayDateNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("info","dataFet:"+dataSnapshot.getValue());
                if(dataSnapshot.hasChild(Meal))
                {
                    String breakfast =  dataSnapshot.child(Meal).getValue().toString();
                    String[] Items = breakfast.split(",");

                    for (int i = 0; i < Items.length; i++)
                        Items[i] = Items[i].trim();
                //    Log.d("info","Trimmed array"+trimmedArray);
                    ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
                            R.layout.menu_list, Items);
                    lView.setAdapter(adapter);

                    Log.d("info","Datasnapshot:"+dataSnapshot.child("Breakfast").getValue().toString());


                    progress.dismiss();
                    // float AverageR = AverageRating/childCount;

                }
                else
                {
                    Toast.makeText(getActivity(),"Menu Not Available",Toast.LENGTH_LONG).show();
                    progress.dismiss();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(getActivity(),"Dialog Opened:"+dialog.getTag(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(getActivity(),"Dialog Opened:"+dialog.getTag()+"No",Toast.LENGTH_LONG).show();
    }


}
