package com.example.testapplication.di


import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.testapplication.BuildConfig
import com.example.testapplication.R
import com.example.testapplication.ui.adapters.ImageAdapter
import com.example.testapplication.ui.adapters.ShoppingItemAdapter
import com.example.testapplication.data.local.ShoppingDao
import com.example.testapplication.data.local.ShoppingItemDatabase
import com.example.testapplication.data.remote.PixabayAPI
import com.example.testapplication.domain.repositories.DefaultShoppingRepository
import com.example.testapplication.domain.repositories.ShoppingRepository
import com.example.testapplication.ui.viewModel.ShoppingViewModel
import com.example.testapplication.utils.Constants
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient

import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Created by Victor Hernandez on 1/8/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@KoinApiExtension
object AppModule {

    fun getModules() = arrayListOf<Module>().apply {
        add(dbModule)
        add(networkModule)
        add(repositoryModule)
        add(viewModelModule)
        add(glideModule)
        add(moduleAdapter)
    }


    private val dbModule = module {
        single { provideShoppingDataBase(androidContext()) }
        single { provideShoppingDao(get()) }
    }

    private val networkModule = module {
        single { providePixabayApi() }
    }

    private val glideModule = module {
        single { provideGlideInstance(androidContext()) }
    }

    private val moduleAdapter = module {
        single { provideImageAdapter() }
        single { provideShoppingAdapter() }
    }
    private val repositoryModule = module {
        single { provideRepository(get(), get()) }
    }

    private val viewModelModule = module {
        single { ShoppingViewModel(get()) }
    }

    @KoinApiExtension
    private fun provideImageAdapter() = ImageAdapter()
    private fun provideShoppingAdapter() = ShoppingItemAdapter()

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

    private fun provideShoppingDataBase(
            context: Context,
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, Constants.DATABASE_NAME).build()

    private fun provideShoppingDao(
            database: ShoppingItemDatabase
    ) = database.shoppingDao()

    private fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(clientInterceptor())
                .build()
                .create(PixabayAPI::class.java)
    }

    private fun provideRepository(
            dao: ShoppingDao,
            api: PixabayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

    private fun clientInterceptor(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }
        return builder.build()
    }

}