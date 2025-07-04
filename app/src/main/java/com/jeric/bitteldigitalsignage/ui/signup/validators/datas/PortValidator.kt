package com.jeric.bitteldigitalsignage.ui.signup.validators.datas

import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.ui.signup.validators.utils.InputValidator
import com.jeric.bitteldigitalsignage.ui.signup.validators.utils.ValidationResult

class PortValidator : InputValidator {
    override fun validate(input: String): ValidationResult {
        return if (input.isEmpty()) {
            ValidationResult(R.string.port_empty)
        } else {
            ValidationResult()
        }
    }
}
