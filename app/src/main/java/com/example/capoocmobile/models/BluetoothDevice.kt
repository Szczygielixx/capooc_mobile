package com.example.capoocmobile.models

import com.welie.blessed.BluetoothPeripheral

data class BluetoothDevice(
    val name: String,
    val address: String,
    val rssi: Int,
)