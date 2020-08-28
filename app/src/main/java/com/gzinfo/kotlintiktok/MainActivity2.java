package com.gzinfo.kotlintiktok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.gzinfo.kotlintiktok.annontaion.BindView;
import com.gzinfo.kotlintiktok.annontaion.ClickView;
import com.gzinfo.kotlintiktok.util.MyTools;

public class MainActivity2 extends AppCompatActivity {
    @BindView(R.id.text1)
    TextView textView1;
    @BindView(R.id.text2)
    TextView textView2;

    @BindView(R.id.button1)
    MaterialButton button1;
    @BindView(R.id.button2)
    MaterialButton button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MyTools.init(this);
        textView1.setText("1");
        textView2.setText("2");
        button1.setText("1");
        button2.setText("1");
    }

    @ClickView({R.id.button1,R.id.button2})
    public void onClick(View view){
        Snackbar.make(view,view.getId()+" ", BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}