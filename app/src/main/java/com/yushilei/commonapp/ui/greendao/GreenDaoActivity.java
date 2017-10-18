package com.yushilei.commonapp.ui.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.base.BaseApp;
import com.yushilei.commonapp.common.bean.db.DaoSession;
import com.yushilei.commonapp.common.bean.db.Note;
import com.yushilei.commonapp.common.bean.db.NoteType;
import com.yushilei.commonapp.common.util.SetUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yushilei
 */
public class GreenDaoActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_green_dao;
    }

    @Override
    public void initView() {
        setOnClick(R.id.act_green_dao_query);
        setOnClick(R.id.act_green_dao_save);
    }

    @Override
    public void onClick(View v) {
        DaoSession daoSession = BaseApp.AppContext.getDaoSession();
        switch (v.getId()) {
            case R.id.act_green_dao_query:
                List<Note> notes1 = daoSession.getNoteDao().loadAll();
                if (SetUtil.isEmpty(notes1)) {
                    return;
                }
                for (int i = 0; i < notes1.size(); i++) {
                    Log.d(getTAG(),notes1.get(i).toString());
                }
                break;
            case R.id.act_green_dao_save:

                List<Note> notes = new ArrayList<>(10);
                for (int i = 0; i < 10; i++) {
                    Note e = new Note("text+" + i, "comment+" + i, new Date(System.currentTimeMillis()), NoteType.LIST);
                    notes.add(e);
                }
                daoSession.getNoteDao().insertInTx(notes);
                break;
            default:
                break;
        }
    }
}