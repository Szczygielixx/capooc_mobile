package com.example.capoocmobile.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.capoocmobile.models.BluetoothDevice
import com.example.capoocmobile.models.BluetoothRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BluetoothRepository(application)

    private val _devices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val devices: StateFlow<List<BluetoothDevice>> = _devices

    private val _selectedDevice = MutableStateFlow<BluetoothDevice?>(null)
    val selectedDevice: StateFlow<BluetoothDevice?> = _selectedDevice

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    init {
        repository.devices.onEach { deviceList ->
            Timber.tag("MainViewModel").d("Devices updated. Count: %s", deviceList.size)
            deviceList.forEach { device ->
                Log.d("MainViewModel", "Device: ${device.name} (${device.address})")
            }
            _devices.value = deviceList
        }.launchIn(viewModelScope)
    }

    fun startScan() {
        viewModelScope.launch {
            _isScanning.value = true
            repository.startScanning()
        }
    }

    fun stopScan() {
        viewModelScope.launch {
            _isScanning.value = false
            repository.stopScanning()
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