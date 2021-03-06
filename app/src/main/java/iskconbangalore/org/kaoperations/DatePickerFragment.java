package iskconbangalore.org.kaoperations;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;
public class DatePickerFragment extends DialogFragment
         {
            DatePickerDialog.OnDateSetListener ondateSet;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }


    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
       this.ondateSet = ondate;
    }
//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int day) {
//        Calendar c = Calendar.getInstance();
//        c.set(year, month, day);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String formattedDate = sdf.format(c.getTime());
//    }
}