package iskconbangalore.org.kaoperations;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MenuDisplay extends AppCompatActivity {

    TextView selectedDate,Tbreakfast,Tlunch,Tdinner;
    private int year, month, day;


    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display);
        selectedDate=findViewById(R.id.selectedDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day =  calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        Tbreakfast = findViewById(R.id.menu_breakfast);
        Tlunch = findViewById(R.id.menu_lunch);
        Tdinner = findViewById(R.id.menu_Dinner);

        displayMenu();
    }





    public void displayMenu()
    {
        DatabaseReference todayDateNode = FirebaseDatabase.getInstance().getReference("Menu").child(selectedDate.getText().toString()).child("Special");
        final Context delActivity = this;
        final ProgressDialog progress = new ProgressDialog(delActivity);
        progress.setTitle("Loading");
        progress.setMessage("Please Wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        todayDateNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Breakfast"))
                {
                String breakfast =  dataSnapshot.child("Breakfast").getValue().toString();
                String Lunch = dataSnapshot.child("Lunch").getValue().toString();
                String Dinner = dataSnapshot.child("Dinner").getValue().toString();

                String Tbreak = "BreakFast" + ":"+ breakfast;
                String Tlun = "Lunch" + ":"+ Lunch;
                String TDin = "Dinner" + ":"+ Dinner;

                Tbreakfast.setText(Tbreak);
                Tlunch.setText(Tlun);
                Tdinner.setText(TDin);

                   Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());


                progress.dismiss();
               // float AverageR = AverageRating/childCount;

            }
            else
                {
                    Toast.makeText(MenuDisplay.this,"Menu Not Available",Toast.LENGTH_LONG).show();
                    progress.dismiss();
                    Tbreakfast.setText("NA");
                    Tlunch.setText("NA");
                    Tdinner.setText("NA");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT)
//                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        selectedDate.setText(new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
        displayMenu();
    }
}
