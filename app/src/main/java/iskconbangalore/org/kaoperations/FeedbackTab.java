package iskconbangalore.org.kaoperations;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeedbackTab extends AppCompatActivity implements DialogPrasadamUpdate.NoticeDialogListener,
        DialogPrasadamUpdate.OnHeadlineSelectedListener,DialogOperationsBehaviourUpdate.OnHeadlineSelectedListener,
        DialogOperationsDeliveryUpdate.OnHeadlineSelectedListener,DialogOperationsHygieneUpdate.OnHeadlineSelectedListener
{

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    Users UserDetails;
    private Button feedbackSubmit;
    private static String MealType, feedbackDate;
    private ArrayList Feedbacklist;

    Map<String,String> email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_tab);
        Feedbacklist = new ArrayList();
        email = new HashMap<String,String>();
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        getEmailDetails();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        feedbackSubmit = findViewById(R.id.SubmitFeedback);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Intent intent = getIntent();

        MealType = intent.getStringExtra("SelMealType");
        feedbackDate = intent.getStringExtra("SelectedDate");
        Log.d("info", "FeedbackTabIntent:" + intent.getStringExtra("SelMealType") + intent.getStringExtra("SelMealType"));
        //getValues();

        UtilityFunctions.getUserPoints(this, new firebaseCallBack() {

            @Override
            public void onCallback(Users user) {
                Log.d("info", "InterfacePointsValue:" + user.getPoints());
                UserDetails = user;
                //TPoints.setText("Feedback Points:" + UserPoints);
            }


        });


    }
    private void getEmailDetails()
    {
        DatabaseReference todayDateNode = FirebaseDatabase.getInstance().getReference("email");

        todayDateNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("info","Emails:"+snapshot.getKey()+"/"+snapshot.getValue().toString());
                    email.put(snapshot.getKey(),snapshot.getValue().toString());
                }

//
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        Log.d("info","Emails Detail:"+email);
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
    public void submitFeedback(View view) {

        Log.d("info","Calling SUbmit feedback");
        sendEmail();
    }


    @Override
    public void onFeedbackAdded(Map<String,String> feedback) {
        Toast.makeText(this,"Feedback Added",Toast.LENGTH_LONG).show();
        Feedbacklist.add(feedback);

        feedbackSubmit.setText("Submit Feedback ("+Feedbacklist.size()+")");


    }

    protected void sendEmail() {
        Log.i("Send email", "");
//        ArrayList<String> TO = new ArrayList<String>();
        Log.d("info","Email Residency:"+email);
        String CC1 = email.get(UserDetails.getResidency());
        String[] CC = {CC1, email.get("CC")};
       // String[] TO = {"krmt.office@hkm-group.org"};
//        TO.add(email.get("default"));
//        Log.d("info","DefaultEmail:"+TO.get(0));

       // CC.add(email.get(UserDetails.getResidency()));
   //     Log.d("info","VariableEmail:"+CC.get(0));
        // String[] CC = {"admin.folk@hkm-group.org"};
         String[] TO = {email.get("TO")};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KrishnaAmrita Prasadam Feedback");
        String output = "Hare Krishna Prabhu ,";
        for ( int i=0;i<Feedbacklist.size();i++ )
        {
            Map<String, String> map = (Map)Feedbacklist.get(i);
            output+="\n\nFeedback on "+map.get("Item")+" for "+map.get("Meal")+" of Date : "+map.get("SelectedDate")+"\n";

               // Log.d("info","MapENtries:"+entry.getKey() + "/" + entry.getValue());
                if(map.containsKey("Quality"))
                {
                    output+="Quality : "+map.get("Quality")+"\n";
                }
                if(map.containsKey("Quantity"))
                {
                    output+="Quality : "+map.get("Quantity")+"\n";
                }
                if(map.containsKey("Quality"))
                {
                    output+="Taste: "+map.get("Taste")+"\n";
                }
                if(map.containsKey("Comments"))
                {
                    output+="Comments : "+map.get("Comments")+"\n";
                }
            if(map.containsKey("Behaviour"))
            {
                output+="Behaviour : "+map.get("Behaviour")+"\n";
            }
            if(map.containsKey("Hygiene"))
            {
                output+="Hygiene : "+map.get("Hygiene")+"\n";
            }
            if(map.containsKey("Delivery"))
            {
                output+="Delivery : "+map.get("Delivery")+"\n";
            }


        }
        output+="\n\nWith Regards,"+"\n"+UserDetails.getName();
        emailIntent.putExtra(Intent.EXTRA_TEXT, output);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //
            Log.i("info","Finished sending email...");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void onFeedbackBehaviourAdded(Map<String, String> feedback) {
//        Feedbacklist.add(feedback);
//
//        feedbackSubmit.setText("Submit Feedback ("+Feedbacklist.size()+")");
//    }
//
//    @Override
//    public void onFeedbackDeliveryAdded(Map<String, String> feedback) {
//        Feedbacklist.add(feedback);
//
//        feedbackSubmit.setText("Submit Feedback ("+Feedbacklist.size()+")");
//    }

//    @Override
//    public void onFeedbackHygieneAdded(Map<String, String> feedback) {
//        Feedbacklist.add(feedback);
//
//        feedbackSubmit.setText("Submit Feedback ("+Feedbacklist.size()+")");
//    }
}


