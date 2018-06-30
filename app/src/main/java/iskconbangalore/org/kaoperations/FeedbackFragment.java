package iskconbangalore.org.kaoperations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class FeedbackFragment extends Fragment {
    private static final String TAG ="Profile";
    private Button btnTest;
    Spinner MealType;
    String feedBackDat;
    CalendarView feedbackDate;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);


        btnTest = (Button) view.findViewById(R.id.nextFeedback);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFeedback();
            }

        });

        MealType = (Spinner)view.findViewById(R.id.mealType);
        feedbackDate = (CalendarView)view.findViewById(R.id.feedbackDate);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

        String strDate = sdfDate.format(feedbackDate.getDate());
        feedBackDat = strDate;

        feedbackDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String mon="";
                month=month+1;
                if(month<10)
                {
                    mon="0"+month;
                }
                else
                    mon=mon+month;
              feedBackDat=year+"-"+mon+"-"+dayOfMonth;
            }
        });
        return view;
    }

    public void goToFeedback()
    {
        Toast.makeText(getActivity(),"Hello",Toast.LENGTH_LONG).show();
        Intent k = new Intent(getActivity(),FeedbackTab.class);


         String mealTy = MealType.getSelectedItem().toString();
         Log.d("info","FeedbackDate:"+feedBackDat);
         Log.d("info","MealType:"+mealTy);

         k.putExtra("SelectedDate",feedBackDat);
         k.putExtra("SelMealType",mealTy);

       // k.putExtra("name",name);
        startActivity(k);
    }
}
