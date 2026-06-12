package com.example.plaintext

import com.example.plaintext.data.repository.PasswordDBStore
import com.example.plaintext.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private val passwordDBStore = mock(PasswordDBStore::class.java)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(passwordDBStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onUsernameChange updates uiState`() {
        viewModel.onUsernameChange("testUser")
        assertEquals("testUser", viewModel.uiState.username)
    }

    @Test
    fun `onPasswordChange updates uiState`() {
        viewModel.onPasswordChange("testPass")
        assertEquals("testPass", viewModel.uiState.password)
    }

    @Test
    fun `login with correct credentials calls onSuccess`() = runTest(testDispatcher) {
        viewModel.onUsernameChange("admin")
        viewModel.onPasswordChange("123")

        var successCalled = false
        viewModel.login(
            expectedUser = "admin",
            expectedPass = "123",
            onSuccess = { successCalled = true },
            onError = {}
        )

        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(successCalled)
    }

    @Test
    fun `login with wrong credentials calls onError`() = runTest(testDispatcher) {
        viewModel.onUsernameChange("admin")
        viewModel.onPasswordChange("wrong")

        var errorCalled = false
        viewModel.login(
            expectedUser = "admin",
            expectedPass = "123",
            onSuccess = {},
            onError = { errorCalled = true }
        )

        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(errorCalled)
    }

    @Test
    fun `login updates isLoading state`() = runTest(testDispatcher) {
        viewModel.onUsernameChange("admin")
        viewModel.onPasswordChange("123")

        viewModel.login(
            expectedUser = "admin",
            expectedPass = "123",
            onSuccess = {},
            onError = {}
        )

        // Com o StandardTestDispatcher, a coroutine do login (viewModelScope.launch)
        // não começa a rodar até que chamemos advanceUntilIdle() ou semelhante.
        // Se quisermos ver o isLoading = true, precisamos dar um passo na execução.
        
        testDispatcher.scheduler.runCurrent()
        assertTrue(viewModel.uiState.isLoading)

        testDispatcher.scheduler.advanceUntilIdle()
        assertFalse(viewModel.uiState.isLoading)
    }
}
