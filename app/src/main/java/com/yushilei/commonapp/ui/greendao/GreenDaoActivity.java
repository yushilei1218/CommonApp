package com.yushilei.commonapp.ui.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;
import com.yushilei.commonapp.common.base.BaseApp;
import com.yushilei.commonapp.common.bean.db.DaoSession;
import com.yushilei.commonapp.common.bean.db.Note;
import com.yushilei.commonapp.common.bean.db.NoteType;
import com.yushilei.commonapp.common.bean.db.Person;
import com.yushilei.commonapp.common.util.JsonUtil;
import com.yushilei.commonapp.common.util.SetUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yushilei
 */
public class GreenDaoActivity extends BaseActivity {

    private TextView mMtv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_green_dao;
    }

    @Override
    public void initView() {
        //1.0.1 bugfix
        //1.1.0更新
        //1.2.0更新
        setOnClick(R.id.act_green_dao_query);
        setOnClick(R.id.act_green_dao_save);
        setOnClick(R.id.act_green_dao_clear);
        setOnClick(R.id.act_green_dao_save_and_update);

        setOnClick(R.id.act_green_dao_save_person);
        setOnClick(R.id.act_green_dao_query_person);
        setOnClick(R.id.act_green_dao_clear_person);

        mMtv = (TextView) findView(R.id.act_green_dao_tv);
    }

    @Override
    public void onClick(View v) {
        DaoSession daoSession = BaseApp.AppContext.getDaoSession();
        switch (v.getId()) {
            case R.id.act_green_dao_query:
                showNoteContent(daoSession);
                break;
            case R.id.act_green_dao_save:
                testInsertNote(daoSession);
                break;
            case R.id.act_green_dao_clear:
                daoSession.getNoteDao().deleteAll();
                break;
            case R.id.act_green_dao_save_and_update:
                testInsertOrUpdateNote(daoSession);
                break;
            case R.id.act_green_dao_save_person:
                List<Person> data = getPersons();
                daoSession.getPersonDao().insertInTx(data);
                break;
            case R.id.act_green_dao_query_person:
                List<Person> persons = daoSession.getPersonDao().loadAll();
                if (!SetUtil.isEmpty(persons)) {
                    String text = JsonUtil.toJson(persons);
                    Log.d(getTAG(), text);
                    mMtv.setText(text);
                }
                break;
            case R.id.act_green_dao_clear_person:
                daoSession.getPersonDao().deleteAll();
                break;

            default:
                break;
        }
    }

    private List<Person> getPersons() {
        ArrayList<Person> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ArrayList<String> tags = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                tags.add("j- " + j + " i- " + i);
            }
            data.add(new Person("item+" + i, i, new Date(), tags, i));
        }
        return data;
    }

    private void testInsertOrUpdateNote(DaoSession daoSession) {
        List<Note> notes2 = new ArrayList<>(10);
        for (int i = 5; i < 15; i++) {
            Note e = new Note((long) i, "text+" + i, "comment+test" + i, new Date(System.currentTimeMillis()), NoteType.TEXT);
            notes2.add(e);
        }
        daoSession.getNoteDao().insertOrReplaceInTx(notes2);
    }

    private void testInsertNote(DaoSession daoSession) {
        List<Note> notes = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Note e = new Note("text+" + i, "comment+" + i, new Date(System.currentTimeMillis()), NoteType.LIST);
            notes.add(e);
        }
        daoSession.getNoteDao().insertInTx(notes);
    }

    private void showNoteContent(DaoSession daoSession) {
        List<Note> notes1 = daoSession.getNoteDao().loadAll();
        if (SetUtil.isEmpty(notes1)) {
            return;
        }
        String s = JsonUtil.toJson(notes1);
        mMtv.setText(s);
    }
}
