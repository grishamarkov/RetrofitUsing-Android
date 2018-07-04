
package com.n2me.androidtv.common.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Meta {

    @SerializedName("page")
    @Expose
    private String page;

    @SerializedName("per_page")
    @Expose
    private String perPage;

    @SerializedName("total_pages")
    @Expose
    private String totalPages;

    @SerializedName("total_count")
    @Expose
    private String totalCount;



}
