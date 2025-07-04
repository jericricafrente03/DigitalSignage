package com.jeric.bitteldigitalsignage.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeric.bitteldigitalsignage.datastore.DataStoreOperations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomSplashViewModel @Inject constructor(private val pref: DataStoreOperations): ViewModel() {

    private val _uiState = MutableLiveData<CustomSplashUiState>()
    val uiState: LiveData<CustomSplashUiState>
        get() = _uiState

    init {
        isLoggedIn()
    }

    private fun isLoggedIn() = viewModelScope.launch {
        pref.readStbFlow(Dispatchers.IO){ settings ->
            val newState = if (settings.FIRST_RUN == "1") {
                CustomSplashUiState.Authenticated
            } else {
                CustomSplashUiState.Splash
            }
            _uiState.postValue(newState)
        }
    }

}