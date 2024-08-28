package com.example.capoocmobile.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.capoocmobile.ui.components.BluetoothDevicesList
import com.example.capoocmobile.view_models.MainViewModel

@Composable
fun MainView(navController: NavController, viewModel: MainViewModel) {
    Column (modifier = Modifier.fillMaxSize()) {
        Text(text = "Select the device to connect to:", modifier = Modifier.padding(32.dp).align(Alignment.CenterHorizontally), fontSize = 24.sp)
        BluetoothDevicesList(devices = viewModel.devices)
    }}