package com.yushilei.commonapp.ui.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yushilei.commonapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewModelFragment extends BottomSheetDialogFragment {


    public ViewModelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_model, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView lv = (ListView) view.findViewById(R.id.fg_view_model_lv);
        final ArrayList<String> data = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
        lv.setAdapter(adapter);
        LiveData<List<String>> liveData = ViewModelProviders.of(this).get(MyViewModel.class).getLiveData();
        liveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (strings != null) {
                    data.addAll(strings);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
