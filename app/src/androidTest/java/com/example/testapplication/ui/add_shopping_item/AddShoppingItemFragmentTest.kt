package com.example.testapplication.ui.add_shopping_item

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.pressBack
import androidx.test.filters.MediumTest
import com.example.testapplication.data.local.ShoppingItem
import com.example.testapplication.di.TestAppModule
import com.example.testapplication.getOrAwaitValue
import com.example.testapplication.repositories.FakeShoppingRepositoryAndroidImpl
import com.example.testapplication.ui.viewModel.ShoppingViewModel
import com.example.testapplication.utils.launchFragmentInKoinContainer
import com.example.testapplication.utils.test_screens.AddShopingItemScreen
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
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito
import org.mockito.Mockito.verify

// Created by Victor Hernandez on 1/15/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com
@MediumTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest : KoinTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val screen = AddShopingItemScreen()

    val testViewmodel by inject<ShoppingViewModel>()

    @KoinApiExtension
    @Before
    fun setup() {
        //setup test from inject dependency
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(TestAppModule.getModules())
        }
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun clickInsertIntoDb_shoppingItemInsertIntoDb() {
        launchFragmentInKoinContainer<AddShoppingItemFragment>()

        screen {
            etItemName.replaceText("Shopping item")
            etItemAmount.replaceText("5")
            etItemPrice.replaceText("5.5")
            btnAdd.click()
        }
        val list = testViewmodel.shoppingItem.getOrAwaitValue()
        println("data $list")
        assertThat(testViewmodel.shoppingItem.getOrAwaitValue())
            .contains(
                ShoppingItem(
                    name = "Shopping item",
                    amount = 5,
                    price = 5.5f,
                    imageUrl = ""
                )
            )
    }


    @Test
    fun pressBackButtom_popBackStack() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInKoinContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        //event in UI
        pressBack()
        verify(navController).popBackStack()
    }
}