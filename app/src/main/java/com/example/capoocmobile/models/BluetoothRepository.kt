package com.example.capoocmobile.models

class BluetoothRepository {
    fun getSampleDevices(): List<BluetoothDevice> {
        return listOf(
            BluetoothDevice("Słuchawki Sony", "00:11:22:33:44:55"),
            BluetoothDevice("Głośnik JBL", "AA:BB:CC:DD:EE:FF"),
            BluetoothDevice("Mysz Logitech", "11:22:33:44:55:66"),
            BluetoothDevice("Klawiatura Bluetooth", "AA:BB:CC:11:22:33")
        )
    }
}