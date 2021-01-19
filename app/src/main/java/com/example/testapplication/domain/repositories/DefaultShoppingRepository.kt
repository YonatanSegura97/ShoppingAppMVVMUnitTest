package com.example.testapplication.domain.repositories

import androidx.lifecycle.LiveData
import com.example.testapplication.data.local.ShoppingDao
import com.example.testapplication.data.local.ShoppingItem
import com.example.testapplication.data.remote.PixabayAPI
import com.example.testapplication.data.remote.responses.ImageResponse
import com.example.testapplication.utils.Resource
import java.lang.Exception

// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

class DefaultShoppingRepository (
        private val shoppigDao : ShoppingDao,
        private val pixabayAPI: PixabayAPI
        ):ShoppingRepository{

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
       shoppigDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppigDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeallShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppigDao.observeAllShoppingItem()
    }

    override fun observeTotalPrice(): LiveData<Float> {
      return shoppigDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
       return try {
           val response = pixabayAPI.searchForImage(imageQuery)
           response?.let {
               return@let Resource.success(it)
           } ?: Resource.error("An unknown error occured", null)

       }catch (e: Exception){
           Resource.error("Couldn't reach the server. Check your internet connection", null)
       }
    }

}