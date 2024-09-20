package com.example.capoocmobile.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import kotlin.random.Random
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.FloatEntry

@Composable
fun ChartView() {
    var chartEntryModel by remember { mutableStateOf(generateRandomEntryModel()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Chart(
            chart = lineChart(),
            model = chartEntryModel,
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { chartEntryModel = generateRandomEntryModel() }
        ) {
            Text("Refreshhhhhhhh")
        }
    }
}

private fun generateRandomEntryModel(): ChartEntryModel {
    return entryModelOf(List(10) { index ->
        FloatEntry(x = index.toFloat(), y = Random.nextFloat() * 100)
    })
}