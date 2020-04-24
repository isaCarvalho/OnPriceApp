package com.example.onpriceapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://onpriceapi.herokuapp.com"

class APIController
{
    companion object
    {
        /**
         * This method returns the Retrofit instance for the requests
         */
        fun getRetrofitInstance(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}