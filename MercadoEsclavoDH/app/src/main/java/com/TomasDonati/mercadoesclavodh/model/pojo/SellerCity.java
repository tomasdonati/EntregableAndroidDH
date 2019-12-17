package com.TomasDonati.mercadoesclavodh.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SellerCity implements Serializable {

    @SerializedName("id")
    private String cityId;
    @SerializedName("name")
    private String cityName;

}
