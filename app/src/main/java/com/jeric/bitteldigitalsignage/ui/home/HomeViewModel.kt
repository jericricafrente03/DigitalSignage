package com.jeric.bitteldigitalsignage.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeric.bitteldigitalsignage.datastore.DataStoreOperations
import com.jeric.bitteldigitalsignage.datastore.model.STB
import com.jeric.bitteldigitalsignage.http.HTTPConnection
import com.jeric.bitteldigitalsignage.network.domain.model.MediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.SignageDataModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneMediaModel
import com.jeric.bitteldigitalsignage.network.domain.model.ZoneModel
import com.jeric.bitteldigitalsignage.network.domain.model.weather.daily.GetDailyData
import com.jeric.bitteldigitalsignage.network.domain.repository.MeshRepository
import com.jeric.bitteldigitalsignage.network.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val meshRepository: MeshRepository,
) : ViewModel() {

    private val _signageDataState = MutableSharedFlow<SignageDataModel?>(replay = 1)
    val signageDataState: SharedFlow<SignageDataModel?> = _signageDataState.asSharedFlow()

    private val _zoneState = MutableSharedFlow<List<ZoneModel>>(replay = 1)
    val zoneState: SharedFlow<List<ZoneModel>> = _zoneState.asSharedFlow()

    private val _zoneMediaFlow = MutableSharedFlow<List<ZoneMediaModel>>(replay = 1)
    val zoneMediaFlow: SharedFlow<List<ZoneMediaModel>> = _zoneMediaFlow.asSharedFlow()

    private val _weatherUiState = MutableSharedFlow<List<GetDailyData>>(replay = 1)
    val weatherUiState: SharedFlow<List<GetDailyData>> = _weatherUiState.asSharedFlow()

    private val _weatherUiStateToday = MutableSharedFlow<GetDailyData>(replay = 1)
    val weatherUiStateToday: SharedFlow<GetDailyData> = _weatherUiStateToday.asSharedFlow()

    init {
        startHttpMessageCollection()
    }



    private suspend fun getSbTime() {
        while (true) {
            val getTimeData = meshRepository.getTime()
            if (getTimeData is DataState.Error) {
                meshRepository.registerLoginResult()
                delay(2000)
            } else if (getTimeData is DataState.Success) {
                break
            }
        }
    }

    fun getDailyWeather() = viewModelScope.launch {
        try {
            meshRepository.getDailyWeather().collectLatest { weeklyWed ->
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val currentData = format.format(Date())
                val todayDate = format.parse(currentData)
                val result = weeklyWed.filter { dates ->
                    val end = dates.date.let { format.parse(it) }
                    todayDate!! < end
                }.take(3)
                val today = weeklyWed.filter { dates ->
                    val end = dates.date.let { format.parse(it) }
                    todayDate!! == end
                }
                val cacheResult = result.ifEmpty { weeklyWed.takeLast(3) }
                val cacheResultToday = today.ifEmpty { weeklyWed.takeLast(1) }.last()
                _weatherUiState.emit(cacheResult)
                _weatherUiStateToday.emit(cacheResultToday)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    private fun startHttpMessageCollection() = viewModelScope.launch {
        val commandActions = mapOf(
            "get_signage" to {
                getSignageDataModel()
                getZoneModel()
                getZoneMediaModel()
            }
        )
        try {
            HTTPConnection.dataFlow
                .map {
                    it.getString("command")
                        .replace("\"", "")
                        .trim('[', ']')
                        .split(",")
                        .first()
                }
                .collectLatest { command ->
                    getSbTime()
                    when {
                        commandActions.containsKey(command) -> {
                            commandActions[command]?.invoke()
                        }
                        else -> {}
                    }

                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSignageDataModel() = viewModelScope.launch {
        getSbTime()
        meshRepository.getSignageDataModel().collectLatest {
            _signageDataState.emit(it)
        }
    }

    fun getZoneModel() = viewModelScope.launch {
        meshRepository.getZoneModel().collectLatest {
            _zoneState.emit(it)
        }
    }

    fun getZoneMediaModel() = viewModelScope.launch {
        meshRepository.getZoneMediaModel().collectLatest {
            _zoneMediaFlow.emit(it)
        }
    }





}