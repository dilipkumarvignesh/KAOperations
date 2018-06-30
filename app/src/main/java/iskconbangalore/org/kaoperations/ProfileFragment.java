package iskconbangalore.org.kaoperations;

import android.content.Context;
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
    private Button btnTest;
    private int UserPoints;
    Context con;
    private TextView TUsername, TResidency, TMenuType, TPoints;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        con = getActivity();

        TUsername = view.findViewById(R.id.Username);
        TResidency = view.findViewById(R.id.UserResidency);
        TMenuType = view.findViewById(R.id.UserMenuType);
        TPoints = view.findViewById(R.id.UserPoints);

        SharedPreferences userInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        UtilityFunctions.getUserPoints(this.getActivity(), new firebaseCallBack() {

            @Override
            public void onCallback(int value) {
                Log.d("info", "InterfacePointsValue:" + value);
                UserPoints = value;
            }


        });
        String MenuType = userInfo.getString("MenuType", "NA").toString();
        String Username = userInfo.getString("Name", "NA").toString();
        String Residency = userInfo.getString("Residency", "NA").toString();
        int Points = userInfo.getInt("Points", 0);

        TUsername.setText(Username);
        TResidency.setText(Residency);
        TMenuType.setText(MenuType);
        TPoints.setText("" + UserPoints);
        return view;
    }




    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences userInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        UtilityFunctions.getUserPoints(this.getActivity(), new firebaseCallBack() {

            @Override
            public void onCallback(int value) {
                Log.d("info", "InterfacePointsValue:" + value);
                UserPoints = value;
                TPoints.setText("" + UserPoints);
            }


        });

        // put your code here...

    }
}
