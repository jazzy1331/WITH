package bitrebels.com.with;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ShelterFinder extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_finder);

        db = FirebaseFirestore.getInstance();

        db.collection("shelters").document("houses").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();

                List<String> house;
                LinearLayout m_11 = (LinearLayout) findViewById(R.id.linearLayout3);

                int index = 1;
                String str = "house" + index;
                while (!(((List<String>) document.get(str)) == (null))) {
                    str = "house" + index;
                    house = (List<String>) document.get(str);
                    if (((List<String>) document.get(str)) == (null)) {
                        break;
                    }
                    for (int i = 0; i < house.size(); i++) {
                        TextView textView = new TextView(ShelterFinder.this);
                        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        textView.setText(house.get(i));
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        textView.setTextColor(Color.parseColor("#000000"));
                        if (i == 0) {
                            textView.setTypeface(Typeface.DEFAULT_BOLD);
                        }
                        m_11.addView(textView);
                    }
                    TextView textView = new TextView(ShelterFinder.this);
                    textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setText("");
                    textView.setHeight(100);
                    m_11.addView(textView);

                    index++;
                }
            }
        });
    }
}
