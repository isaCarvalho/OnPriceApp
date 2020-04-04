package com.example.onpriceapp.model

import java.util.*

data class Store(val id : Int, var name : String, var password : String, var cnpj : String,
                 var street : String, var number : Int, var neightborhood : String, var city : String,
                 var uf : String, var timeZone: String)