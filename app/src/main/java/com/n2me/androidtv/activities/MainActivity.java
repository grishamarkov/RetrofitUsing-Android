package com.n2me.androidtv.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v17.leanback.widget.VerticalGridView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.n2me.androidtv.R;
import com.n2me.androidtv.common.bus.BusProvider;
import com.n2me.androidtv.common.event.CategoriesEvent;
import com.n2me.androidtv.common.event.MediaEvent;
import com.n2me.androidtv.common.event.ServiceErrorEvent;
import com.n2me.androidtv.common.event.UsersEvent;
import com.n2me.androidtv.common.pref.N2MEPreference;
import com.n2me.androidtv.common.rest.N2meWebService;
import com.n2me.androidtv.common.rest.model.Categories;
import com.n2me.androidtv.common.rest.model.Category;
import com.n2me.androidtv.common.rest.model.Media;
import com.n2me.androidtv.common.rest.model.Medium;
import com.n2me.androidtv.common.rest.model.Users;
import com.n2me.androidtv.custom.CustomFrameLayout;
import com.n2me.androidtv.fragments.CustomHeadersFragment;
import com.n2me.androidtv.fragments.CustomRowsFragment;
import com.n2me.androidtv.fragments.EBooksFragment;
import com.n2me.androidtv.fragments.EventsFragment;
import com.n2me.androidtv.fragments.GamesFragment;
import com.n2me.androidtv.fragments.InternationalFragment;
import com.n2me.androidtv.fragments.LiveTVFragment;
import com.n2me.androidtv.fragments.MoviesFragment;
import com.n2me.androidtv.fragments.MusicFragment;
import com.n2me.androidtv.fragments.RecentWatchedFragment;
import com.n2me.androidtv.util.Utils;
import com.squareup.otto.Subscribe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();

    private CustomHeadersFragment headersFragment;
    private CustomRowsFragment rowsFragment;

    private final int CATEGORIES_NUMBER = 8;
    private LinkedHashMap<Integer, CustomRowsFragment> fragments;
    private CustomFrameLayout customFrameLayout;

    private boolean navigationDrawerOpen;
    private static final float NAVIGATION_DRAWER_SCALE_FACTOR = 0.9f;

    private Categories mCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BusProvider.get().register(this);

        createPredefinedFragments();
        loadCategories();
        loadPreference();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BusProvider.get().unregister(this);
    }

    public void loadPreference() {
        N2MEPreference.loadConfig();
    }

    public void loadCategories() {
        showLoading();
        N2meWebService.getInstance().categories();
    }

    public void showLoading() {
        ProgressBar spinner;
        spinner = (ProgressBar)findViewById(R.id.loadingBar);
        spinner.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        ProgressBar spinner;
        spinner = (ProgressBar)findViewById(R.id.loadingBar);
        spinner.setVisibility(View.GONE);
    }

    public void createPredefinedFragments() {
        fragments = new LinkedHashMap<Integer, CustomRowsFragment>();

        // add fragments
        CustomRowsFragment fragment = new CustomRowsFragment();
        fragments.put(0, new RecentWatchedFragment());
        fragments.put(1, new LiveTVFragment());
        fragments.put(2, new MoviesFragment());
        fragments.put(3, new EventsFragment());
        fragments.put(4, new GamesFragment());
        fragments.put(5, new EBooksFragment());
        fragments.put(6, new InternationalFragment());
//        fragments.put(7, new MusicFragment());

        customFrameLayout = (CustomFrameLayout) this.findViewById(R.id.customerFrameLayout);
        setupCustomFrameLayout();

        headersFragment = new CustomHeadersFragment();
        rowsFragment = fragments.get(0);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.header_container, headersFragment, "CustomHeadersFragment")
                .replace(R.id.rows_container, rowsFragment, "CustomRowsFragment");
        transaction.commit();
    }

    private void setupCustomFrameLayout() {
        customFrameLayout.setOnChildFocusListener(new CustomFrameLayout.OnChildFocusListener() {
            @Override
            public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
                if ((headersFragment.getView() != null) && (headersFragment.getView().requestFocus(direction, previouslyFocusedRect)))
                    return true;
                if ((rowsFragment.getView() != null) && (rowsFragment.getView().requestFocus(direction, previouslyFocusedRect)))
                    return true;

                return false;
            }

            @Override
            public void onRequestChildFocus(View child, View focused) {
                int childId = child.getId();
                if (childId == R.id.rows_container)
                    toggleHeadersFragment(false);
                else if (childId == R.id.header_container)
                    toggleHeadersFragment(true);
            }
        });

        customFrameLayout.setOnFocusSearchListener(new CustomFrameLayout.OnFocusSearchListener() {
            @Override
            public View onFocusSearch(View focused, int direction) {
                if (direction == View.FOCUS_LEFT) {
                    if (isVerticalScrolling() || navigationDrawerOpen) {
                        return focused;
                    }
                    return getVerticalGridView(headersFragment);
                } else if (direction == View.FOCUS_RIGHT) {
                    if (isVerticalScrolling() || !navigationDrawerOpen) {
                        return focused;
                    }
                    return getVerticalGridView(rowsFragment);
                } else {
                    return null;
                }
            }
        });
    }

    // Toggle Header Fragment
    public synchronized void toggleHeadersFragment(final boolean doOpen) {
        boolean condition = (doOpen ? !isNavigationDrawerOpen() : isNavigationDrawerOpen());
        if (condition) {
            try {
                final View headersContainer = (View) headersFragment.getView().getParent();
                final View rowsContainer = (View) rowsFragment.getView().getParent();

                final float delta = headersContainer.getWidth() * NAVIGATION_DRAWER_SCALE_FACTOR;

                // get current margin (a previous animation might have been interrupted)
                final int currentHeadersMargin = (((ViewGroup.MarginLayoutParams) headersContainer.getLayoutParams()).leftMargin);
                final int currentRowsMargin = (((ViewGroup.MarginLayoutParams) rowsContainer.getLayoutParams()).leftMargin);

                // calculate destination
                final int headersDestination = (doOpen ? 0 : (int) (0 - delta));
                final int rowsDestination = (doOpen ? (Utils.dpToPx(300, this)) : (int) (Utils.dpToPx(300, this) - delta));

                // calculate the delta (destination - current)
                final int headersDelta = headersDestination - currentHeadersMargin;
                final int rowsDelta = rowsDestination - currentRowsMargin;

                Animation animation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
//                        ViewGroup.MarginLayoutParams headersParams = (ViewGroup.MarginLayoutParams) headersContainer.getLayoutParams();
//                        headersParams.leftMargin = (int) (currentHeadersMargin + headersDelta * interpolatedTime);
//                        headersContainer.setLayoutParams(headersParams);
//
//                        ViewGroup.MarginLayoutParams rowsParams = (ViewGroup.MarginLayoutParams) rowsContainer.getLayoutParams();
//                        rowsParams.leftMargin = (int) (currentRowsMargin + rowsDelta * interpolatedTime);
//                        rowsContainer.setLayoutParams(rowsParams);
                    }
                };

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        navigationDrawerOpen = doOpen;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (!doOpen) {
                            rowsFragment.refresh();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                });

                animation.setDuration(200);
                ((View) rowsContainer.getParent()).startAnimation(animation);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isVerticalScrolling() {
        try {
            // don't run transition
            return getVerticalGridView(headersFragment).getScrollState()
                    != HorizontalGridView.SCROLL_STATE_IDLE
                    || getVerticalGridView(rowsFragment).getScrollState()
                    != HorizontalGridView.SCROLL_STATE_IDLE;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public VerticalGridView getVerticalGridView(Fragment fragment) {
        try {
            Class baseRowFragmentClass = getClassLoader().loadClass("android/support/v17/leanback/app/BaseRowFragment");
            Method getVerticalGridViewMethod = baseRowFragmentClass.getDeclaredMethod("getVerticalGridView", null);
            getVerticalGridViewMethod.setAccessible(true);
            VerticalGridView gridView = (VerticalGridView) getVerticalGridViewMethod.invoke(fragment, null);

            return gridView;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Subscribe
    public void onCategoryEvent(CategoriesEvent event) {
        final Categories categories = event.getCategories();
        final int type = event.getType();

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(MainActivity.this, "Category Event is called", Toast.LENGTH_SHORT).show();
                hideLoading();

                switch (type) {
                    case CategoriesEvent.TYPE_CATEGORY_ALL:
                        allCategories(categories);
                        break;
                    case CategoriesEvent.TYPE_CATEGORY_WITH_ID:
                        break;
                    case CategoriesEvent.TYPE_CATEGORY_WITH_EMAIL:
                        break;
                    case CategoriesEvent.TYPE_CATEGORY_WITH_MEDIAID:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    // dynamically create category fragments
    private void allCategories(Categories categories) {
        mCategories = categories;

        for (int i =0; i < fragments.size(); i++) {
            CustomRowsFragment fragment = fragments.get(i);
            fragment.setSubCategories(mCategories);
        }


//        int count = categories.getCategories().size();
//
//        // extract main categories
//        ArrayList<Category> mainCategories = new ArrayList<>();
//        for (int i=0; i<count; i++) {
//            Category category = categories.getCategories().get(i);
//            if (category.getParentCategory() == null)
//                mainCategories.add(category);
//        }
//
//        // sort main categories in ascending order
//        Collections.sort(mainCategories, new Comparator<Category>() {
//            @Override
//            public int compare(Category lhs, Category rhs) {
//                return lhs.getTitle().compareTo(rhs.getTitle());
//            }
//        });
//
//        Log.v(TAG, "Sorted Main Categories Count = " + mainCategories.size());
    }

    @Subscribe
    public void onMediaEvent(MediaEvent event) {
        final Media media = event.getMedia();
        final int type = event.getType();
        final int categoryID = event.getCategoryId();

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(MainActivity.this, "Media Event is called", Toast.LENGTH_SHORT).show();
                hideLoading();

                switch (type) {
                    case MediaEvent.TYPE_MEDIA:
                        break;
                    case MediaEvent.TYPE_MEDIA_BY_CATEGORYID:
                        setMediaList(categoryID, media);
                        break;
                    case MediaEvent.TYPE_MEDIA_ID:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setMediaList(int categoryId, Media media) {
        List<Medium> mediaList = media.getMedia();
//        if ( (mediaList == null) || (mediaList.size() == 0) )
//            return;

        for (int i =0; i < fragments.size(); i++) {
            CustomRowsFragment fragment = fragments.get(i);
            fragment.setMediaList(categoryId, media);
        }

    }

    @Subscribe
    public void onUsersEvent(UsersEvent event) {
        final Users users = event.getUsers();

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Users Event is called", Toast.LENGTH_SHORT).show();
                hideLoading();

                // not implemented yet
            }
        });
    }

    @Subscribe
    public void onServiceErrorEvent(final ServiceErrorEvent errorEvent){

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
                hideLoading();
            }
        });
    }

    public synchronized boolean isNavigationDrawerOpen() {
        return navigationDrawerOpen;
    }

    public void updateCurrentRowsFragment(CustomRowsFragment fragment) {
        rowsFragment = fragment;
    }

    public LinkedHashMap<Integer, CustomRowsFragment> getFragments() {
        return fragments;
    }
}