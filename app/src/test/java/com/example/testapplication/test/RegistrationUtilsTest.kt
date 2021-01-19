package com.example.testapplication.test

import com.example.testapplication.RegistrationUtils
import com.google.common.truth.Truth.assertThat
import org.junit.Test

// Created by Victor Hernandez on 1/7/21.
// Proyect TestApplication
//contact victoralfonso920@gmail.com

class RegistrationUtilsTest{


    @Test
    fun `empty user name returns false`(){
        val result = RegistrationUtils.validateRegistrationInput(
                "",
                "123",
                "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeat password return true`(){
        val result = RegistrationUtils.validateRegistrationInput(
                "Philipp",
                "123",
                "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exisit returns false`(){
        val result = RegistrationUtils.validateRegistrationInput(
                "Carl",
                "123",
                "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `incorrectly confirmed password return false`(){
        val result = RegistrationUtils.validateRegistrationInput(
                "Philipp",
                "123",
                "abcdefg"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password return false`(){
        val result = RegistrationUtils.validateRegistrationInput(
                "Philipp",
                "",
                ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `less than 2 digit password return false`(){
        val result = RegistrationUtils.validateRegistrationInput(
                "Philipp",
                "abcdefg5",
                "abcdefg5"
        )
        assertThat(result).isFalse()
    }


}