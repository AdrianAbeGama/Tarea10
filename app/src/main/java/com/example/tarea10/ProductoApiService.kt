package com.example.tarea10

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path

interface ProductoApiService {
    @GET("api/productos/")
    fun obtenerProductos(): Call<List<Producto>>

    @POST("api/productos/")
    fun crearProducto(@Body producto: Producto): Call<Producto>

    @GET("api/productos/{codigo}/")
    fun obtenerProducto(@Path("codigo") codigo: Int): Call<Producto>
}