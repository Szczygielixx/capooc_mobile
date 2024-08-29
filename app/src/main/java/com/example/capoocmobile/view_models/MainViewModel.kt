package com.example.capoocmobile.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.capoocmobile.models.BluetoothDevice
import com.example.capoocmobile.models.BluetoothRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BluetoothRepository(application)

    val devices: StateFlow<List<BluetoothDevice>> = repository.devices

    private val _selectedDevice = MutableStateFlow<BluetoothDevice?>(null)
    val selectedDevice: StateFlow<BluetoothDevice?> = _selectedDevice.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    init {
        devices.onEach { deviceList ->
            Log.d("MainViewModel", "Devices updated. Count: ${deviceList.size}")
        }.launchIn(viewModelScope)
    }

    fun startScan() {
        viewModelScope.launch {
            repository.startScanning()
            _isScanning.value = true
        }
    }

    fun stopScan() {
        viewModelScope.launch {
            repository.stopScanning()
            _isScanning.value = false
        }
    }

    fun selectDevice(device: BluetoothDevice) {
        _selectedDevice.value = device
    }

    fun clearSelectedDevice() {
        _selectedDevice.value = null
    }

    override fun onCleared() {
        super.onCleared()
        stopScan()
    }
}