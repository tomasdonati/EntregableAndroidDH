package com.TomasDonati.mercadoesclavodh.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SellerAddress implements Serializable {

    @SerializedName("city")
    private SellerCity sellerCity;
}
