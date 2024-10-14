package com.example.tarea10

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path

interface SerieApiService {
    @GET("api/series/")
    fun obtenerSeries(): Call<List<Serie>>

    @POST("api/series/")
    fun crearSerie(@Body serie: Serie): Call<Serie>

    @GET("api/series/{id}/")
    fun obtenerSerie(@Path("id") id: Int): Call<Serie>
}