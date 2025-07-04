package com.jeric.bitteldigitalsignage.ui.signup.validators.datas

import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.ui.signup.validators.utils.InputValidator
import com.jeric.bitteldigitalsignage.ui.signup.validators.utils.ValidationResult

class RoomValidator : InputValidator {

    override fun validate(input: String): ValidationResult {
        return if (input.isEmpty()) {
            ValidationResult(R.string.room_empty)
        } else {
            ValidationResult()
        }
    }
}
