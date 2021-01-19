package com.example.testapplication.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.testapplication.R
import com.example.testapplication.ResourceComparer
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

// Created by Victor Hernandez on 1/7/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

class ResourceComparerTest{

    private lateinit var resourceComparer: ResourceComparer
    @Before
    fun setup(){
        resourceComparer = ResourceComparer()
    }

    @After
    fun teardown(){

    }

    @Test
    fun stringReosiurcesSameAsGivenString_returnTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "TestApplication")
        assertThat(result).isTrue()
    }

    @Test
    fun stringReosiurceDifferentSameAsGivenString_returnTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Hello")
        assertThat(result).isFalse()
    }
}