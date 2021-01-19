package com.example.testapplication.ui.image_pick

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import com.example.testapplication.ui.adapters.ImageAdapter
import com.example.testapplication.di.TestAppModule
import com.example.testapplication.getOrAwaitValue
import com.example.testapplication.ui.viewModel.ShoppingViewModel
import com.example.testapplication.utils.launchFragmentInKoinContainer
import com.example.testapplication.utils.test_screens.ImagePickRecyclerScreen
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
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

// Created by Victor Hernandez on 1/18/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@KoinApiExtension
@MediumTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest:KoinTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val imgAdapter by inject<ImageAdapter>()

    val viewModel: ShoppingViewModel by inject()
    //init UI Screen Kakao
    private val screen =  ImagePickRecyclerScreen()

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
fun clickImage_popBackStackAndSetImageUrl(){
    val navController = mock(NavController::class.java)
    val urlImage = "TEST"
    launchFragmentInKoinContainer<ImagePickFragment> {
        Navigation.setViewNavController(requireView(),navController)
        imgAdapter.images = listOf(urlImage)

    }
    //event in UI
    screen{
        recycler.firstChild<ImagePickRecyclerScreen.ViewHolder> {
            ivShoppingImage.click()

        }
    }
    verify(navController).popBackStack()
    assertThat(viewModel.curlImageUrl.getOrAwaitValue()).isEqualTo(urlImage)
}


}