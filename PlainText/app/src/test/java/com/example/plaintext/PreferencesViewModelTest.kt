package com.example.plaintext

import androidx.lifecycle.SavedStateHandle
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PreferencesViewModelTest {

    private lateinit var viewModel: PreferencesViewModel

    @Before
    fun setup() {
        viewModel = PreferencesViewModel(SavedStateHandle())
    }

    @Test
    fun `updateLogin updates state`() {
        viewModel.updateLogin("newLogin")
        assertEquals("newLogin", viewModel.preferencesState.login)
    }

    @Test
    fun `updatePassword updates state`() {
        viewModel.updatePassword("newPass")
        assertEquals("newPass", viewModel.preferencesState.password)
    }

    @Test
    fun `updatePreencher updates state`() {
        viewModel.updatePreencher(false)
        assertFalse(viewModel.preferencesState.preencher)
        
        viewModel.updatePreencher(true)
        assertTrue(viewModel.preferencesState.preencher)
    }

    @Test
    fun `checkCredentials returns true for correct credentials`() {
        viewModel.updateLogin("user")
        viewModel.updatePassword("123")
        
        assertTrue(viewModel.checkCredentials("user", "123"))
    }

    @Test
    fun `checkCredentials returns false for wrong credentials`() {
        viewModel.updateLogin("user")
        viewModel.updatePassword("123")
        
        assertFalse(viewModel.checkCredentials("user", "wrong"))
        assertFalse(viewModel.checkCredentials("wrong", "123"))
    }
}
