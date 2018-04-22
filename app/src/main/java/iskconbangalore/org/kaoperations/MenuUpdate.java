package iskconbangalore.org.kaoperations;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MenuUpdate extends AppCompatActivity {
    private int year, month, day;
    TextView selectedDate;
    private Calendar calendar;
    DatabaseReference root;
    EditText Breakfast,Lunch,Dinner;
    private RadioGroup radioMenuGroup;
    private RadioButton radioMenuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_update);
        selectedDate=findViewById(R.id.selectedDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day =  calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        Breakfast = findViewById(R.id.input_morning_prasadam);
        Lunch = findViewById(R.id.input_lunch_prasadam);
        Dinner = findViewById(R.id.input_dinner_prasadam);
        root = FirebaseDatabase.getInstance().getReference();
        radioMenuGroup=(RadioGroup)findViewById(R.id.radioGroup);
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
    }

    public void setMenu(View view)
    {
        int selectedId=radioMenuGroup.getCheckedRadioButtonId();
        radioMenuButton=findViewById(selectedId);
        final String MenuType = radioMenuButton.getText().toString();
        DatabaseReference menuRef = root.child("Menu").child(selectedDate.getText().toString()).child(MenuType);
        final Context con = this;
        menuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("info","dataSnapValue:"+dataSnapshot.getValue());
                //    Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());
                if(!dataSnapshot.exists())
                {
                    String BreakfastMenu = Breakfast.getText().toString();
                    String LunchMenu = Lunch.getText().toString();
                    String DinnerMenu = Dinner.getText().toString();



//                    float rating = rating1.getRating();
//
//
//                    String Date = UtilityFunctions.getDate();
//
//                    String key = root.child("Ratings").child(Date).push().getKey();
                     Map<String, Object> Menu_Updates = new HashMap<>();
                     Menu_Updates.put("Breakfast",BreakfastMenu);
                     Menu_Updates.put("Lunch",LunchMenu);
                     Menu_Updates.put("Dinner",DinnerMenu);

//                    childUpdates.put("/Ratings/" + Date + "/" + key, rating);
//                    childUpdates.put("/users/" + name + "/" + Date, rating);

                    root.child("Menu").child(selectedDate.getText().toString()).child(MenuType).updateChildren(Menu_Updates);
                    Toast.makeText(con,"Menu Updated",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(con,"Already updated for today",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}
