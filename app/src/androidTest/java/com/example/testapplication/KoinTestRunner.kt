package com.example.testapplication

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

// Created by Victor Hernandez on 1/13/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

class KoinTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, KoinTestApp::class.java.name, context)
    }
}