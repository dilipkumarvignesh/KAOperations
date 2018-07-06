package iskconbangalore.org.kaoperations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;

public class tabbedMain extends AppCompatActivity {

    private static final String TAG = "TabbedMain";
    public static final String MyPREFERENCES = "UserInfo" ;
    private SectionsPageAdapter mSectionsPageAdapter;
    SharedPreferences sharedpreferences;
    private ViewPager mViewPager;
    Button update,updateFeedback,report,rating;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private String name,email;
    DatabaseReference root;
    ArrayList<String> ResidencyNames;
    Users user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_main);
        mAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference();
// ...
        Log.d("info","Inside Tabbed Main");

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo( R.mipmap.ic_krishna_round )
                        .setIsSmartLockEnabled( false )
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build()))

                        .build(),
                RC_SIGN_IN);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            name = user.getDisplayName();
            email = user.getEmail();
            String mUserprofileUrl = user.getPhotoUrl().toString();
            Log.d("info","Photo URL:"+mUserprofileUrl);
//            String no = user.
            Log.d("info","Username:"+name);
            Log.d("info","Email:"+email);
        }
        else
        {
            name="";
        }

        //  verifySignIn();
        ResidencyNames = new ArrayList<>();
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        FirebaseMessaging.getInstance().subscribeToTopic("updates");


    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(), "Profile");
        adapter.addFragment(new MenuFragment(), "Menu");
        adapter.addFragment(new FeedbackFragment(), "Feedback");
        viewPager.setAdapter(adapter);
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

    public void verifySignIn()
    {
        DatabaseReference userNameRef = root.child("users").child(name);
        final Context con = this;
            userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        // Log.d("info","dataSnapValue:"+dataSnapshot.getValue(Users.class));
                        user = dataSnapshot.getValue(Users.class);

                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("Name", user.getName());
                            editor.putString("MenuType", user.getMenuType());
                            editor.putString("Residency", user.getResidency());
                            editor.putString("DisplayName", name);
                            editor.commit();



                    }
                    else
                    {
                        Intent k = new Intent(getApplicationContext(), SignInfo.class);
                        k.putExtra("name", name);
                        startActivity(k);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
    }


}
