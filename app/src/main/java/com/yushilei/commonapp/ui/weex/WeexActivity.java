package com.yushilei.commonapp.ui.weex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yushilei.commonapp.R;

public class WeexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weex);
        WeexFragment fg = WeexFragment.newInstance(null, null);
        getSupportFragmentManager().beginTransaction().add(R.id.act_weex, fg).commit();
    }
}
