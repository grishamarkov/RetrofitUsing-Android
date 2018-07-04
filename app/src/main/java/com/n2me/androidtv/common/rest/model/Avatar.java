
package com.n2me.androidtv.common.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Avatar {

    @SerializedName("avatar")
    @Expose
    private Avatar_ avatar;

    /**
     * 
     * @return
     *     The avatar
     */
    public Avatar_ getAvatar() {
        return avatar;
    }

    /**
     * 
     * @param avatar
     *     The avatar
     */
    public void setAvatar(Avatar_ avatar) {
        this.avatar = avatar;
    }

}
