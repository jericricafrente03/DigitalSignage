package com.jeric.bitteldigitalsignage.ui.signup


data class SignupUiState(
    var etHost: String = "",
    var etPort: String = "",
    var etRoom: String = "",
    var etMac: String = "",
    var etAreaId: String = "",
    var isLoading: Boolean =false,
    var result: String? =null
)