package com.jeric.bitteldigitalsignage.ui.signup.validators.datas

import com.jeric.bitteldigitalsignage.ui.signup.validators.utils.AuthParams
import com.jeric.bitteldigitalsignage.ui.signup.validators.utils.InputValidator
import javax.inject.Inject

class ValidatorFactory @Inject constructor() {
    private val validators: Map<AuthParams, InputValidator> = mapOf(
        AuthParams.IP_ADDRESS to AddressValidator(),
        AuthParams.PORT to PortValidator(),
        AuthParams.ROOM to RoomValidator(),
        AuthParams.MAC to MacValidator(),
        AuthParams.AREA_ID to MacValidator(),
    )

    fun get(param: AuthParams): InputValidator {
        return validators.getOrElse(param) {
            throw IllegalArgumentException("Validator not found; make sure you have provided correct param")
        }
    }
}
