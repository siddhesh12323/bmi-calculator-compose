package com.example.bmicalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Text("Settings", fontSize = 35.sp, modifier = Modifier.padding(16.dp))
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))
                        NavigationDrawerItem(
                            label = {
                                Text(text = "Item 1")
                            },
                            selected = false,
                            onClick = { /*TODO*/ }
                        )
                        // ...other drawer items
                    }
                },
            ) {
                Scaffold(
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            text = { Text("Show drawer") },
                            icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        )
                    }
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmi by remember { mutableDoubleStateOf(0.0) }
    var weightErrorText by remember { mutableStateOf("") }
    var heightErrorText by remember { mutableStateOf("") }
    var verdict by remember { mutableStateOf("") }
    val focusManager: FocusManager = LocalFocusManager.current
        Column(modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "BMI Calculator",  fontSize = 27.sp, modifier = modifier.padding(bottom = 20.dp))
            WeightInput(weight, weightErrorText, {newWeight -> weight = newWeight})
            HeightInput(height, heightErrorText, {newHeight -> height = newHeight})
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
                        verdict = "You BMI shows that you are " + when(bmi) {
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
                focusManager.clearFocus()
            }, text = "Reset", modifier = modifier.padding(bottom = 20.dp))
            BMIResultsWithVerdict(bmi = bmi, verdict = verdict)
        }

}

@Composable
fun WeightInput(weight: String, weightErrorText: String, onWeightChange: (String) -> Unit,modifier: Modifier = Modifier) {
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
fun HeightInput(height: String, heightErrorText: String, onHeightChange: (String) -> Unit, modifier: Modifier = Modifier) {
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
fun CustomButton(modifier: Modifier = Modifier,
                 onClick: () -> Unit, text: String) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    HomeScreen()
}