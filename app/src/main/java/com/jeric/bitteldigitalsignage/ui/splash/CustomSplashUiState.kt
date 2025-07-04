package com.jeric.bitteldigitalsignage.ui.splash

sealed class CustomSplashUiState {
    data object Authenticated : CustomSplashUiState()
    data object Splash: CustomSplashUiState()
}
