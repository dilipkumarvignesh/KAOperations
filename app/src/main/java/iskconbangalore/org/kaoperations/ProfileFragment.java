package iskconbangalore.org.kaoperations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "Profile";
    private Button sendNotification,MenuUpdate,fetchFeedback;
    private int UserPoints;
    private String Username,Residency;
    Context con;
    private TextView TUsername, TResidency, TMenuType, TPoints;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        con = getActivity();

        TUsername = view.findViewById(R.id.Username);
        TResidency = view.findViewById(R.id.UserResidency);
       // TMenuType = view.findViewById(R.id.UserMenuType);
        TPoints = view.findViewById(R.id.UserPoints);

        SharedPreferences userInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        UtilityFunctions.getUserPoints(this.getActivity(), new firebaseCallBack() {

            @Override
            public void onCallback(Users user) {
                Log.d("info", "InterfacePointsValue:" + user.getPoints());
                UserPoints = user.getPoints();
//                Username = user.getName();
//                Residency = user.getResidency();

            }


        });
        String MenuType = userInfo.getString("MenuType", "NA").toString();
        String Username = userInfo.getString("Name", "NA").toString();
        String Residency = userInfo.getString("Residency", "NA").toString();
        int Points = userInfo.getInt("Points", 0);

        TUsername.setText(Username);
        TResidency.setText(Residency + " Residency");
      //  TMenuType.setText(MenuType + " Menu");
        TPoints.setText("Feedback Points:  " + UserPoints);


        MenuUpdate = view.findViewById(R.id.UpdateMenu);
        sendNotification = view.findViewById(R.id.sendNotification);
       // fetchFeedback = view.findViewById(R.id.fetchFeedback);

        UtilityFunctions.getUserPoints(this.getActivity(),new firebaseCallBack() {

            @Override
            public void onCallback(Users user) {
                Log.d("info","InterfacePointsValue:"+user.getPoints());
                int admin= user.getAdmin();
                Log.d("info","admin Value:"+admin);
                if(admin == 1)
                {
                    sendNotification.setVisibility(View.VISIBLE);
                    //fetchFeedback.setVisibility(View.VISIBLE);
                    MenuUpdate.setVisibility(View.VISIBLE);

                }
            }


        });
//        fetchFeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent k = new Intent(getActivity(),Feedback.class);
//
//                startActivity(k);
//            }
//
//        });
        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getActivity(),AdminNotifications.class);

                startActivity(k);
            }

        });

        MenuUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getActivity(),MenuUpdate.class);

                startActivity(k);
            }

        });
        return view;


    }




    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences userInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        UtilityFunctions.getUserPoints(this.getActivity(), new firebaseCallBack() {

            @Override
            public void onCallback(Users user) {
                Log.d("info", "InterfacePointsValue:" + user.getPoints());
                UserPoints = user.getPoints();
                TPoints.setText("Feedback Points:" + UserPoints);
            }


        });

        String Username = userInfo.getString("Name", "NA").toString();
        String Residency = userInfo.getString("Residency", "NA").toString();


        TUsername.setText(Username);
        TResidency.setText(Residency + " Residency");

        // put your code here...

    }
}
