package com.yushilei.commonapp.ui.sharedelement;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.yushilei.commonapp.R;
import com.yushilei.commonapp.common.adapter2.BaseRecyclerHolder;
import com.yushilei.commonapp.common.adapter2.HolderDelegate;
import com.yushilei.commonapp.common.adapter2.MultiListAdapter;
import com.yushilei.commonapp.common.bean.BeanA;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment {


    public GridFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        GridView grid = (GridView) view.findViewById(R.id.grid);
        MultiListAdapter adapter = new MultiListAdapter(1);

        adapter.setMatch(BeanA.class, new Item());
        ArrayList<BeanA> data = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            data.add(new BeanA(i + ""));
        }
        grid.setAdapter(adapter);
        adapter.replaceData(data);
    }

    public final class Item extends HolderDelegate<BeanA> {
        @Override
        public int getLayoutId() {
            return R.layout.item_grid_shared;
        }

        @Override
        public void onBindData(BaseRecyclerHolder holder, BeanA beanA, int pos) {
            ImageView img = (ImageView) holder.findView(R.id.img);
            img.setImageResource(R.mipmap.ic_clock);
            img.setOnClickListener(holder);
            ViewCompat.setTransitionName(img, pos + "_img");
        }

        @Override
        public void onItemClick(View target, BaseRecyclerHolder<BeanA> holder, BeanA beanA) {
            DetailFragment fg = new DetailFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fg.setSharedElementEnterTransition(new DetailsTransition());
                fg.setEnterTransition(new Fade());
                setExitTransition(new Fade());
                fg.setSharedElementReturnTransition(new DetailsTransition());
            }
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addSharedElement(target, "kittenImage")
                    .replace(R.id.container, fg)
                    .addToBackStack(null)
                    .commit();
//            DialogFragment fg = new DialogFragment();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                fg.setSharedElementEnterTransition(new DetailsTransition());
//                fg.setEnterTransition(new Fade());
//                setExitTransition(new Fade());
//                fg.setSharedElementReturnTransition(new DetailsTransition());
//            }
//            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//            transaction.addSharedElement(target, "kittenImage")
//                    .addToBackStack(null);
//            fg.show(transaction," aa");
        }
    }
}
