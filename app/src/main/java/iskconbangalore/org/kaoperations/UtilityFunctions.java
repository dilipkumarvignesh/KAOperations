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
private static Users user;
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


public static Users getUserPoints(Context con,firebaseCallBack callback)
{

    final firebaseCallBack callBack = callback;
    SharedPreferences userInfo = con.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

    String Name =  userInfo.getString("DisplayName","NA").toString();
    Log.d("info","UtilityFunctionName:"+Name);
    DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("users").child(Name);
    userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            if (dataSnapshot.exists()) {
                // Log.d("info","dataSnapValue:"+dataSnapshot.getValue(Users.class));
                user = dataSnapshot.getValue(Users.class);
                callBack.onCallback(user);
                Log.d("info","Points Value:"+user.getPoints());
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());

        }
    });
    return user;
}

    public static String getFormattedDate(int year, int month, int day) {
        String mon="";
        String lday = "";
        if(day < 10)
        {
            lday = "0"+day;
        }
        else
        {
            lday = ""+day;
        }
        if(month<10)
        {
            mon="0"+month;
        }
        else
            mon = ""+month;

        String formattedDate =  ""+year+"-"+mon+"-"+lday;
        Log.d("info","Formatted Date:"+formattedDate);
               return formattedDate;
    }
}
