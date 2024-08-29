package com.example.capoocmobile.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.capoocmobile.view_models.MainViewModel

@Composable
fun DetailsView(navController: NavController, viewModel: MainViewModel) {
    val selectedDevice by viewModel.selectedDevice.collectAsState()

    selectedDevice?.let { device ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Device name: ${device.name}")
            Text("MAC address: ${device.address}")
            device.rssi?.let { Text("RSSI: $it dBm") }
            Button(
                onClick = { /* TODO: Implement synchronization */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Sync")
            }
        }
    } ?: Text("Device not selected")

    BackHandler {
        viewModel.clearSelectedDevice()
        navController.popBackStack()
    }
}