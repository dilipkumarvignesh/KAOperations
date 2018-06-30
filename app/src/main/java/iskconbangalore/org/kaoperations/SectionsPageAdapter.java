package iskconbangalore.org.kaoperations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 2/28/2017.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        HashMap<String, String> intentData = FeedbackTab.getValues();
        Bundle bundle = new Bundle();
        Log.d("info","SectionAdapterValues:"+intentData.get("FeedbackDate")+intentData.get("MealType"));
        bundle.putString("SelectedDate",intentData.get("FeedbackDate"));
        bundle.putString("MealType",intentData.get("MealType"));

// set Fragmentclass Arguments
       Fragment fr = mFragmentList.get(position);
       fr.setArguments(bundle);

        return fr;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }



}