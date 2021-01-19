package com.example.testapplication.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.testapplication.R
import com.example.testapplication.ui.adapters.ImageAdapter
import com.example.testapplication.ui.adapters.ShoppingItemAdapter
import com.example.testapplication.data.local.ShoppingItemDatabase
import com.example.testapplication.domain.repositories.ShoppingRepository
import com.example.testapplication.repositories.FakeShoppingRepositoryAndroidImpl
import com.example.testapplication.ui.viewModel.ShoppingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module

// Created by Victor Hernandez on 1/13/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@KoinApiExtension
object TestAppModule {

    fun getModules() = arrayListOf<Module>().apply {
        add(dbModule)
        add(viewmodelModule)
        add(reporitoryModuleTest)
        add(glideModuleTest)
        add(moduleAdapterTest)
    }

    private val dbModule = module{
        single { ProvideInMemoryDBTest(get()) }
        single { provideShoppingDaoTest(get()) }
    }

    private val glideModuleTest = module {
        single { provideGlideInstance(androidContext()) }
    }

    private val reporitoryModuleTest = module {
        single { provideRepository() }
    }

    private val viewmodelModule = module {
        single { ShoppingViewModel(get()) }
    }

    private val moduleAdapterTest = module {
        single { provideImageAdapter() }
        single { provideShoppingItemAdapter() }
    }

    @KoinApiExtension
    private fun provideImageAdapter() = ImageAdapter()

    private fun provideShoppingItemAdapter() = ShoppingItemAdapter()

    private fun provideGlideInstance(
            context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerInside()
                    .format(DecodeFormat.PREFER_RGB_565)
    )

    private fun ProvideInMemoryDBTest(context: Context) =
        Room.inMemoryDatabaseBuilder(context,ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    private fun provideShoppingDaoTest(
            database: ShoppingItemDatabase
    ) = database.shoppingDao()

    private fun provideRepository() = FakeShoppingRepositoryAndroidImpl() as ShoppingRepository
}