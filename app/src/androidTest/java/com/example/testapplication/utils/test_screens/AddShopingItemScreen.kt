package com.example.testapplication.utils.test_screens

import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.example.testapplication.R

// Created by Victor Hernandez on 1/18/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

class AddShopingItemScreen : Screen<AddShopingItemScreen>() {

    val etItemName = KEditText{ withId(R.id.etShoppingItemName)}
    val etItemAmount= KEditText{ withId(R.id.etShoppingItemAmount)}
    val etItemPrice = KEditText{ withId(R.id.etShoppingItemPrice)}
    val btnAdd = KButton{withId(R.id.btnAddShoppingItem)}

}