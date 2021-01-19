package com.example.testapplication.ui.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.testapplication.R
import com.example.testapplication.ui.adapters.ShoppingItemAdapter
import com.example.testapplication.data.local.ShoppingItem
import com.example.testapplication.di.TestAppModule
import com.example.testapplication.getOrAwaitValue
import com.example.testapplication.ui.viewModel.ShoppingViewModel
import com.example.testapplication.utils.launchFragmentInKoinContainer
import com.example.testapplication.utils.test_screens.ShoppingScreen
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito.*

// Created by Victor Hernandez on 1/15/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@MediumTest
@KoinApiExtension
@ExperimentalCoroutinesApi
class ShoppingFragmentTest : KoinTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    //init UI Screen Kakao
    private val screen =  ShoppingScreen()
    private val viewModel: ShoppingViewModel by inject()
    private val shoppingItemAdapter by inject<ShoppingItemAdapter>()


    @Before
    fun setup(){
        //setup test from inject dependency
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(TestAppModule.getModules())
        }
    }

    @After
    fun teardown(){
        stopKoin()
    }

    @Test
    fun swipeShoppingItem_deleteItemInDb(){
        val shoppingItem = ShoppingItem("TEST",1,1f,"TEST",1)
        launchFragmentInKoinContainer<ShoppingFragment>{
            viewModel.insertShoppingItemIntoDb(shoppingItem)
        }
        onView(withId(R.id.rvShoppingItems)).perform(
                RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                        0,
                        swipeLeft()
                )
        )
        screen.recycler{
            firstChild<ShoppingScreen.ViewHolder> {
                ViewActions.swipeLeft()
            }
        }
        assertThat(viewModel.shoppingItem.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddShoppingItemButtom_navigateToAddShoppingItemFragment(){
        val navController = mock(NavController::class.java)
        launchFragmentInKoinContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }
        //event in UI
        screen{ fabButtom.click() }
        verify(navController).navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
        )
    }
}