package bitrebels.com.with;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ZipCodeActivity extends AppCompatActivity {

    private EditText ZipEntry;
    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_code);

        ZipEntry = findViewById(R.id.zipcodeEntry);
        sharedpreferences = getSharedPreferences("COLLECTIONNUM", Context.MODE_PRIVATE);


    }

    public void submitZipCode(View view) {

        String text = ZipEntry.getText().toString();


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("ZIPCODE", text);
        editor.putBoolean("isFirstRun", true);
        editor.commit();

        Intent myIntent = new Intent(ZipCodeActivity.this, Alerts.class);
        this.startActivity(myIntent);


    }
}
