package com.TomasDonati.mercadoesclavodh.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    public static final String IMAGE_URL = "http://mla-s2-p.mlstatic.com/";

    @SerializedName("id")
    private String productId;
    @SerializedName("title")
    private String productName;
    @SerializedName("thumbnail")
    private String productThumbnail;
    @SerializedName("condition")
    private String productCondition;
    @SerializedName("price")
    private String productPrice;

    public String getProductId() {
        return productId;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductThumbnail(){
        return productThumbnail;
    }
}
