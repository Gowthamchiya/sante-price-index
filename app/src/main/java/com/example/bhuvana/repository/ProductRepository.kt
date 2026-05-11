package com.example.bhuvana.repository

import com.example.bhuvana.data.MarketData

class ProductRepository {

    fun getProducts(): List<MarketData> {

        return listOf(

            MarketData(
                "Tomato",
                "₹40/kg",
                "UP"
            ),

            MarketData(
                "Onion",
                "₹32/kg",
                "DOWN"
            ),

            MarketData(
                "Potato",
                "₹28/kg",
                "UP"
            )
        )
    }
}