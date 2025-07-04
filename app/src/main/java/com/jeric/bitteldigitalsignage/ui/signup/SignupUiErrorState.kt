package com.jeric.bitteldigitalsignage.ui.signup

data class SignupUiErrorState(
    var hostErr: Int? = null,
    var portErr: Int? = null,
    var roomErr: Int? = null,
    var macErr: Int? = null,
)