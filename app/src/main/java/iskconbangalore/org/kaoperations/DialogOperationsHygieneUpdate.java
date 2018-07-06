package iskconbangalore.org.kaoperations;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DialogOperationsHygieneUpdate extends DialogFragment {

    String Item,selectedDate;
    private static String displayName;
    private int UserPoints;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = this.getActivity().getLayoutInflater().inflate(R.layout.dialog_operations_update_2, null);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle mArgs = getArguments();
        Item = mArgs.getString("Item");
        selectedDate = mArgs.getString("SelectedDate");
        UtilityFunctions.getUserPoints(this.getActivity(),new firebaseCallBack() {

            @Override
            public void onCallback(Users user) {
                Log.d("info","InterfacePointsValueHygiene:"+user.getPoints());
                UserPoints = user.getPoints();
                //  displayName = user.getName();
            }


        });
        // Dialog th = this;
        // Set the dialog title
        builder
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setView(inflater.inflate(R.layout.dialog_operations_update_2, null))
                // Set the action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                        String Hygiene="";

                        RadioGroup RHygiene = ((AlertDialog) dialog).findViewById(R.id.RadioHygiene);
                        int iHygieneSelected = RHygiene.getCheckedRadioButtonId();
                    //    Log.d("info", "QuantitySelected:" + iQuantitySelected);
                        if (iHygieneSelected != -1) {
                            RadioButton radioQuantity = ((AlertDialog) dialog).findViewById(iHygieneSelected);
                            Hygiene = radioQuantity.getText().toString();
                        }



                        EditText comments = ((AlertDialog)dialog).findViewById(R.id.textComments);
                        String commentText = comments.getText().toString();

                        UpdateFeedback(Hygiene,commentText);



                    }


                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                     //   mListener.onDialogNegativeClick(DialogPrasadamUpdate.this);
                    }
                });

        return builder.create();
    }

    public void UpdateFeedback(String Hygiene,String comments) {
        String date = UtilityFunctions.getDate();
        final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = root.child("users").child("dilip kumarvignesh").child(date);
        final Context con = this.getActivity();
        SharedPreferences userInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        displayName =  userInfo.getString("DisplayName","NA").toString();

        final String feedBackHygiene = Hygiene;
        final String Comments = comments;

        sendEmail(Hygiene,Comments);
        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("info", "dataSnapValue:" + dataSnapshot.getValue());
                //    Log.d("info","Datasnapshot:"+dataSnapshot.getChildren());
                if (!dataSnapshot.exists()) {


                    String Date = UtilityFunctions.getDate();

                    Map<String,String> feedback = new HashMap<>();
                    feedback.put("Hygiene",feedBackHygiene);

                    feedback.put("Comments",Comments);


                    String key = root.child("Feedback").child(Date).push().getKey();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/Feedback/" + selectedDate + "/" + key+"/Hygiene", feedback);
                    Log.d("info","HygienePointsValue:"+UserPoints+1);
                    childUpdates.put("/users/"+displayName+"/Points",UserPoints+1);
                    childUpdates.put("/users/" + displayName + "/Feedback/" + selectedDate +"/Hygiene", feedback);

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
    protected void sendEmail(String Hygiene,String Comments) {
        Log.i("Send email", "");
        String[] TO = {"krmt.office@hkm-group.org"};
        String[] CC = {"admin.folk@hkm-group.org"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);


        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KrishnaAmrita Prasadam Feedback");
        String output = "" + "Hare Krishna Prabhu ," +
                "\n\n\nFeedback for Hygiene for Date : "+selectedDate+"\n"+
                "Hygiene : "+Hygiene+"\n"+
                "Comments : "+Comments+"\n\n With Regards,\n"+displayName;;

        emailIntent.putExtra(Intent.EXTRA_TEXT, output);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //
            Log.i("info","Finished sending email...");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this.getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
