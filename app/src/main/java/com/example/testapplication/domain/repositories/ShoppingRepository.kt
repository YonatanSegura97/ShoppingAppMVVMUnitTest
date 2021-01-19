package com.example.testapplication.domain.repositories

import androidx.lifecycle.LiveData
import com.example.testapplication.data.local.ShoppingItem
import com.example.testapplication.data.remote.responses.ImageResponse
import com.example.testapplication.utils.Resource

// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

     fun observeallShoppingItems(): LiveData<List<ShoppingItem>>

     fun observeTotalPrice(): LiveData<Float>

     suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>

}