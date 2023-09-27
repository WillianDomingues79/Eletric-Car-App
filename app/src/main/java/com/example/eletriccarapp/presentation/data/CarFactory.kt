package com.example.eletriccarapp.presentation.data

import com.example.eletriccarapp.presentation.domain.Car

object CarFactory {
    val list = listOf<Car>(
        Car(
            id = 1,
            preco = "R$ 300.000,00",
            bateria = "300 kWh",
            potencia = "200cv",
            recarga = "30 min",
            urlPhoto = "www.google.com.br",
            isFavorite = false,
        ),
        Car(
            id = 2,
            preco = "R$ 300.000,00",
            bateria = "300 kWh",
            potencia = "200cv",
            recarga = "30 min",
            urlPhoto = "www.google.com.br",
            isFavorite = false,
        )

    )
}