package com.example.capoocmobile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.capoocmobile.models.BluetoothDevice

@Composable
fun BluetoothDevicesList(
    devices: List<BluetoothDevice>,
    onDeviceClick: (BluetoothDevice) -> Unit
) {
    LazyColumn {
        items(devices) { device ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onDeviceClick(device) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Name: ${device.name}")
                    Text(text = "Address: ${device.address}")
                    Text(text = "RSSI: ${device.rssi} dBm")
                }
            }
        }
    }
}