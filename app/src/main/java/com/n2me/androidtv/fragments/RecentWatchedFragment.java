package com.n2me.androidtv.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;

import com.n2me.androidtv.activities.MovieDetailsActivity;
import com.n2me.androidtv.common.pref.N2MEPreference;
import com.n2me.androidtv.common.rest.model.Media;
import com.n2me.androidtv.common.rest.model.Medium;
import com.n2me.androidtv.presenter.CardPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class RecentWatchedFragment extends CustomRowsFragment {

    private final int NUM_ROWS = 1;
    private final int NUM_COLS = 15;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadRows();
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                Medium movie = (Medium) item;
                Media media = new Media();
                media.setMedia(N2MEPreference.getRecentWatchedList());

                String json = new Gson().toJson(movie);
                String jsonMedia = new Gson().toJson(media);

                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra(MovieDetailsActivity.MOVIE, json);
                intent.putExtra(MovieDetailsActivity.MOVIELIST, jsonMedia);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        MovieDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            }
        });
    }

    public void loadRows() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        cardPresenter = new CardPresenter();

        ArrayList<Medium> list = N2MEPreference.getRecentWatchedList();

        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (int j = 0; j < list.size(); j++) {
            listRowAdapter.add(list.get(j));
        }

        if (list.size() != 0) {
            HeaderItem header = new HeaderItem(0, "Recent Watched");
            rowsAdapter.add(new ListRow(header, listRowAdapter));
        }

        setAdapter(rowsAdapter);
    }
}