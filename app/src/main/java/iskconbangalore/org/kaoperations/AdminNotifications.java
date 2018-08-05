package iskconbangalore.org.kaoperations;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class AdminNotifications extends AppCompatActivity {
    private EditText title,Body;
    private Button sendMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notifications);

        title = findViewById(R.id.notificationTitle);
        Body = findViewById(R.id.notificationBody);
        sendMessage = findViewById(R.id.sendMessage);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }

        });
    }

    private void sendNotification()
    {
        final String titleContent = title.getText().toString();
        final String BodyContent = Body.getText().toString();
        final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference NotificationRef = root.child("messages");
        final Context con = this;
        //Log.d("info","Dialog Points:"+points);

        NotificationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("info", "dataSnapValue:" + dataSnapshot.getValue());



                    String Date = UtilityFunctions.getDate();

                    Map<String,String> message = new HashMap<>();
                    message.put("name",titleContent);
                    message.put("text",BodyContent);
                    message.put("tmstmp",new Date().toString());


                    String key = root.child("messages").push().getKey();
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put("/messages/" + key, message);


                    root.updateChildren(childUpdates);
                Toast.makeText(con,"Notification Sent",Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(con, "Already updated for today", Toast.LENGTH_LONG).show();
//                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
