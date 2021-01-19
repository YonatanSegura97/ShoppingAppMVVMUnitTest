package com.example.testapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Created by Victor Hernandez on 1/7/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    var name:String,
    var amount:Int,
    var price:Float,
    var imageUrl:String,
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
)
