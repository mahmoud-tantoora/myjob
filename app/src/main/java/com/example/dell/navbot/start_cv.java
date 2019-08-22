package com.example.dell.navbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class start_cv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_cv);
    }

    public void createcv(View view) {
        Intent intent=new Intent(this,cv_woker.class);
        startActivity(intent);
    }
}