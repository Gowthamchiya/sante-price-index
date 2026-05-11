package com.example.bhuvana.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.bhuvana.data.MarketData
import com.example.bhuvana.repository.ProductRepository

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    var productList = mutableStateListOf<MarketData>()

    init {

        loadProducts()

    }

    private fun loadProducts() {

        productList.clear()

        productList.addAll(
            repository.getProducts()
        )
    }
}