package com.example.onpriceapp.api

import com.example.onpriceapp.model.Product
import com.example.onpriceapp.model.Store
import retrofit2.Call
import retrofit2.http.*

/**
 * This is the interface that contains the endpoints for the REST API
 */
interface API
{
    @POST("stores")
    fun insertStore(@Body store: Store) : Call<String>

    @POST("products")
    @FormUrlEncoded
    fun insertProduct(@Body product: Product) : Call<String>

    @GET("stores")
    fun listStores() : Call<List<Store>>

    @GET("stores/products")
    fun listProducts(@Query("id") id : Int) : Call<List<Product>>

    @GET("login")
    fun login(@Query("name") name : String, @Query("password") password: String) : Call<Store>

    @GET("stores")
    fun getStore(@Query("id") id : Int) : Call<List<Store>>

    @PUT("stores")
    @FormUrlEncoded
    fun updateStore(@Query("id") id : Int, @Body store: Store) : Call<String>

    @PUT("products")
    @FormUrlEncoded
    fun updateProduct(@Query("id") id: Int, @Body product: Product) : Call<String>

    @DELETE("stores")
    fun deleteStore(@Query("id") id : Int) : Call<String>

    @DELETE("products")
    fun deleteProduct(@Query("id") id : Int) : Call<String>

}