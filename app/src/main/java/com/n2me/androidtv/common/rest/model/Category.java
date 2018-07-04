
package com.n2me.androidtv.common.rest.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Category {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("picture_url")
    @Expose
    private String pictureUrl;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("parent_category")
    @Expose
    private ParentCategory parentCategory;
    @SerializedName("categories")
    @Expose
    private List<Object> categories = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The pictureUrl
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * 
     * @param pictureUrl
     *     The picture_url
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    /**
     * 
     * @return
     *     The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The parentCategory
     */
    public ParentCategory getParentCategory() {
        return parentCategory;
    }

    /**
     * 
     * @param parentCategory
     *     The parent_category
     */
    public void setParentCategory(ParentCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    /**
     * 
     * @return
     *     The categories
     */
    public List<Object> getCategories() {
        return categories;
    }

    /**
     * 
     * @param categories
     *     The categories
     */
    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

}
