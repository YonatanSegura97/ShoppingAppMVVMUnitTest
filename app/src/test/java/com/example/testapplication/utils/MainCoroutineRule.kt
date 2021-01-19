package com.example.testapplication.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// Created by Victor Hernandez on 1/12/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@ExperimentalCoroutinesApi
class MainCoroutineRule(
        private val dispacher: CoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispacher) {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispacher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

}