package iskconbangalore.org.kaoperations;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;

public class FeedbackTab extends AppCompatActivity implements DialogPrasadamUpdate.NoticeDialogListener {

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private static String MealType, feedbackDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_tab);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Intent intent = getIntent();

        MealType = intent.getStringExtra("SelMealType");
        feedbackDate = intent.getStringExtra("SelectedDate");
        Log.d("info", "FeedbackTabIntent:" + intent.getStringExtra("SelMealType") + intent.getStringExtra("SelMealType"));
        //getValues();


    }

    public static HashMap<String, String> getValues() {
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("MealType", MealType);
        hmap.put("FeedbackDate", feedbackDate);
        return hmap;

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new OperationFragment(), "Operations");
        adapter.addFragment(new MenuFeedbackFragment(), "Menu");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d("info", "Called from Prasadam Tab");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

}


