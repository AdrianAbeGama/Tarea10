package com.example.tarea10

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var series by remember { mutableStateOf<List<Serie>>(emptyList()) }

    LaunchedEffect(Unit) {
        // Obtener productos
        RetrofitClient.productoApiService.obtenerProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    productos = response.body() ?: emptyList()
                } else {
                    Log.e("Error", "Error al obtener productos")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("Error", "Error de red: ${t.message}")
            }
        })

        // Obtener series
        RetrofitClient.serieApiService.obtenerSeries().enqueue(object : Callback<List<Serie>> {
            override fun onResponse(call: Call<List<Serie>>, response: Response<List<Serie>>) {
                if (response.isSuccessful) {
                    series = response.body() ?: emptyList()
                } else {
                    Log.e("Error", "Error al obtener series")
                }
            }

            override fun onFailure(call: Call<List<Serie>>, t: Throwable) {
                Log.e("Error", "Error de red: ${t.message}")
            }
        })
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Productos")
        productos.forEach { producto ->
            Text(text = "${producto.codigo} - ${producto.descripcion} - ${producto.precio}")
        }

        Text(text = "Series")
        series.forEach { serie ->
            Text(text = "${serie.id} - ${serie.name} - ${serie.release_date} - ${serie.rating} - ${serie.category}")
        }

        Button(onClick = {
            // Crear un nuevo producto
            val nuevoProducto = Producto(codigo = 6, descripcion = "Producto 6", precio = 35.99)
            RetrofitClient.productoApiService.crearProducto(nuevoProducto).enqueue(object : Callback<Producto> {
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    if (response.isSuccessful) {
                        Log.d("Producto", "Producto creado: ${response.body()?.descripcion}")
                    } else {
                        Log.e("Error", "Error al crear producto")
                    }
                }

                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    Log.e("Error", "Error de red: ${t.message}")
                }
            })

            // Crear una nueva serie
            val nuevaSerie = Serie(id = 4, name = "Serie 4", release_date = "2023-10-04", rating = 8, category = "drama")
            RetrofitClient.serieApiService.crearSerie(nuevaSerie).enqueue(object : Callback<Serie> {
                override fun onResponse(call: Call<Serie>, response: Response<Serie>) {
                    if (response.isSuccessful) {
                        Log.d("Serie", "Serie creada: ${response.body()?.name}")
                    } else {
                        Log.e("Error", "Error al crear serie")
                    }
                }

                override fun onFailure(call: Call<Serie>, t: Throwable) {
                    Log.e("Error", "Error de red: ${t.message}")
                }
            })
        }) {
            Text(text = "Crear Producto y Serie")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}