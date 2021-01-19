package com.example.testapplication.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.data.local.ShoppingItem
import com.example.testapplication.data.remote.responses.ImageResponse
import com.example.testapplication.domain.repositories.ShoppingRepository
import com.example.testapplication.utils.Resource

// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

class FakeShoppingRepositoryAndroidImpl : ShoppingRepository{

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrices = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrices.postValue(getTotalPrices())
    }

    private fun getTotalPrices():Float{
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeallShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrices
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
      return if(shouldReturnNetworkError){
          Resource.error("Error",null)
      }else{
          Resource.success(ImageResponse(listOf(),0,0))
      }
    }
}