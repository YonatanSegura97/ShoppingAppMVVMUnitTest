package com.example.testapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

// Created by Victor Hernandez on 1/7/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItem():LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun observeTotalPrice():LiveData<Float>
}