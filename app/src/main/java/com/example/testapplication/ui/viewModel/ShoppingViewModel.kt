package com.example.testapplication.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.local.ShoppingItem
import com.example.testapplication.data.remote.responses.ImageResponse
import com.example.testapplication.domain.repositories.ShoppingRepository
import com.example.testapplication.utils.Constants
import com.example.testapplication.utils.Event
import com.example.testapplication.utils.Resource
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.Exception

// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

class ShoppingViewModel(
    private val repository: ShoppingRepository
) : ViewModel() {

    var data = "inicial"
    val shoppingItem = repository.observeallShoppingItems()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curlImageUrl = MutableLiveData<String>()
    val curlImageUrl: LiveData<String> = _curlImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurlImageUrl(url:String){
        _curlImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name:String,amountString: String,priceString: String){
        if(name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The fields must not by empty",null)))
            return
        }

        if(name.length > Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The name of the item" +
                    "must not excedd ${Constants.MAX_NAME_LENGTH} characters",null)))
            return
        }

        if(priceString.length > Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("The price of the item" +
                    "must not excedd ${Constants.MAX_PRICE_LENGTH} characters",null)))
            return
        }

        val amount = try {
            amountString.toInt()
        }catch (e:Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter a valid amount",null)))
            return
        }

        val shoppingItem = ShoppingItem(name,amount,priceString.toFloat(),_curlImageUrl.value ?: "")
        insertShoppingItemIntoDb(shoppingItem)
        setCurlImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(imageQuery: String){
        if(imageQuery.isEmpty()){
            _images.value = Event(Resource.error("The fields must not by empty",null))
            return
        }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }

}