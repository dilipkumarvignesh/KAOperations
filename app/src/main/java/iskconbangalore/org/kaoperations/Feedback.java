package iskconbangalore.org.kaoperations;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Feedback extends AppCompatActivity {
    private TextView selectedDate;
    private int year, month, day;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        selectedDate = findViewById(R.id.selectedDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day =  calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);



    }


        public void selectDate(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "date picker");
        newFragment.setCallBack(onDate);
    }

    private DatePickerDialog.OnDateSetListener onDate = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = months
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        String mon="";
        if(month<10)
            mon="0"+month;
        else
            mon=""+month;

        String formattedDate = UtilityFunctions.getFormattedDate(year,month,day);
        selectedDate.setText(formattedDate);

        final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = root.child("Feedback").child(selectedDate.getText().toString());

        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("info", "FeedbackValues:" + dataSnapshot.getValue());
                Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Log.d("info","Item:"+postSnapshot.getValue());
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }


        });

    }


}
