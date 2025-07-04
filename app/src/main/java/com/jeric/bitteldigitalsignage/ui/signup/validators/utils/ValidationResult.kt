package com.jeric.bitteldigitalsignage.ui.signup.validators.utils

data class ValidationResult(
    val errorMessage: Int? = null,
) {
    val isValid: Boolean
        get() = errorMessage == null
}
