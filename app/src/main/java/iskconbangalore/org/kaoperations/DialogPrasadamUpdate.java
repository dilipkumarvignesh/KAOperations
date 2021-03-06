package iskconbangalore.org.kaoperations;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class DialogPrasadamUpdate extends DialogFragment {
    String name;
    String Item,Meal;
    String selectedDate;
    static int UserPoints;
    OnHeadlineSelectedListener mCallback;
    static String displayName;
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);

        public void onDialogNegativeClick(DialogFragment dialog);
    }

    public interface OnHeadlineSelectedListener {
        public void onFeedbackAdded(Map<String,String> feedback);
    }


    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = this.getActivity().getLayoutInflater().inflate(R.layout.dialog_prasadam_update, null);
        LayoutInflater inflater = getActivity().getLayoutInflater();
       // LayoutInflater inflater = (LayoutInflater)this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SharedPreferences userInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        final View view1 = inflater.inflate(R.layout.dialog_prasadam_update, null, false);

        name =  userInfo.getString("Name","NA").toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle mArgs = getArguments();
        Item = mArgs.getString("Item");
        selectedDate = mArgs.getString("SelectedDate");
        Meal = mArgs.getString("Meal");
        Log.d("info","Menu Selected Date"+selectedDate);
        UtilityFunctions.getUserPoints(this.getActivity(),new firebaseCallBack() {

            @Override
            public void onCallback(Users user) {
                Log.d("info","InterfacePointsValue:"+user.getPoints());
                UserPoints = user.getPoints();
                //  displayName = user.getName();
            }


        });

        builder
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setView(inflater.inflate(R.layout.dialog_prasadam_update, null))
                // Set the action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        mListener.onDialogPositiveClick(DialogPrasadamUpdate.this);
                        String Quantity = "", Quality = "", Feedback="";

                        TextView title = view.findViewById(R.id.PrasadamName);
                        title.setText(Item);

                        RadioGroup RQuantity = ((AlertDialog) dialog).findViewById(R.id.RadioQuantity);
                        int iQuantitySelected = RQuantity.getCheckedRadioButtonId();
                        Log.d("info", "QuantitySelected:" + iQuantitySelected);
                        if (iQuantitySelected != -1) {
                            RadioButton radioQuantity = ((AlertDialog) dialog).findViewById(iQuantitySelected);
                            Quantity = radioQuantity.getText().toString();
                        }

                        RadioGroup RQuality = ((AlertDialog) dialog).findViewById(R.id.RadioQuality);
                        int iQualitySelected = RQuality.getCheckedRadioButtonId();
                        if (iQualitySelected != -1) {
                            RadioButton radioQuality = ((AlertDialog) dialog).findViewById(iQualitySelected);
                            Quality = radioQuality.getText().toString();
                        }


                        RadioGroup RFeedback = ((AlertDialog) dialog).findViewById(R.id.RadioFeedback);
                        int iFeedbackSelected = RFeedback.getCheckedRadioButtonId();
                        if (iFeedbackSelected != -1) {
                            RadioButton radioFeedback = ((AlertDialog) dialog).findViewById(iFeedbackSelected);
                            Feedback= radioFeedback.getText().toString();
                        }
                        Toast.makeText(getActivity(), "Feedback Updated", Toast.LENGTH_LONG).show();

                        EditText comments = ((AlertDialog)dialog).findViewById(R.id.textComments);
                        String commentText = comments.getText().toString();

                        UpdateFeedback(Quantity, Quality,Feedback,commentText);



                    }


                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(DialogPrasadamUpdate.this);
                    }
                });

        return builder.create();
    }


    public void UpdateFeedback(String Quantity, String Quality,String Feedback,String comments) {
        String date = UtilityFunctions.getDate();
        final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = root.child("users").child(name).child(date);
        final Context con = this.getActivity();
        final String QualityFeedback = Quality;
        final String QuantityFeedback = Quantity;
        final String OverallFeedback = Feedback;
        final String Comments = comments;

        SharedPreferences userInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        //Log.d("info","Dialog Points:"+points);
        displayName =  userInfo.getString("DisplayName","NA").toString();
     //   sendEmail(QualityFeedback,QuantityFeedback,OverallFeedback,Comments);
        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("info", "dataSnapValue:" + dataSnapshot.getValue());
                //    Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());
                if (!dataSnapshot.exists()) {


                    String Date = UtilityFunctions.getDate();

                    Map<String,String> feedback = new HashMap<>();
                    feedback.put("Quantity",QuantityFeedback);
                    feedback.put("Quality",QualityFeedback);
                    feedback.put("Taste",OverallFeedback);
                    feedback.put("Comments",Comments);
                    feedback.put("Item",Item);
                    feedback.put("Meal",Meal);
                    feedback.put("SelectedDate",selectedDate);


                    String key = root.child("Feedback").child(Date).push().getKey();
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put("/Feedback/" + selectedDate + "/" + key+"/"+Item, feedback);
                    childUpdates.put("/users/"+displayName+"/Points",UserPoints+1);
                    childUpdates.put("/users/" + displayName + "/Feedback/" + selectedDate+"/"+Item, feedback);


                   mCallback.onFeedbackAdded(feedback);

                    root.updateChildren(childUpdates);

                } else {
                    Toast.makeText(con, "Already updated for today", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

//    protected void sendEmail(String QualityFeedback,String QuantityFeedback,String OverallFeedback,String Comments) {
//        Log.i("Send email", "");
//        String[] TO = {"krmt.office@hkm-group.org"};
//        String[] CC = {"admin.folk@hkm-group.org"};
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//
//        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.setType("text/plain");
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KrishnaAmrita Prasadam Feedback");
//        String output = "" + "Hare Krishna Prabhu ," +
//                "\n\nFeedback for "+Item+" for Date : "+selectedDate+"\n"+
//                "Quality : "+QualityFeedback+"\n"+
//                "Quantity : "+QuantityFeedback+"\n"+
//                "Taste : "+OverallFeedback+"\n"+
//                "Comments : "+Comments+"\n\n With Regards,\n"+displayName;
//        emailIntent.putExtra(Intent.EXTRA_TEXT, output);
//
//        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//           //
//            Log.i("info","Finished sending email...");
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(this.getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
//    }
}
