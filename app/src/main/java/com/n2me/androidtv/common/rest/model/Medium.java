
package com.n2me.androidtv.common.rest.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Medium {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("source_url")
    @Expose
    private String sourceUrl;
    @SerializedName("extra_sources")
    @Expose
    private String extraSources;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("embedded_code")
    @Expose
    private String embeddedCode;
    @SerializedName("overlay_code")
    @Expose
    private Object overlayCode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("pricing_plan_id")
    @Expose
    private Object pricingPlanId;
    @SerializedName("is_a_game")
    @Expose
    private Boolean isAGame;
    @SerializedName("medium_id")
    @Expose
    private Object mediumId;
    @SerializedName("picture_url")
    @Expose
    private String picture;
//    @SerializedName("language_list")
//    @Expose
//    private List<String> languageList = new ArrayList<String>();
    @SerializedName("categories")
    @Expose
    private List<Category> categories = new ArrayList<Category>();

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
     *     The sourceUrl
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * 
     * @param sourceUrl
     *     The source_url
     */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    /**
     * 
     * @return
     *     The extraSources
     */
    public String getExtraSources() {
        return extraSources;
    }

    /**
     * 
     * @param extraSources
     *     The extra_sources
     */
    public void setExtraSources(String extraSources) {
        this.extraSources = extraSources;
    }

    /**
     * 
     * @return
     *     The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 
     * @param language
     *     The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 
     * @return
     *     The rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * 
     * @param rating
     *     The rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * 
     * @return
     *     The order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * 
     * @param order
     *     The order
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * 
     * @return
     *     The embeddedCode
     */
    public String getEmbeddedCode() {
        return embeddedCode;
    }

    /**
     * 
     * @param embeddedCode
     *     The embedded_code
     */
    public void setEmbeddedCode(String embeddedCode) {
        this.embeddedCode = embeddedCode;
    }

    /**
     * 
     * @return
     *     The overlayCode
     */
    public Object getOverlayCode() {
        return overlayCode;
    }

    /**
     * 
     * @param overlayCode
     *     The overlay_code
     */
    public void setOverlayCode(Object overlayCode) {
        this.overlayCode = overlayCode;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The pricingPlanId
     */
    public Object getPricingPlanId() {
        return pricingPlanId;
    }

    /**
     * 
     * @param pricingPlanId
     *     The pricing_plan_id
     */
    public void setPricingPlanId(Object pricingPlanId) {
        this.pricingPlanId = pricingPlanId;
    }

    /**
     * 
     * @return
     *     The isAGame
     */
    public Boolean getIsAGame() {
        return isAGame;
    }

    /**
     * 
     * @param isAGame
     *     The is_a_game
     */
    public void setIsAGame(Boolean isAGame) {
        this.isAGame = isAGame;
    }

    /**
     * 
     * @return
     *     The mediumId
     */
    public Object getMediumId() {
        return mediumId;
    }

    /**
     * 
     * @param mediumId
     *     The medium_id
     */
    public void setMediumId(Object mediumId) {
        this.mediumId = mediumId;
    }

    /**
     * 
     * @return
     *     The picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 
     * @param picture
     *     The picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 
     * @return
     *     The languageList
     */
//    public List<String> getLanguageList() {
//        return languageList;
//    }

    /**
     * 
     * @param languageList
     *     The language_list
     */
//    public void setLanguageList(List<String> languageList) {
//        this.languageList = languageList;
//    }

    /**
     * 
     * @return
     *     The categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * 
     * @param categories
     *     The categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
