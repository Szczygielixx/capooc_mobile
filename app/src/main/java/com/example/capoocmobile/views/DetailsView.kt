package com.example.capoocmobile.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.capoocmobile.view_models.MainViewModel

@Composable
fun DetailsView(navController: NavController, viewModel: MainViewModel) {
    val selectedDevice = viewModel.selectedDevice
    if (selectedDevice == null) {
        Text("Devices not selected")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Device name: ${selectedDevice.name}")
        Text("MAC address: ${selectedDevice.address}")
        Button(
            onClick = { /* TODO: Implement synchronization */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Sync")
        }
    }
    BackHandler {
        viewModel.clearSelectedDevice()
        navController.popBackStack()
    }
}