package com.example.bmicalculator.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bmicalculator.Routes.Routes
import kotlin.math.pow

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmi by remember { mutableDoubleStateOf(0.0) }
    var weightErrorText by remember { mutableStateOf("") }
    var heightErrorText by remember { mutableStateOf("") }
    var verdict by remember { mutableStateOf("") }
    var horizontalDragCount by remember { mutableFloatStateOf(0.0f) }
    val focusManager: FocusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    horizontalDragCount += dragAmount.x
                    if (horizontalDragCount < -30.0f) {
                        horizontalDragCount = 0.0f
                        navController.navigate(Routes.SETTINGS)
                        {
                            launchSingleTop = true
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                    }
                }
            }, verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BMI Calculator",
            fontSize = 27.sp,
            modifier = modifier.padding(bottom = 20.dp)
        )
        WeightInput(weight, weightErrorText, { newWeight -> weight = newWeight })
        HeightInput(height, heightErrorText, { newHeight -> height = newHeight })
        CustomButton(onClick = {
            val weightInDouble = weight.toDoubleOrNull() ?: 0.0
            val heightInDouble = height.toDoubleOrNull() ?: 0.0
            try {
                if (heightInDouble <= 0.0 && weightInDouble <= 0.0) {
                    heightErrorText = "Invalid height!"
                    weightErrorText = "Invalid weight!"
                } else if (heightInDouble <= 0.0) {
                    heightErrorText = "Invalid height!"
                } else if (weightInDouble <= 0.0) {
                    weightErrorText = "Invalid weight!"
                } else {
                    bmi = weightInDouble / (heightInDouble.pow(2))
                    heightErrorText = ""
                    weightErrorText = ""
                    verdict = "You BMI shows that you are " + when (bmi) {
                        in 0.0..18.4 -> "Underweight"
                        in 18.5..24.9 -> "Normal"
                        in 25.0..29.9 -> "Overweight"
                        else -> "Obese"
                    } + "!"
                    focusManager.clearFocus()
                }
            } catch (e: Exception) {
                println(e.toString())
            }
        }, text = "Calculate", modifier = modifier.padding(vertical = 20.dp))
        CustomButton(onClick = {
            weight = ""
            height = ""
            weightErrorText = ""
            heightErrorText = ""
            verdict = ""
            bmi = 0.0
            focusManager.clearFocus()
        }, text = "Reset", modifier = modifier.padding(bottom = 20.dp))
        BMIResultsWithVerdict(bmi = bmi, verdict = verdict)
    }
}

@Composable
fun WeightInput(
    weight: String,
    weightErrorText: String,
    onWeightChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(all = 20.dp)) {
        TextField(
            value = weight,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = onWeightChange,
            label = { Text("Weight in kilograms") }
        )
        Text(text = weightErrorText, color = Color.Red)
    }
}

@Composable
fun HeightInput(
    height: String,
    heightErrorText: String,
    onHeightChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column() {
        TextField(
            value = height,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = onHeightChange,
            label = { Text("Height in meters") }
        )
        Text(text = heightErrorText, color = Color.Red)
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit, text: String
) {
    Button(
        modifier = modifier,
        onClick = { onClick() }) {
        Text(text = text)
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun BMIResultsWithVerdict(bmi: Double, verdict: String, modifier: Modifier = Modifier) {
    if (bmi == 0.0) {
        Text(text = "Your result will appear here", fontSize = 17.sp)
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "BMI: ${String.format("%.2f", bmi).toDouble()}", fontSize = 17.sp)
            Box(modifier = modifier.size(10.dp))
            Text(text = verdict, fontSize = 17.sp)
        }
    }
}