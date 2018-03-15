package iskconbangalore.org.kaoperations;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Rating extends AppCompatActivity implements View.OnClickListener {

    Button submit;
    RatingBar rating1;
    String email,name;
    Boolean update ;
    DatabaseReference root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_rating);
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        email = extras.getString("email");

        update = false;
        rating1 = (RatingBar)findViewById(R.id.rating1);

        submit = (Button) findViewById(R.id.RatingSubmit);
        submit.setOnClickListener(this);
    }

    public void verifyUpdate()
    {
        String date = UtilityFunctions.getDate();
        DatabaseReference userNameRef = root.child(name).child(date);

        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //    Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());
                if(!dataSnapshot.exists())
                {
                    update = true;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    public void onClick(View v) {
        Log.d("info", "Button clicked:" + v.getId());
        switch (v.getId()) {

            case R.id.RatingSubmit: {
                String date = UtilityFunctions.getDate();
                DatabaseReference userNameRef = root.child("users").child(name).child(date);
                final Context con = this;
                userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Log.d("info","dataSnapValue:"+dataSnapshot.getValue());
                        //    Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());
                        if(!dataSnapshot.exists())
                        {
                            float rating = rating1.getRating();


                            String Date = UtilityFunctions.getDate();


                            String key = root.child("Ratings").child(Date).push().getKey();
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/Ratings/" + Date + "/" + key, rating);
                            childUpdates.put("/users/" + name + "/" + Date, rating);

                            root.updateChildren(childUpdates);
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

                break;

            }

        }
    }
}