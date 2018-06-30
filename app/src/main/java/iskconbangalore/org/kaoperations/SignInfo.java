package iskconbangalore.org.kaoperations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignInfo extends AppCompatActivity {
    EditText Name, Number, Email;
    Spinner Residency;
    RadioGroup menuType;
    RadioButton menuChoose;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_info);
        Name=findViewById(R.id.input_name);
        Number=findViewById(R.id.input_number);
       // Email=findViewById(R.id.input_email);
        Residency = findViewById(R.id.spinner);
        menuType = findViewById(R.id.radioGroup);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        getResidency();

    }

    public void updateDetails(View view)
    {
        String Nam = Name.getText().toString();
        String No = Number.getText().toString();
      //  String email = Email.getText().toString();
        String Res = Residency.getSelectedItem().toString();
        int selectedId=menuType.getCheckedRadioButtonId();
        menuChoose=findViewById(selectedId);
        final String MenuType = menuChoose.getText().toString();
        Map<String, Object> User_Updates = new HashMap<>();
        User_Updates.put("Name",Nam);
        User_Updates.put("Number",No);
        User_Updates.put("Points",0);
      //  User_Updates.put("Email",email);
        User_Updates.put("Residency",Res);
        User_Updates.put("MenuType",MenuType);
//                    childUpdates.put("/Ratings/" + Date + "/" + key, rating);
//                    childUpdates.put("/users/" + name + "/" + Date, rating);

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        root.child("users").child(name).updateChildren(User_Updates);
        Toast.makeText(this,"User Details Updated",Toast.LENGTH_LONG).show();

    }

    public void getResidency()
    {
        DatabaseReference ResidencyNode = FirebaseDatabase.getInstance().getReference("Residency");
        final List<String> residencies = new ArrayList<String>();
        ResidencyNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Log.d("info","Residency Name:"+snapshot.getValue());
                   residencies.add(snapshot.getValue().toString());
                }
                ArrayAdapter<String> ResidencyAdapter = new ArrayAdapter<String>(SignInfo.this, android.R.layout.simple_spinner_item, residencies);
                ResidencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Residency.setAdapter(ResidencyAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
