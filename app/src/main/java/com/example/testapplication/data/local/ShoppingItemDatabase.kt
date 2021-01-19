package com.example.testapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

// Created by Victor Hernandez on 1/7/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@Database(
    entities = [ShoppingItem::class],
    version = 1
)


abstract class ShoppingItemDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao
}