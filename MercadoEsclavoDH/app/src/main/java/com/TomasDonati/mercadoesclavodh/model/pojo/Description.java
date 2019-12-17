package com.TomasDonati.mercadoesclavodh.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Description implements Serializable {

    public String getDescriptionText() {
        return descriptionText;
    }

    @SerializedName("plain_text")
    private String descriptionText;
}
