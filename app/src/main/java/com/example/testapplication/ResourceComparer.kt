package com.example.testapplication

import android.content.Context

// Created by Victor Hernandez on 1/7/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

class ResourceComparer {

    fun isEqual(context:Context, resId:Int,string:String):Boolean{
        return context.getString(resId) == string
    }
}