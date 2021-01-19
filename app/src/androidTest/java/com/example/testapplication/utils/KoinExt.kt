package com.example.testapplication.utils

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions
import com.example.testapplication.KoinTestActivity
import com.example.testapplication.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

// Created by Victor Hernandez on 1/15/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInKoinContainer(
    fragmentArgs: Bundle? = null,
    themeResId: Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: T.() -> Unit = {}
){
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            KoinTestActivity::class.java
        )
    ).putExtra(
        "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
        themeResId
    )

    ActivityScenario.launch<KoinTestActivity>(mainActivityIntent).onActivity { activity ->

        fragmentFactory?.let {
           activity.supportFragmentManager.fragmentFactory = it
        }

        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
                Preconditions.checkNotNull(T::class.java.classLoader),
                T::class.java.name
        )

        fragment.arguments = fragmentArgs

        activity.supportFragmentManager.beginTransaction()
                .add(android.R.id.content, fragment,"")
                .commitNow()

        (fragment as T).action()

    }
}