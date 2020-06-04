package com.example.traceit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class start_activity extends AppCompatActivity {

    private Button play;

    private DatabaseHelper dbhelper;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);


        play = (Button) findViewById(R.id.play);

        dbhelper = new DatabaseHelper(this);

        preferences = getSharedPreferences("preference", MODE_PRIVATE);
        Boolean firstTime = preferences.getBoolean("firstTime", true);

        if(firstTime){
            chanagePref();
            addData(1);
        }else{

        }



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(start_activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void chanagePref() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstTime", false);
        editor.apply();
    }

    public void addData(int l){
        boolean insertData = dbhelper.addData(l);

        if(insertData) toastMe("Welcome to TraceIT!!");
        else toastMe("Something went wrong");
    }

    public void toastMe(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}
