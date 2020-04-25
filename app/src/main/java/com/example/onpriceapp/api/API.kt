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
    @FormUrlEncoded
    suspend fun insertStore(@Field("name") name : String,
                            @Field("password") password : String,
                            @Field("cnpj") cnpj : String,
                            @Field("street") street : String,
                            @Field("number") number : String,
                            @Field("bairro") bairro : String,
                            @Field("city") city : String,
                            @Field("uf") uf : String,
                            @Field("time") time : String)

    @POST("products")
    @FormUrlEncoded
    suspend fun insertProduct(@Field("name") name : String,
                              @Field("category") category : String,
                              @Field("price") price : String,
                              @Field("stamp") stamp : String,
                              @Field("qt") qt : Int,
                              @Field("unity")unity : String,
                              @Field("id_store") store_id : Int)

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
    suspend fun updateStore(@Query("id") id : Int,
                            @Field("name") name : String,
                            @Field("password") password : String,
                            @Field("cnpj") cnpj : String,
                            @Field("street") street : String,
                            @Field("number") number : String,
                            @Field("bairro") bairro : String,
                            @Field("city") city : String,
                            @Field("uf") uf : String,
                            @Field("time") time : String)

    @PUT("products")
    @FormUrlEncoded
    suspend fun updateProduct(@Query("id") id: Int,
                              @Field("name") name : String,
                              @Field("category") category : String,
                              @Field("price") price : String,
                              @Field("stamp") stamp : String,
                              @Field("qt") qt : Int,
                              @Field("unity")unity : String)

    @DELETE("stores")
    suspend fun deleteStore(@Query("id") id : Int)

    @DELETE("products")
    fun deleteProduct(@Query("id") id : Int)

}