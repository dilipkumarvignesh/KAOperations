package iskconbangalore.org.kaoperations;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by i308830 on 1/6/18.
 */

public class dbOperations {

    private DatabaseReference mDatabase;
  //  List<SchoolData> routes = new ArrayList<>();


    public void getSchoolData(String locationData)
    {
        DatabaseReference routes = FirebaseDatabase.getInstance().getReference(locationData);
    }
    public void writeRouteData()
    {
//        DatabaseReference routes = FirebaseDatabase.getInstance().getReference("");
//        RouteData route1 = new RouteData("1","ABC");
//        String key = routes.push().getKey();
//        routes.child(key).setValue(route1);

    }

    public static void writeTimeUpdate(String Residency,String Time)
    {
        String toDate = UtilityFunctions.getDate();
        String time = UtilityFunctions.getTime();
        Log.d("info","TimeValue:"+time);
        DatabaseReference todayDateNode = FirebaseDatabase.getInstance().getReference(toDate);
        todayDateNode.child(Residency).child(Time+"Del").setValue(time);
        todayDateNode.child(Residency).child("residency").setValue(Residency);
    }


    public static void writeFeedbackUpdate(String Residency,String Time,String feedback)
    {
        String toDate = UtilityFunctions.getDate();
        String time = UtilityFunctions.getTime();
        Log.d("info","TimeValue:"+time);
        ResUpdate res = new ResUpdate();
        res.residency=Residency;
        //res.updateTime=Time;

        DatabaseReference todayDateNode = FirebaseDatabase.getInstance().getReference(toDate);
        todayDateNode.child(Residency).child(Time+"Feedback").setValue(feedback);
        todayDateNode.child(Residency).child("residency").setValue(Residency );
    }

    public static void readData(String toDate)
    {
        DatabaseReference todayDateNode = FirebaseDatabase.getInstance().getReference(toDate);

    }
}
