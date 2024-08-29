package com.example.capoocmobile.models

import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.welie.blessed.BluetoothCentralManager
import com.welie.blessed.BluetoothCentralManagerCallback
import com.welie.blessed.BluetoothPeripheral
import com.welie.blessed.ScanFailure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BluetoothRepository(context: Context) {
    private val central: BluetoothCentralManager
    private val _devices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val devices: StateFlow<List<BluetoothDevice>> = _devices
    private var isScanning = false
    private val callback = object : BluetoothCentralManagerCallback() {

        override fun onScanFailed(scanFailure: ScanFailure) {
            Log.d("BluetoothRepository", "Scan failed: $scanFailure")
        }
        override fun onBluetoothAdapterStateChanged(state: Int) {
            Log.d("BluetoothRepository", "Bluetooth adapter state changed: $state")
        }
        override fun onDiscovered(peripheral: BluetoothPeripheral, scanResult: ScanResult) {
            Log.d("BluetoothRepository", "Discovered peripheral: ${peripheral.name}")
            val device = BluetoothDevice(
                name = peripheral.name ?: "Unknown",
                address = peripheral.address,
                rssi = scanResult.rssi
            )
            _devices.value = (_devices.value + device).distinctBy { it.address }
        }
    }

    init {
        Log.d("BluetoothRepository", "Initializing BluetoothRepository")
        central = BluetoothCentralManager(context, callback, Handler(Looper.getMainLooper()))
    }

    fun startScanning() {
        if (!isScanning) {
            Log.d("BluetoothRepository", "Starting scan")
            central.scanForPeripherals()
            isScanning = true
        }
    }

    fun stopScanning() {
        if (isScanning) {
            Log.d("BluetoothRepository", "Stopping scan")
            central.stopScan()
            isScanning = false
        }
    }
}