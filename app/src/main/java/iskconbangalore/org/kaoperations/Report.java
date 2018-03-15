package iskconbangalore.org.kaoperations;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Report extends AppCompatActivity {
    MyItemRecyclerViewAdapter recAdapter;
    ArrayList<ResUpdate> ResData = new ArrayList<ResUpdate>();
    float AverageRating ;
 //   private OnListFragmentInteractionListener mListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Log.d("info","Inside report activity");
        getTodayReport();
       // RecyclerView recList = (RecyclerView) findViewById(R.id.list);
        ArrayList resList = new ArrayList<ResUpdate>();

       // recList.setHasFixedSize(true);
      //  LinearLayoutManager llm = new LinearLayoutManager(this);
      //  llm.setOrientation(LinearLayoutManager.VERTICAL);
       // recList.setLayoutManager(llm);
        //getRouteData(UtilityFunctions.getDate());
      //   recAdapter = new MyItemRecyclerViewAdapter(ResData);
//        resList.add(new ResUpdate("hello","hi","bi","he","he"));
   //     recList.setAdapter(recAdapter);

    }

    public void getRouteData(String Date) {

        DatabaseReference todayDateNode = FirebaseDatabase.getInstance().getReference(Date);
  //      String finalValue = LocationValue+RouteValue;
   //     Log.d("info","LocationRoute:"+finalValue);
       // DatabaseReference ref = database.getReference(finalValue);
        final Context delActivity = this;
        final ProgressDialog progress = new ProgressDialog(delActivity);
        progress.setTitle("Loading");
        progress.setMessage("Please Wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        // Attach a listener to read the data at our posts reference
        todayDateNode.orderByChild("order").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //    Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ResUpdate res= data.getValue(ResUpdate.class);
                    Log.d("info","Res:"+data.getValue(ResUpdate.class));
                    Log.d("info","Residency Morning Feed:"+res.morningFeedback);
                    Log.d("info","Residency Evening Feed:"+res.eveningFeedback);
                    Log.d("info","Residency Morning Time:"+res.morningDel);
                    Log.d("info","Residency Devening Time:"+res.eveningDel);
                    Log.d("info","Residency :"+res.residency);
                    ResData.add(res);
                    recAdapter.swapDataSet(ResData);
                    //notify
//                    Log.d("info","ResTime:"+data.child("Morning").getChildren());
//                    Log.d("info","PrasadTime:"+data.getChildren());
//          //          ResUpdate res = routeSnapShot.getValue(ResUpdate.class);
//             //       ResData.add(res);
//                    Log.d("info","Schools size:"+ResData.size());
             //       Log.d("info", "School Name:" + );
//                    Log.d("info", "Temp Quantity:" + school.TempQuantity);
//                    Log.d("info", "Quantity:" + school.Quantity);
//                    //   Log.d("info", "GPS_Coord:" + school.GPS_Coord);
//                    Log.d("info","Exp_Time:"+school.Exp_Time);


                }
             //   schoolSize = schoolData.size();
                progress.dismiss();
              //  Toast.makeText(delActivity,"All Data Downloaded:"+schoolData.size(),Toast.LENGTH_LONG).show();
               // setSchoolDataUi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    getTodayReport();
    }
    public void getTodayReport()
    {
        String Date = UtilityFunctions.getDate();
        final Context delActivity = this;
        final ProgressDialog progress = new ProgressDialog(delActivity);
        progress.setTitle("Loading");
        progress.setMessage("Please Wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        AverageRating = 0;
        DatabaseReference todayDateNode = FirebaseDatabase.getInstance().getReference("Ratings").child(Date);
        todayDateNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long childCount =  dataSnapshot.getChildrenCount();
                Log.d("info","Total Count:"+childCount);
                //    Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String rating = data.getValue().toString();
                    Log.d("info","Given Rating:"+rating);
                    AverageRating += Integer.parseInt(rating);

                }

                progress.dismiss();
                float AverageR = AverageRating/childCount;
                Log.d("info","Average Rating:"+AverageR);
                RatingBar aveRatingBar = (RatingBar)findViewById(R.id.AveRating);
                aveRatingBar.setRating(AverageR);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




    }
}
