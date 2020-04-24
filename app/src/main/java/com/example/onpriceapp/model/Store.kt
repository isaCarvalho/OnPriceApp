package com.example.onpriceapp.model

import com.google.gson.annotations.SerializedName

/**
 * This is the store data class
 */
data class Store(
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("password") var password : String,
    @SerializedName("cnpj") var cnpj : String,
    @SerializedName("street") var street : String,
    @SerializedName("number") var number : String,
    @SerializedName("bairro") var bairro : String,
    @SerializedName("city") var city : String,
    @SerializedName("uf") var uf : String,
    @SerializedName("time") var time: String
)