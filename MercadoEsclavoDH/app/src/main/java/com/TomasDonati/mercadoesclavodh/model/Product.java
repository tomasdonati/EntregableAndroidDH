package com.TomasDonati.mercadoesclavodh.model;

import com.google.gson.annotations.SerializedName;

public class Product {

    public static final String IMAGE_URL = "http://mla-s2-p.mlstatic.com/";

    @SerializedName("title")
    private String productName;
    @SerializedName("thumbnail")
    private String productThumbnail;
    @SerializedName("condition")
    private String productCondition;

    public String getProductCondition() {
        return productCondition;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductThumbnail(){
        return productThumbnail;
    }
}
