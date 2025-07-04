package com.jeric.bitteldigitalsignage.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeric.bitteldigitalsignage.datastore.DataStoreOperations
import com.jeric.bitteldigitalsignage.datastore.model.STB
import com.jeric.bitteldigitalsignage.network.domain.model.stb.StbRegistration
import com.jeric.bitteldigitalsignage.network.domain.repository.MeshRepository
import com.jeric.bitteldigitalsignage.network.domain.manager.BaseUrlInterceptor
import com.jeric.bitteldigitalsignage.network.util.DataState
import com.jeric.bitteldigitalsignage.network.util.NetworkUtil
import com.jeric.bitteldigitalsignage.ui.signup.validators.datas.ValidatorFactory
import com.jeric.bitteldigitalsignage.ui.signup.validators.utils.AuthParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val validatorFactory: ValidatorFactory,
    private val repository: MeshRepository,
    private val pref: DataStoreOperations
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState

    private val _uiErrorState = MutableStateFlow(SignupUiErrorState())
    val uiErrorState: StateFlow<SignupUiErrorState> = _uiErrorState

    fun areInputsValid(): Boolean {
        val ipAddError = validatorFactory.get(AuthParams.IP_ADDRESS).validate(_uiState.value.etHost)
        val portError = validatorFactory.get(AuthParams.PORT).validate(_uiState.value.etPort)
        val roomError = validatorFactory.get(AuthParams.ROOM).validate(_uiState.value.etRoom)
        val macError = validatorFactory.get(AuthParams.MAC).validate(_uiState.value.etMac)


        val hasError = listOf(
            ipAddError,
            portError,
            roomError,
            macError
        ).any { !it.isValid }

        _uiErrorState.value = _uiErrorState.value.copy(
            hostErr = ipAddError.errorMessage,
            portErr = portError.errorMessage,
            roomErr = roomError.errorMessage,
            macErr = macError.errorMessage,
        )
        if (!hasError) {
            viewModelScope.launch(Dispatchers.IO) {
                signup(_uiState.value.etHost, _uiState.value.etPort, _uiState.value.etRoom, _uiState.value.etMac)
            }
        }
        return !hasError
    }

    private fun signup(etHost: String, etPort: String, etRoom: String, etMac: String) {
        viewModelScope.launch {
            val licenseData = NetworkUtil.convertToHash(etMac)
            val stbRegistration = StbRegistration(
                ip = "http://$etHost",
                port = etPort,
                room = etRoom,
                apiKey = licenseData,
                mac = etMac
            )
            pref.saveStbState(
                stb = STB.apply {
                    HOST = "http://${etHost}"
                    PORT = etPort
                    ROOM = etRoom
                    MAC_ADDRESS = etMac
                    API_KEY = licenseData
                }
            )
            BaseUrlInterceptor.setBaseUrl("${STB.HOST}:${STB.PORT}/")
            handleRegistration(stb = stbRegistration)
        }
    }

    private fun handleRegistration(stb: StbRegistration) {
        try {
            repository.registerResult(stb).onEach { state ->
                when (state) {
                    is DataState.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
                    is DataState.Error -> _uiState.value = _uiState.value.copy(result = state.message, isLoading = false)
                    is DataState.Success -> {
                        if (state.data?.result == "success") {
                            pref.saveStbState(stb = STB.apply {
                                FIRST_RUN = "1"
                                ALLOW_API_RUN_TV = true
                            })
                            _uiState.value = _uiState.value.copy(result = "Successfully Registered!")
                        } else {
                            _uiState.value = _uiState.value.copy(result = state.data?.message)
                        }
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                }
            }.launchIn(viewModelScope)
        }catch (e: Exception){
            _uiState.value = _uiState.value.copy(result = e.message)
        }
    }

}
