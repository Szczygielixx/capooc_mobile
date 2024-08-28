package com.example.capoocmobile.view_models

import androidx.lifecycle.ViewModel
import com.example.capoocmobile.models.BluetoothDevice
import com.example.capoocmobile.models.BluetoothRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainViewModel : ViewModel() {
    private val repository = BluetoothRepository()

    val devices: List<BluetoothDevice> = repository.getSampleDevices()
    var selectedDevice: BluetoothDevice? by mutableStateOf(null)
        private set

    fun selectDevice(device: BluetoothDevice) {
        selectedDevice = device
    }

    fun clearSelectedDevice() {
        selectedDevice = null
    }
}