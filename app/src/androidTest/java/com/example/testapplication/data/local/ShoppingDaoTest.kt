package com.example.testapplication.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.testapplication.KoinTestApp
import com.example.testapplication.KoinTestRunner
import com.example.testapplication.di.TestAppModule
import com.example.testapplication.getOrAwaitValue
import com.example.testapplication.ui.shopping.ShoppingFragment
import com.example.testapplication.utils.launchFragmentInKoinContainer
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito.mock

// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class ShoppingDaoTest : KoinTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database by inject<ShoppingItemDatabase>()
    private val dao by inject<ShoppingDao>()

    @Before
    fun setup(){
        //setup test from inject dependency
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(TestAppModule.getModules())
        }

        //setup test from manual

       /*
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()
        */

    }

    @After
    fun teardown(){
        database.close()
        stopKoin()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name",1,1f,"url",id=1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItem().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {

        val shoppingItem = ShoppingItem("name",1,1f,"url",id=1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(shoppingItem)

    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {

        val list = arrayListOf(
         ShoppingItem("name",1,1f,"url",id=1),
        ShoppingItem("name",4,5.5f,"url",id=2),
         ShoppingItem("name",3,3f,"url",id=3)
        )
        list.forEach {
            dao.insertShoppingItem(it)
        }

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(1 * 1f + 4 * 5.5f + 3 * 3f)

    }
    @Test
    fun testLaunchFragmentInkoinContainer(){
        launchFragmentInKoinContainer<ShoppingFragment> {

        }
    }

}