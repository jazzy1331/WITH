package bitrebels.com.with;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Alerts extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseFirestore db;
    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedpreferences = getSharedPreferences("COLLECTIONNUM", Context.MODE_PRIVATE);

        Boolean isFirstRun = sharedpreferences.getBoolean("isFirstRun", false);

        if (!isFirstRun) {
            //show sign up activity
            startActivity(new Intent(Alerts.this, ZipCodeActivity.class));
        }


        db = FirebaseFirestore.getInstance();

        db.collection("alerts").document(sharedpreferences.getString("ZIPCODE", "43210")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();

                List<String> alert;
                LinearLayout m_11 = (LinearLayout) findViewById(R.id.linearLayout2);

                int index = 1;
                String str = "alert" + index;
                while (!(((List<String>) document.get(str)) == (null))) {
                    str = "alert" + index;
                    alert = (List<String>) document.get(str);
                    if (((List<String>) document.get(str)) == (null)) {
                        break;
                    }
                    for (int i = 0; i < alert.size(); i++) {
                        TextView textView = new TextView(Alerts.this);
                        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        textView.setText(alert.get(i));
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        if (i == 1) {
                            textView.setTextColor(Color.parseColor("#FF0000"));
                        } else if (i == 0) {
                            textView.setTextColor(Color.parseColor("#0000FF"));
                        } else {
                            textView.setTextColor(Color.parseColor("#000000"));
                        }
                        if (i == 0 || i == 3) {
                            textView.setTypeface(Typeface.DEFAULT_BOLD);
                        }
                        m_11.addView(textView);
                    }
                    TextView textView = new TextView(Alerts.this);
                    textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setText("");
                    textView.setHeight(100);
                    m_11.addView(textView);

                    index++;
                }
            }
        });

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("isFirstRun", false);
        editor.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent myIntent;

        if (id == R.id.reportIssue) {
            myIntent = new Intent(Alerts.this, ReportProblem.class);
            this.startActivity(myIntent);
        } else if (id == R.id.issuesFeed) {
            myIntent = new Intent(Alerts.this, IssuesAroundMe.class);
            this.startActivity(myIntent);

        } else if (id == R.id.shelterFinder) {
            myIntent = new Intent(Alerts.this, ShelterFinder.class);
            this.startActivity(myIntent);

        } else if (id == R.id.tornado) {
            myIntent = new Intent(Alerts.this, Tornado.class);
            this.startActivity(myIntent);

        } else if (id == R.id.blizzard) {
            myIntent = new Intent(Alerts.this, Blizzard.class);
            this.startActivity(myIntent);

        } else if (id == R.id.earthquake) {
            myIntent = new Intent(Alerts.this, Earthquake.class);
            this.startActivity(myIntent);

        } else if (id == R.id.flood) {
            myIntent = new Intent(Alerts.this, Flood.class);
            this.startActivity(myIntent);

        } else if (id == R.id.hurricane) {
            myIntent = new Intent(Alerts.this, Hurricane.class);
            this.startActivity(myIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
