package com.example.moavara.Retrofit

import com.example.moavara.Retrofit.Api.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.joara.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiJoara: ApiJoara = retrofit.create(ApiJoara::class.java)
}