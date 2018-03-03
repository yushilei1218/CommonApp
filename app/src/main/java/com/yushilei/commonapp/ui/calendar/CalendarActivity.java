package com.yushilei.commonapp.ui.calendar;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.base.BaseActivity;

import java.util.Calendar;

import butterknife.BindView;

public class CalendarActivity extends BaseActivity {


    @BindView(R.id.act_mcv_calendar)
    MaterialCalendarView mcv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    public void initView() {
        mcv.state().edit()
                .setFirstDayOfWeek(Calendar.WEDNESDAY)
                .setMinimumDate(CalendarDay.from(2018, 3, 3))
                .setMaximumDate(CalendarDay.from(2019, 3, 3))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
    }
}
