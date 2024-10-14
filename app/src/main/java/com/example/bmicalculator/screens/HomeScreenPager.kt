package com.example.bmicalculator.screens

import android.annotation.SuppressLint
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmicalculator.models.BMIViewModel
import com.example.bmicalculator.utils.HeightUnits
import com.example.bmicalculator.utils.WeightUnits

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreenPager(
    bmiViewModel: BMIViewModel,
    modifier: Modifier = Modifier,
) {
    val focusManager: FocusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BMI Calculator",
            fontSize = 27.sp,
            modifier = modifier.padding(bottom = 20.dp)
        )
        WeightInputPager(
            { newWeight ->
                bmiViewModel.changeWeight(newWeight)
            },
            bmiViewModel
        )
        HeightInputPager(
            { newHeight -> bmiViewModel.changeHeight(newHeight) },
            { newHeightFt -> bmiViewModel.changeHeightFt(newHeightFt) },
            { newHeightIn -> bmiViewModel.changeHeightIn(newHeightIn) },
            bmiViewModel,
        )
        CustomButtonPager(onClick = {
            bmiViewModel.calculateBMI()
            focusManager.clearFocus()
        }, text = "Calculate", modifier = modifier.padding(vertical = 20.dp))
        CustomButtonPager(onClick = {
            bmiViewModel.changeWeight("")
            bmiViewModel.changeHeight("")
            bmiViewModel.changeHeightFt("")
            bmiViewModel.changeHeightIn("")
            bmiViewModel.changeWeightErrorText("")
            bmiViewModel.changeHeightErrorText("")
            bmiViewModel.changeVerdict("")
            bmiViewModel.changeBMI(0.0)
            focusManager.clearFocus()
        }, text = "Reset", modifier = modifier.padding(bottom = 20.dp))
        BMIResultsWithVerdictPager(
            bmi = bmiViewModel.currentBMI,
            verdict = bmiViewModel.currentVerdict
        )
    }
}

@Composable
fun WeightInputPager(
    onWeightChange: (String) -> Unit,
    bmiViewModel: BMIViewModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(all = 20.dp)) {
        TextField(
            value = bmiViewModel.currentWeight,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            onValueChange = onWeightChange,
            label = {
                if (bmiViewModel.currentWeightUnit == WeightUnits.POUNDS) {
                    Text("Weight in pounds")
                } else {
                    Text("Weight in kilograms")
                }
            }
        )
        Text(text = bmiViewModel.currentWeightErrorText, color = Color.Red)
    }
}

@Composable
fun HeightInputPager(
    onHeightChange: (String) -> Unit,
    onHeightFtChange: (String) -> Unit,
    onHeightInChange: (String) -> Unit,
    bmiViewModel: BMIViewModel,
    modifier: Modifier = Modifier
) {
    Column() {
        when (bmiViewModel.currentHeightUnit) {
            HeightUnits.FEET_INCHES -> {
                TextField(
                    value = bmiViewModel.currentHeightFt,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = onHeightFtChange,
                    label = {
                        Text("Height in feet")
                    },
                )
                Text(text = bmiViewModel.currentHeightErrorText, color = Color.Red)
                TextField(
                    value = bmiViewModel.currentHeightIn,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = onHeightInChange,
                    label = {
                        Text("Height in inches")
                    },
                    modifier = modifier.padding(top = 20.dp)
                )
                Text(text = bmiViewModel.currentHeightErrorText, color = Color.Red)
            }

            HeightUnits.CENTIMETERS -> {
                TextField(
                    value = bmiViewModel.currentHeight,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = onHeightChange,
                    label = {
                        Text("Height in centimeters")
                    })
                Text(text = bmiViewModel.currentHeightErrorText, color = Color.Red)
            }

            HeightUnits.METERS -> {
                TextField(
                    value = bmiViewModel.currentHeight,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = onHeightChange,
                    label = { Text("Height in meters") })
                Text(text = bmiViewModel.currentHeightErrorText, color = Color.Red)
            }
        }
    }
}

@Composable
fun CustomButtonPager(
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
fun BMIResultsWithVerdictPager(bmi: Double, verdict: String, modifier: Modifier = Modifier) {
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
