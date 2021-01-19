package com.example.testapplication.utils.test_screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.example.testapplication.R
import org.hamcrest.Matcher

// Created by Victor Hernandez on 1/18/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

class ImagePickRecyclerScreen : Screen<ImagePickRecyclerScreen>() {

    val recycler by lazy {
        KRecyclerView({
            this.withId(R.id.rvImages)
        }, {
            itemType(::ViewHolder)
        })
    }

    class ViewHolder(parent: Matcher<View>) : KRecyclerItem<ViewHolder>(parent) {
        val ivShoppingImage = KView { withId(R.id.ivShoppingImage)  }
    }
}