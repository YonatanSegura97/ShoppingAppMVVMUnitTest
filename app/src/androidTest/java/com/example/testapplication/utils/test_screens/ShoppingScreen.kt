package com.example.testapplication.utils.test_screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.example.testapplication.R
import org.hamcrest.Matcher

// Created by Victor Hernandez on 1/15/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

class ShoppingScreen : Screen<ShoppingScreen>() {
    val fabButtom = KButton{ withId(R.id.fabAddShoppingItem)}

    val recycler by lazy {
        KRecyclerView({
            this.withId(R.id.rvShoppingItems)
        }, {
            itemType(::ViewHolder)
        })
    }

    class ViewHolder(parent: Matcher<View>) : KRecyclerItem<ViewHolder>(parent) {

    }
}