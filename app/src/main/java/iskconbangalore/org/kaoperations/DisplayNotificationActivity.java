package iskconbangalore.org.kaoperations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notification);


        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String body  = intent.getStringExtra("body");

        TextView TVnotificationTitle = findViewById(R.id.notificationTitle);
        TextView TVnotificationBody = findViewById(R.id.notificationBody);

        TVnotificationTitle.setText(title);
        TVnotificationBody.setText(body);

    }
}
