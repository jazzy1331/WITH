package bitrebels.com.with;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.support.v7.widget.CardView;
import android.widget.TextView;


import java.text.BreakIterator;
import java.util.List;
import java.util.Map;

public class IssuesAroundMe extends AppCompatActivity {

    private FirebaseFirestore db;

    SharedPreferences sharedpreferences;
    private LinearLayout mLinearLayout;
    private Context mContext;

    private String description;
    private String issue;
    private String time;
    private Map<String, Object> dataMap;
    private String TAG = "REPORT FEED ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_around_me);

        db = FirebaseFirestore.getInstance();

        sharedpreferences = getSharedPreferences("COLLECTIONNUM", Context.MODE_PRIVATE);

        mLinearLayout = (LinearLayout) findViewById(R.id.l1);
        mContext = getApplicationContext();

        addShelters();

    }

    public void addShelters() {

        int maxIndex = sharedpreferences.getInt("COLLECTIONINDEX", 3);

        if (maxIndex != 0) {

            for (int i = maxIndex; i >= 0; i--) {

                DocumentReference currentDocRef = db.collection("problems").document(sharedpreferences.getString("ZIPCODE", "43210")).collection(Integer.toString(i)).document(sharedpreferences.getString("ZIPCODE", "43210"));

                description = "ORIGINAL VALUE";
                time = "ORIGINAL VALUE";
                issue = "ORIGINAL VALUE";


                currentDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        Log.d(TAG, "GOT INTO THE METHOD!!!!!!!");

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());

//                                description = document.get("DESCRIPTION").toString();
//                                time = document.get("TIME").toString();
//                                issue = document.get("ISSUE").toString();

                                dataMap = document.getData();

                                // Initialize a new CardView
                                CardView card = new CardView(mContext);

                                // Set the CardView layoutParams
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                card.setLayoutParams(params);

                                params.setMargins(15, 15, 15, 15);


                                // Set CardView corner radius
                                card.setRadius(10);


                                description = dataMap.get("DESCRIPTION").toString();
                                time = dataMap.get("TIME").toString();
                                issue = dataMap.get("ISSUE").toString();


                                // Set cardView content padding
                                card.setContentPadding(150, 150, 150, 150);

                                // Set a background color for CardView
                                card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

//                // Set the CardView maximum elevation
//                card.setMaxCardElevation(15);
//
//                // Set CardView elevation
//                card.setCardElevation(9);

                                LinearLayout a = new LinearLayout(mContext);
                                a.setOrientation(LinearLayout.VERTICAL);

                                // Initialize a new TextView to put in CardView
                                TextView issueTextView = new TextView(mContext);
                                issueTextView.setLayoutParams(params);
                                issueTextView.setText(issue);
                                issueTextView.setTypeface(null, Typeface.BOLD);
                                issueTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

                                // Put the TextView in CardView
                                a.addView(issueTextView);

                                // Initialize a new TextView to put in CardView
                                TextView descriptionTextView = new TextView(mContext);
                                descriptionTextView.setLayoutParams(params);
                                descriptionTextView.setText(description);
                                descriptionTextView.setTextColor(Color.parseColor("#000000"));
                                descriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

                                // Put the TextView in CardView
                                a.addView(descriptionTextView);

                                // Initialize a new TextView to put in CardView
                                TextView timeTextView = new TextView(mContext);
                                timeTextView.setLayoutParams(params);
                                timeTextView.setText(time);
                                timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);


                                // Put the TextView in CardView
                                a.addView(timeTextView);

                                card.addView(a);

                                // Finally, add the CardView in root layout
                                mLinearLayout.addView(card);


                            } else {
                                Log.d(TAG, "No such document");

                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


            }
        }
    }
}