package com.example.bhuvana.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bhuvana.viewmodel.ProductViewModel

@Composable
fun ProductListViewModelScreen(

    productViewModel: ProductViewModel = viewModel()

) {

    LazyColumn(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {

        items(productViewModel.productList) { product ->

            Text(
                text = "${product.productName} - ${product.productPrice}",
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}