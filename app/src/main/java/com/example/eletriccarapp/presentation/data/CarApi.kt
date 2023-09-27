package com.example.eletriccarapp.presentation.data

import com.example.eletriccarapp.presentation.domain.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarApi {
    @GET("cars.json")
    fun getAllCars(): Call<List<Car>>
}