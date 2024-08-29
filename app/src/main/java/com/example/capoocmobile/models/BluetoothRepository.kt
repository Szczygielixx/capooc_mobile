package com.example.capoocmobile.models

import android.bluetooth.BluetoothAdapter
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
import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat


class BluetoothRepository(private val context: Context) {
    private val central: BluetoothCentralManager
    private val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    private val _devices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val devices: StateFlow<List<BluetoothDevice>> = _devices

    private var isScanning = false

    private val callback = object : BluetoothCentralManagerCallback() {
        override fun onDiscovered(peripheral: BluetoothPeripheral, scanResult: ScanResult) {
            val deviceName = getDeviceName(peripheral, scanResult)
            Log.d("BluetoothRepository", "Discovered peripheral: $deviceName (${peripheral.address})")
            val device = BluetoothDevice(
                name = deviceName,
                address = peripheral.address,
                rssi = scanResult.rssi
            )
            _devices.value = (_devices.value + device).distinctBy { it.address }
        }

        override fun onScanFailed(scanFailure: ScanFailure) {
            Log.e("BluetoothRepository", "Scan failed: $scanFailure")
            isScanning = false
        }

        override fun onBluetoothAdapterStateChanged(state: Int) {
            Log.d("BluetoothRepository", "Bluetooth adapter state changed: $state")
        }
    }

    init {
        Log.d("BluetoothRepository", "Initializing BluetoothRepository")
        central = BluetoothCentralManager(context, callback, Handler(Looper.getMainLooper()))
    }

    private fun getDeviceName(peripheral: BluetoothPeripheral, scanResult: ScanResult): String {
        var deviceName = peripheral.name ?: scanResult.scanRecord?.deviceName
        if (deviceName.isNullOrBlank()) {
            deviceName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
            ) {
                try {
                    bluetoothAdapter?.getRemoteDevice(peripheral.address)?.name
                } catch (e: SecurityException) {
                    Log.e("BluetoothRepository", "SecurityException when getting device name: ${e.message}")
                    null
                }
            } else {
                null
            }
        }
        return deviceName ?: "Unknown Device (${peripheral.address.takeLast(5)})"
    }

    fun startScanning() {
        if (!isScanning) {
            Log.d("BluetoothRepository", "Starting scan")
            _devices.value = emptyList()
            central.scanForPeripherals()
            isScanning = true
        } else {
            Log.d("BluetoothRepository", "Scan already in progress")
        }
    }

    fun stopScanning() {
        if (isScanning) {
            Log.d("BluetoothRepository", "Stopping scan")
            central.stopScan()
            isScanning = false
        } else {
            Log.d("BluetoothRepository", "No scan in progress")
        }
    }
}