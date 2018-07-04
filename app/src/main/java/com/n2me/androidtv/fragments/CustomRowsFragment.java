package com.n2me.androidtv.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.n2me.androidtv.R;
import com.n2me.androidtv.activities.MovieDetailsActivity;
import com.n2me.androidtv.common.rest.model.Categories;
import com.n2me.androidtv.common.rest.model.Category;
import com.n2me.androidtv.common.rest.model.Media;
import com.n2me.androidtv.common.rest.model.Medium;
import com.n2me.androidtv.common.rest.model.ParentCategory;
import com.n2me.androidtv.presenter.CardPresenter;
import com.n2me.androidtv.util.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CustomRowsFragment extends RowsFragment {

    protected ArrayObjectAdapter rowsAdapter;
    protected CardPresenter cardPresenter;

    protected SpinnerFragment spinnerFragment;

    private static final int HEADERS_FRAGMENT_SCALE_SIZE = 300;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        int marginOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEADERS_FRAGMENT_SCALE_SIZE, getResources().getDisplayMetrics());
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        params.rightMargin -= marginOffset;
        v.setLayoutParams(params);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setCustomPadding();
    }

    public void setSubCategories(Categories allCategories) {

    }

    public void setMediaList(int categoryId, Media media) {

    }

    public void loadRows() {

    }

    private void setCustomPadding() {
        getView().setPadding(Utils.dpToPx(-24, getActivity()), Utils.dpToPx(50, getActivity()), Utils.dpToPx(48, getActivity()), 0);
    }

    public void refresh() {
        getView().setPadding(Utils.dpToPx(-24, getActivity()), Utils.dpToPx(50, getActivity()), Utils.dpToPx(300, getActivity()), 0);
    }

//    private void showLoadingBar() {
//        spinnerFragment = new SpinnerFragment();
//        getFragmentManager().beginTransaction().add(R.id.rows_container, spinnerFragment).commit();
//    }
//
//    private void hideLoadingBar() {
//        if (spinnerFragment != null)
//            getFragmentManager().beginTransaction().remove(spinnerFragment).commit();
//    }
}