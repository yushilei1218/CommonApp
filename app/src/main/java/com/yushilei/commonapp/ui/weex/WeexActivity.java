package com.yushilei.commonapp.ui.weex;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yushilei.commonapp.R;

public class WeexActivity extends AppCompatActivity {
    private static final String WEEX_URL = "WEEX_URL";
    private String mWeexUrl;

    public static void invoke(Context context, String weexUrl) {
        Intent intent = new Intent(context, WeexActivity.class);
        intent.putExtra(WEEX_URL, weexUrl);
        context.startActivity(intent);
    }

    public static Intent getIntent(Context context, String weexUrl) {
        Intent intent = new Intent(context, WeexActivity.class);
        intent.putExtra(WEEX_URL, weexUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle target;
        if (savedInstanceState == null) {
            target = getIntent().getExtras();
        } else {
            target = savedInstanceState;
        }
        mWeexUrl = target.getString(WEEX_URL);

        setContentView(R.layout.activity_weex);

        WeexFragment fg = WeexFragment.newInstance(mWeexUrl);

        getSupportFragmentManager().beginTransaction().add(R.id.act_weex, fg).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(WEEX_URL, mWeexUrl);
        super.onSaveInstanceState(outState);
    }
}
