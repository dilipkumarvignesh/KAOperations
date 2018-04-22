package iskconbangalore.org.kaoperations;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button update,updateFeedback,report,rating;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private String name,email;
    DatabaseReference root;
    ArrayList<String> ResidencyNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference();
// ...

// ..Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
//noinspection RestrictedApi
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
             name = user.getDisplayName();
             email = user.getEmail();
//            String no = user.
            Log.d("info","Username:"+name);
            Log.d("info","Email:"+email);
        }
        else
        {
          name="";
        }

      //  verifySignIn();
        ResidencyNames=new ArrayList<>();
//        update =(Button)findViewById(R.id.update);
//        report = (Button)findViewById(R.id.Report);
//        rating= (Button)findViewById(R.id.Rating);
//        update.setOnClickListener(this);
//        updateFeedback = (Button)findViewById(R.id.updateFeedback);
//        updateFeedback.setOnClickListener(this);
//        report.setOnClickListener(this);
//        rating.setOnClickListener(this);

//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////
//                updateResponse();
//            }
//        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED);
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, 100);
    }
//
//    @Override
//    public void onRequestPermissions(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 100) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Now user should be able to use camera
//            }
//            else {
//                // Your app will not have this permission. Turn off all functions
//                // that require this permission or it will force close like your
//                // original question
//            }
//        }
//    }
    public void verifySignIn()
    {
        DatabaseReference userNameRef = root.child("users").child(name).child("Name");
        final Context con = this;
        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("info","dataSnapValue:"+dataSnapshot.getValue());
                //    Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());
                if(!dataSnapshot.exists())
                {
                    Intent k = new Intent(getApplicationContext(),SignInfo.class);
                    k.putExtra("name",name);
                    startActivity(k);
                }
                else
                {
                    Toast.makeText(con,"Welcome Back "+ name,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
      //  FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                name = user.getDisplayName();
                email = user.getEmail();
                verifySignIn();
                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }
//    public void onClick(View v) {
//        Log.d("info", "Button clicked:" + v.getId());
//        switch (v.getId()) {
//
//            case R.id.update: {
//                updateResponse();
//                break;
//            }
//            case R.id.updateFeedback: {
//
//                updateFeedback();
//                break;
//            }
//            case R.id.Report:{
////                Intent k = new Intent(getApplicationContext(),SignInfo.class);
////                Intent k = new Intent(getApplicationContext(),Report.class);
////                k.putExtra("name",name);
////                k.putExtra("email",email);
////                startActivity(k);
////                Intent k = new Intent(getApplicationContext(),MenuUpdate.class);
////                startActivity(k);
//
//                Intent k = new Intent(getApplicationContext(),Feedback.class);
//                startActivity(k);
//                break;
//            }
//            case R.id.Rating:{
//                Intent k = new Intent(getApplicationContext(),Rating.class);
//                k.putExtra("name",name);
//                k.putExtra("email",email);
//                startActivity(k);
//            }
//        }
//    }
//    public void updateResponse()
//    {
//        Spinner residency = (Spinner) findViewById(R.id.ResidencySpinner);
//        Spinner Time = (Spinner) findViewById(R.id.TimeSpinner);
//        String residencyValue = residency.getSelectedItem().toString();
//        String TimeValue = Time.getSelectedItem().toString();
//        EditText feedback = (EditText)findViewById(R.id.Feedback);
//        dbOperations.writeTimeUpdate(residencyValue,TimeValue);
//
//
//
//    }
//    public void updateFeedback()
//    {
//        Spinner residency = (Spinner) findViewById(R.id.ResidencySpinner);
//        Spinner Time = (Spinner) findViewById(R.id.TimeSpinner);
//        String residencyValue = residency.getSelectedItem().toString();
//        String TimeValue = Time.getSelectedItem().toString();
//        EditText feedback = (EditText)findViewById(R.id.Feedback);
//        dbOperations.writeFeedbackUpdate(residencyValue,TimeValue,feedback.getText().toString());
//    }

    public void navigateMenu(View view)
    {
        Intent k = new Intent(getApplicationContext(),MenuDisplay.class);
        startActivity(k);
    }

    public void navigateFeedback(View view)
    {
        Intent k = new Intent(getApplicationContext(),Feedback.class);
        startActivity(k);
    }

    public void navigateAdmin(View view)
    {
        Intent k = new Intent(getApplicationContext(),MenuUpdate.class);
               startActivity(k);
    }
}
