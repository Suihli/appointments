package com.example.diseasemonitoring.network

import AppointmentApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Change la BASE_URL pour utiliser l'adresse locale
    private const val BASE_URL = "http://92.168.1.104:3000/"  // Utilisation de l'adresse locale et du port 3000

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: AppointmentApi by lazy {
        retrofit.create(AppointmentApi::class.java)
    }
}
