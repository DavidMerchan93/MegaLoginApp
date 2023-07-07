package com.david.megaloginapp.presentation.view.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()

        composeTestRule.setContent {
            LoginScreen(
                onLogin = {},
                onForgotPassword = {},
                onRegister = {},
            )
        }
    }

    @Test
    fun login_screen_check_all_fields_start_state(): Unit = with(composeTestRule) {
        onNodeWithText("Correo").assertExists()
    }
}
