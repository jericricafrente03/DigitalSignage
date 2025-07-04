package com.jeric.bitteldigitalsignage.ui.signup.validators.datas

import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.ui.signup.validators.utils.InputValidator
import com.jeric.bitteldigitalsignage.ui.signup.validators.utils.ValidationResult

class MacValidator : InputValidator {
    override fun validate(input: String): ValidationResult {
        return if (input.isEmpty()) {
            ValidationResult(R.string.mac_empty)
        } else {
            ValidationResult()
        }
    }
}
