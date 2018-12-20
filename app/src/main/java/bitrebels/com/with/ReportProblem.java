package bitrebels.com.with;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportProblem extends AppCompatActivity {


    private EditText description;
    RadioButton personalIssueRB;
    RadioButton publicIssueRB;
    RadioGroup issueGroup;

    private FirebaseFirestore db;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);


        personalIssueRB = findViewById(R.id.personalIssue);
        publicIssueRB = findViewById(R.id.publicIssue);
        description = (EditText) findViewById(R.id.inputText);
        issueGroup = findViewById(R.id.issueGroup);

        db = FirebaseFirestore.getInstance();

        sharedpreferences = getSharedPreferences("COLLECTIONNUM", Context.MODE_PRIVATE);


    }

    public void submitRequest(View view) {
        String text = description.getText().toString();

        if (text.equals("")) {

            Snackbar.make(view, "Please enter a valid problem description", Snackbar.LENGTH_SHORT).show();

            //Toast.makeText(this, "Please enter a valid problem description", Toast.LENGTH_SHORT).show();
        } else {

            int rbID = issueGroup.getCheckedRadioButtonId();
            String issueType;

            if (rbID == R.id.personalIssue) {
                issueType = "personal";
            } else if (rbID == R.id.publicIssue) {
                issueType = "public";
            } else {
                issueType = "unknown";
            }

            int collectionIndex = sharedpreferences.getInt("COLLECTIONNUM", 0);

            int hour = Calendar.getInstance().get(Calendar.HOUR);
            int min = Calendar.getInstance().get(Calendar.MINUTE);
            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            int year = Calendar.getInstance().get(Calendar.YEAR);

            String currentTime = hour + ":" + min + " - " + month + "/" + day + "/" + year;
            long ms = Calendar.getInstance().getTimeInMillis();

            Map<String, Object> newProblem = new HashMap<>();
            newProblem.put("DESCRIPTION", text);
            newProblem.put("TIME", currentTime);
            newProblem.put("ISSUE", issueType);


            db.collection("problems").document(sharedpreferences.getString("ZIPCODE", "43210")).collection(Integer.toString(collectionIndex)).document(sharedpreferences.getString("ZIPCODE", "43210")).set(newProblem).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "Successfully sent problem to database!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ReportProblem.this, "ERROR" + e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d("TAG", e.toString());
                        }
                    });
            description.setText("");

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("COLLECTIONNUM", (collectionIndex + 1));
            editor.commit();
        }
    }
}
