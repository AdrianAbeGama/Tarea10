package com.example.tarea10

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8000/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productoApiService: ProductoApiService = retrofit.create(ProductoApiService::class.java)
    val serieApiService: SerieApiService = retrofit.create(SerieApiService::class.java)
}