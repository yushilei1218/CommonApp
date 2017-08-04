package com.yushilei.commonapp.ui.contact;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

public class ContactActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    public void initView() {
        boolean b = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED;
        if (b) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    998);
            return;
        }
        getContacts();
    }

    private void getContacts() {
        ContentResolver cr = this.getContentResolver();
        Cursor cursor = cr.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null,
                ContactsContract.Contacts._ID + " DESC");
        while (cursor.moveToNext()) {
            Log.i(getTAG(), cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)));
            Log.i(getTAG(), cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

        }
        cursor.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 998) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts();
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
