package com.example.onpriceapp.model

import com.google.gson.annotations.SerializedName

/**
 * This is the products data class
 */
data class Product(
    @SerializedName("id") val id : Int,
    @SerializedName("name") var name: String,
    @SerializedName("category") var category : String,
    @SerializedName("price") var price : String,
    @SerializedName("stamp") var stamp : String,
    @SerializedName("qt") var qt : Int,
    @SerializedName("unity") var unit: String,
    @SerializedName("id_store") var id_store: Int
    )