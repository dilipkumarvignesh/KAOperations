package iskconbangalore.org.kaoperations;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class Feedback extends AppCompatActivity {

    TextView selectedDate,Tbreakfast,Tlunch,Tdinner;
    private int year, month, day;
    Calendar calendar;
    private static final int CAMERA_REQUEST_CODE = 555;
    public StorageReference mStorage;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        selectedDate=findViewById(R.id.selectedDate);
        month = calendar.get(Calendar.MONTH);
        day =  calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
    }


    public void setMenu(View view) {
        Toast.makeText(this, "Feedback Saved", Toast.LENGTH_LONG).show();
        //View myLayout = findViewById( R.id.BFeedback );
        //  View button = myLayout.findViewById(R.id.)
        // CheckBox BQuantity =
        LinearLayout layout = (LinearLayout) findViewById(R.id.Main);
        // boolean success = formIsValid(layout);

        int z = 0;
        Log.d("info", "Children:" + layout.getChildCount());
        for (int i = 0; i < layout.getChildCount(); i++) {
            View v = layout.getChildAt(i);
            if (v instanceof EditText) {
                //validate your EditText here
            } else if (v instanceof CheckBox) {
                z++;
                Log.d("info", "CheckBoxes:" + z);

                //validate RadioButton
            } //etc. If it fails anywhere, just return false.
        }
    }

    public void enableCheckBox(View view) {
        RadioButton item = (RadioButton) view;
        Toast.makeText(this, item.getText(), Toast.LENGTH_LONG).show();

    }

    public void captureClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        {
            // super.onActivityResult(RequestCode,ResultCode,data);
            if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
                mProgress.setMessage("Uploading Image .....");
                mProgress.show();
                Toast.makeText(this, "Image Captured", Toast.LENGTH_LONG).show();
                Uri uri = data.getData();
                Log.d("info","uri:"+uri);
                StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
                //filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        mProgress.dismiss();
//                        Toast.makeText(Feedback.this,"Image Uploaded",Toast.LENGTH_LONG).show();
//                    }
//                });
            }
        }

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT)
//                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        selectedDate.setText(new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
     //   displayMenu();
    }
}
