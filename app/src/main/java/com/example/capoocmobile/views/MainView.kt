package com.example.capoocmobile.views

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.capoocmobile.ui.components.BluetoothDevicesList
import com.example.capoocmobile.view_models.MainViewModel

@Composable
fun MainView(navController: NavController, viewModel: MainViewModel) {
    val devices by viewModel.devices.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()

    LaunchedEffect(isScanning) {
        Log.d("MainView", "Scanning state changed: $isScanning")
    }

    LaunchedEffect(devices) {
        Log.d("MainView", "Devices updated in UI. Count: ${devices.size}")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Choose the device to connect to:",
            modifier = Modifier.padding(32.dp).align(Alignment.CenterHorizontally),
            fontSize = 24.sp
        )
        Button(
            onClick = { if (isScanning) viewModel.stopScan() else viewModel.startScan() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(if (isScanning) "Stop Scan" else "Start Scan")
        }
        BluetoothDevicesList(
            devices = devices,
            onDeviceClick = { device ->
                viewModel.selectDevice(device)
                navController.navigate("details")
            }
        )
    }
}

