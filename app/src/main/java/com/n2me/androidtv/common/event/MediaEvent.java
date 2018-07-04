package com.n2me.androidtv.common.event;

import com.n2me.androidtv.common.rest.model.Media;

public class MediaEvent {

    public static final int TYPE_MEDIA = 0;
    public static final int TYPE_MEDIA_ID = 1;
    public static final int TYPE_MEDIA_BY_CATEGORYID = 2;

    private Media mMedia;
    private int mType;
    private int mCategoryId = -1;

    public MediaEvent(int type, Media media) {
        mMedia = media;
        mType = type;
        mCategoryId = -1;
    }

    public MediaEvent(int type, int categoryId, Media media) {
        mMedia = media;
        mType = type;
        mCategoryId = categoryId;
    }

    public Media getMedia() {
        return mMedia;
    }

    public int getType() {
        return mType;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

}