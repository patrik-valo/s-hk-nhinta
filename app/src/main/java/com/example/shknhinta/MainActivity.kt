
package com.example.shknhinta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ElectricityApp()
        }
    }
}

@Composable
fun ElectricityApp() {
    var consumption by remember { mutableStateOf("1000") }
    var price by remember { mutableFloatStateOf(0.25f) }
    var reducedVat by remember { mutableStateOf(true) }

    val roundedPrice = ceil(price * 100) / 100
    val vat = if (reducedVat) 0.10 else 0.24

    val consumptionValue = consumption.toDoubleOrNull() ?: 0.0
    val cost = consumptionValue * roundedPrice
    val total = cost + (cost * vat)

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Cost of electricity",
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = consumption,
                onValueChange = { consumption = it },
                label = {
                    Text("Consumption in kWh")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Price/kWh: ${
                    String.format(Locale.US, "%.2f", roundedPrice)
                } €"
            )

            Slider(
                value = price,
                onValueChange = { price = it },
                valueRange = 0.01f..1.00f,
                steps = 98
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = reducedVat,
                    onCheckedChange = {
                        reducedVat = it
                    }
                )

                Text("VAT 10%")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            ) {
                Text(
                    text = String.format(
                        Locale.US,
                        "%.0f€",
                        total
                    ),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}
