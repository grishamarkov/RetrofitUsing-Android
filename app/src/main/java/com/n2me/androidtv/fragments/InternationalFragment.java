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
import com.n2me.androidtv.common.rest.N2meWebService;
import com.n2me.androidtv.common.rest.model.Categories;
import com.n2me.androidtv.common.rest.model.Category;
import com.n2me.androidtv.common.rest.model.Media;
import com.n2me.androidtv.common.rest.model.Medium;
import com.n2me.androidtv.common.rest.model.ParentCategory;
import com.n2me.androidtv.presenter.CardPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InternationalFragment extends CustomRowsFragment {

    private static final String TAG = InternationalFragment.class.getSimpleName();
    private static final String TITLE = "International";

    private ArrayList<Category> subCategories = null;
    private HashMap<Integer, Media> mediaList = null;

    private int receivedMediaListCount = 0;
    private boolean bLoaded = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                Medium movie = (Medium) item;
                Media media = mediaList.get(new Integer(subCategories.get((int)row.getId()).getId()));

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

    @Override
    public void setSubCategories(Categories allCategories) {
        List<Category> categories = allCategories.getCategories();
        if (categories == null)
            return;

        if (subCategories != null) {
            subCategories.removeAll(subCategories);
        }
        else {
            subCategories = new ArrayList<>();
        }

        if (mediaList != null) {
            mediaList.remove(mediaList);
        }
        else {
            mediaList = new HashMap<Integer, Media>();
            receivedMediaListCount = 0;
        }

        Category me = null;

        // extract sub categories
        subCategories = new ArrayList<>();
        for (int i=0; i<categories.size(); i++) {
            Category category = categories.get(i);
            ParentCategory parent = category.getParentCategory();
            if (parent == null) {
                if (category.getTitle().equalsIgnoreCase(InternationalFragment.TITLE))
                    me = category;
                continue;
            }

            if (parent.getTitle().equalsIgnoreCase(InternationalFragment.TITLE)) {
                subCategories.add(category);
            }
        }

        // if not having sub categories, ....
        if ((me != null) && (subCategories.size() == 0))
            subCategories.add(me);

        // call to get media list
        for (int i=0; i<subCategories.size(); i++) {
            Category category = subCategories.get(i);
            N2meWebService.getInstance().mediaByCategoryId(category.getId(), "", "");
        }
    }

    @Override
    public void setMediaList(int categoryId, Media media) {
        if ( (subCategories == null) || (subCategories.size() == 0))
            return;

        for (int i=0; i < subCategories.size(); i++) {
            Category category = subCategories.get(i);
            if (category.getId() == categoryId) {

                if (mediaList.get(new Integer(categoryId)) != null)
                    continue;

                if (media != null)
                    mediaList.put(new Integer(categoryId), media);
                receivedMediaListCount ++;
                break;
            }
        }

        if (receivedMediaListCount == subCategories.size()) {
            loadRows();
        }
    }

    @Override
    public void loadRows() {
        super.loadRows();

        if ((subCategories == null) || (mediaList == null))
            return;

        if (bLoaded == true)
            return;
        bLoaded = true;

        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        cardPresenter = new CardPresenter();

        for (int i = 0; i < subCategories.size(); i++) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            Media media = mediaList.get(new Integer(subCategories.get(i).getId()));
            List<Medium> mediaList = media.getMedia();
            if (mediaList != null) {
                for (int j = 0; j < mediaList.size(); j++) {
                    listRowAdapter.add(mediaList.get(j));
                }

                if (mediaList.size() != 0) {
                    HeaderItem header = new HeaderItem(i, subCategories.get(i).getTitle());
                    rowsAdapter.add(new ListRow(header, listRowAdapter));
                }
            }
            setAdapter(rowsAdapter);
        }
    }
}