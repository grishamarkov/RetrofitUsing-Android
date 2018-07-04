package com.n2me.androidtv.common.event;

import com.n2me.androidtv.common.rest.model.Categories;

public class CategoriesEvent {

    public static final int TYPE_CATEGORY_ALL = 0;
    public static final int TYPE_CATEGORY_WITH_EMAIL = 1;
    public static final int TYPE_CATEGORY_WITH_ID = 2;
    public static final int TYPE_CATEGORY_WITH_MEDIAID = 3;

    private Categories mCategories;
    private int mType;

    public CategoriesEvent(int type, Categories categories) {
        mCategories = categories;
        mType = type;
    }

    public Categories getCategories() {
        return mCategories;
    }

    public int getType() {
        return mType;
    }
}