package com.david.megaloginapp

import io.mockk.clearAllMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseViewModelTest {

    @Before
    open fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }
}
