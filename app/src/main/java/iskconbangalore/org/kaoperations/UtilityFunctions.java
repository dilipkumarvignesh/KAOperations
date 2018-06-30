package iskconbangalore.org.kaoperations;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UtilityFunctions  {


private int UserPoints;
private static int points;
public static String getDate()
{
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    Date now = new Date();
    String strDate = sdfDate.format(now);
    return strDate;

}

public static String getTime()
{

    SimpleDateFormat currentTime = new SimpleDateFormat(" HH:mm:ss");
    currentTime.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
    Date now = new Date();
    String formattedDate = currentTime.format(now);
    return formattedDate;

}


public static int getUserPoints(Context con,firebaseCallBack callback)
{

    final firebaseCallBack callBack = callback;
    SharedPreferences userInfo = con.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

    String Name =  userInfo.getString("DisplayName","NA").toString();
    Log.d("info","UtilityFunctionName:"+Name);
    DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("users").child(Name).child("Points");
    userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()) {
                // Log.d("info","dataSnapValue:"+dataSnapshot.getValue(Users.class));
                points = dataSnapshot.getValue(Integer.class);
                callBack.onCallback(points);
                Log.d("info","Points Value:"+points);
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());
            points = 0;
        }
    });
    return points;
}


}
