package com.example.capoocmobile.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capoocmobile.models.BluetoothDevice
import com.example.capoocmobile.models.BluetoothRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    val darkMode = MutableLiveData<Boolean>()
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun toggleDarkMode() {
        viewModelScope.launch {
            _isDarkMode.value = !_isDarkMode.value
        }
    }
    private val repository = BluetoothRepository()

    val devices: List<BluetoothDevice> = repository.getSampleDevices()
}