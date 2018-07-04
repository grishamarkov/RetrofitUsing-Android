package com.n2me.androidtv.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v17.leanback.app.HeadersFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowHeaderPresenter;

import com.n2me.androidtv.R;
import com.n2me.androidtv.activities.MainActivity;
import com.n2me.androidtv.util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class CustomHeadersFragment extends HeadersFragment {

    private ArrayObjectAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customSetBackground(R.color.header_background);
        setOnHeaderViewSelectedListener(getDefaultItemSelectedListener());
        setHeaderAdapter();
        setCustomPadding();
    }

    private void setHeaderAdapter() {
        adapter = new ArrayObjectAdapter();

        LinkedHashMap<Integer, CustomRowsFragment> fragments = ((MainActivity) getActivity()).getFragments();

        // create header items`
        HeaderItem header0 = new HeaderItem(0, "Recently Watched");
        ArrayObjectAdapter innerAdapter = new ArrayObjectAdapter();
        innerAdapter.add(fragments.get(0));
        adapter.add(0, new ListRow(header0, innerAdapter));

        HeaderItem header1 = new HeaderItem(1, "Live TV");
        ArrayObjectAdapter innerAdapter1 = new ArrayObjectAdapter();
        innerAdapter1.add(fragments.get(1));
        adapter.add(1, new ListRow(header1, innerAdapter1));

        HeaderItem header2 = new HeaderItem(2, "Movies");
        ArrayObjectAdapter innerAdapter2 = new ArrayObjectAdapter();
        innerAdapter2.add(fragments.get(2));
        adapter.add(2, new ListRow(header2, innerAdapter2));

        HeaderItem header3 = new HeaderItem(3, "Events");
        ArrayObjectAdapter innerAdapter3 = new ArrayObjectAdapter();
        innerAdapter3.add(fragments.get(3));
        adapter.add(3, new ListRow(header3, innerAdapter3));

        HeaderItem header4 = new HeaderItem(4, "Games");
        ArrayObjectAdapter innerAdapter4 = new ArrayObjectAdapter();
        innerAdapter4.add(fragments.get(4));
        adapter.add(4, new ListRow(header4, innerAdapter4));

        HeaderItem header5 = new HeaderItem(5, "E-Books");
        ArrayObjectAdapter innerAdapter5 = new ArrayObjectAdapter();
        innerAdapter5.add(fragments.get(5));
        adapter.add(5, new ListRow(header5, innerAdapter5));

        HeaderItem header6 = new HeaderItem(6, "International");
        ArrayObjectAdapter innerAdapter6 = new ArrayObjectAdapter();
        innerAdapter6.add(fragments.get(6));
        adapter.add(6, new ListRow(header6, innerAdapter6));

//        HeaderItem header7 = new HeaderItem(7, "Music");
//        ArrayObjectAdapter innerAdapter7 = new ArrayObjectAdapter();
//        innerAdapter7.add(fragments.get(7));
//        adapter.add(7, new ListRow(header7, innerAdapter7));

        setAdapter(adapter);
    }

    private void setCustomPadding() {
        getView().setPadding(0, Utils.dpToPx(50, getActivity()), Utils.dpToPx(0, getActivity()), 0);
    }


    private OnHeaderViewSelectedListener getDefaultItemSelectedListener() {
        return new OnHeaderViewSelectedListener() {
            @Override
            public void onHeaderSelected(RowHeaderPresenter.ViewHolder viewHolder, Row row) {
                Object obj = ((ListRow) row).getAdapter().get(0);
                getFragmentManager().beginTransaction().replace(R.id.rows_container, (Fragment) obj).commit();
                ((MainActivity) getActivity()).updateCurrentRowsFragment((CustomRowsFragment) obj);
            }
        };
    }

    /**
     * Since the original setBackgroundColor is private, we need to
     * access it via reflection
     *
     * @param colorResource The colour resource
     */
    private void customSetBackground(int colorResource) {
        try {
            Class clazz = HeadersFragment.class;
            Method m = clazz.getDeclaredMethod("setBackgroundColor", Integer.TYPE);
            m.setAccessible(true);
            m.invoke(this, getResources().getColor(colorResource));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}