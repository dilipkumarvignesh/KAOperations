package iskconbangalore.org.kaoperations;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MenuFragment extends Fragment {
    private static final String TAG ="Profile";
    private Button btnTest,MenuUpdate;
    TextView selectedDate,Tbreakfast,Tlunch,Tdinner;
    private int year, month, day;
    private Calendar calendar;
    ListView listBreakfast,listLunch,listDinner;
    LayoutInflater layoutinflater;

    private int admin=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_menu,container,false);
        btnTest = view.findViewById(R.id.selDate);
       // MenuUpdate = view.findViewById(R.id.UpdateMenu);
        selectedDate=view.findViewById(R.id.selectedDate);
        listBreakfast = view.findViewById(R.id.listBreakfast);
//        listLunch = view.findViewById(R.id.listLunch);
//        listDinner = view.findViewById(R.id.listDinner);
        layoutinflater = inflater;

//        UtilityFunctions.getUserPoints(this.getActivity(),new firebaseCallBack() {
//
//            @Override
//            public void onCallback(Users user) {
//                Log.d("info","InterfacePointsValue:"+user.getPoints());
//                admin= user.getAdmin();
//                Log.d("info","admin Value:"+admin);
//                if(admin == 1)
//                {
//
//                    MenuUpdate.setVisibility(View.VISIBLE);
//                }
//            }
//
//
//        });



        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day =  calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            setDate();
            }

        });
//        MenuUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent k = new Intent(getActivity(),MenuUpdate.class);
//
//                startActivity(k);
//            }
//        });

//        Tbreakfast = view.findViewById(R.id.menu_breakfast);
//        Tlunch = view.findViewById(R.id.menu_lunch);
//        Tdinner = view.findViewById(R.id.menu_Dinner);
//        root = FirebaseDatabase.getInstance().getReference();
//        radioMenuGroup=(RadioGroup)findViewById(R.id.radioGroup);
//        btnTest.setOnClickListener((view){
//                Toast.makeText(getActivity(),"Testing",Toast.LENGTH_LONG).show();
//
//    });

        return view;

    }

    private ArrayList<ListCell> sortAndAddSections(ArrayList<ListCell> itemList)
    {

        ArrayList<ListCell> tempList = new ArrayList<ListCell>();
        //First we sort the array
       // Collections.sort(itemList);

        //Loops thorugh the list and add a section before each sectioncell start
        String header = "";
        for(int i = 0; i < itemList.size(); i++)
        {
            //If it is the start of a new section we create a new listcell and add it to our array
            if(header != itemList.get(i).getCategory()){
                ListCell sectionCell = new ListCell(itemList.get(i).getCategory(), null);
                sectionCell.setToSectionHeader();
                tempList.add(sectionCell);
                header = itemList.get(i).getCategory();
            }
            tempList.add(itemList.get(i));
        }

        return tempList;
    }

    public void displayMenu()
    {
        SharedPreferences userInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String MenuType =  userInfo.getString("MenuType","NA").toString();
        DatabaseReference todayDateNode = FirebaseDatabase.getInstance().getReference("Menu").child(selectedDate.getText().toString()).child(MenuType);

        todayDateNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Breakfast"))
                {
                    String breakfast =  dataSnapshot.child("Breakfast").getValue().toString();
                    String Lunch = dataSnapshot.child("Lunch").getValue().toString();
                    String Dinner = dataSnapshot.child("Dinner").getValue().toString();

               //     ArrayAdapter Breakfastadapter = new ArrayAdapter();
                    ArrayAdapter Breakfastadapter,Lunchadapter,Dinneradapter;
                    String[] BreakfastItems = breakfast.split(",");

                    ArrayList items = new ArrayList();

                    for (int i =0;i<BreakfastItems.length;i++)
                    {
                        items.add(new ListCell(BreakfastItems[i], "Breakfast"));
                    }
                    
                    String[] LunchItems = Lunch.split(",");

                    for (int i =0;i<LunchItems.length;i++)
                    {
                        items.add(new ListCell(LunchItems[i], "Lunch"));
                    }
//                     Lunchadapter = new ArrayAdapter<String>(getActivity(),
//                            R.layout.menu_list, LunchItems);

                    String[] DinnerItems = Dinner.split(",");

                    for (int i =0;i<DinnerItems.length;i++)
                    {
                        items.add(new ListCell(DinnerItems[i], "Dinner"));
                    }

                    items = sortAndAddSections(items);

                    ListAdapter adapter = new ListAdapter(getActivity(), items);
                    listBreakfast.setAdapter(adapter);

//
                    Log.d("info","Breakfast:"+breakfast);
                    Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());


                    //progress.dismiss();
                    // float AverageR = AverageRating/childCount;

                }
                else
                {
                    //Toast.makeText(MenuDisplay.this,"Menu Not Available",Toast.LENGTH_LONG).show();
                    //progress.dismiss();
//                    Tbreakfast.setText("NA");
//                    Tlunch.setText("NA");
//                    Tdinner.setText("NA");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    public void setDate() {

        DatePickerFragment picker = new DatePickerFragment();

        picker.show(getActivity().getFragmentManager(), "datePicker");
        picker.setCallBack(onDate);
        Toast.makeText(getActivity(), "Set Date",
                Toast.LENGTH_SHORT)
                .show();
    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        // TODO Auto-generated method stub
//        if (id == 999) {
//            return new DatePickerDialog(getActivity(),
//                    myDateListener, year, month, day);
//        }
//        return null;
//    }
//private void showDatePicker() {
//    DatePickerFragment date = new DatePickerFragment();
//    /**
//     * Set Up Current Date Into dialog
//     */
//    Calendar calender = Calendar.getInstance();
//    Bundle args = new Bundle();
//    args.putInt("year", calender.get(Calendar.YEAR));
//    args.putInt("month", calender.get(Calendar.MONTH));
//    args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
//    date.setArguments(args);
//    /**
//     * Set Call back to capture selected date
//     */
//    date.setCallBack(ondate);
//    date.show(getFragmentManager(), "Date Picker");
//}
//


//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//
////            edittext.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
////                    + "-" + String.valueOf(year));
//        }
//    };
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
        displayMenu();
    }

}
