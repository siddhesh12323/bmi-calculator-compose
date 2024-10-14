package com.example.bmicalculator.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bmicalculator.utils.HeightUnits
import com.example.bmicalculator.utils.WeightUnits
import kotlin.math.pow

class BMIViewModel: ViewModel() {
    var currentBMI by mutableDoubleStateOf(0.0)
        private set

    var currentWeight by mutableStateOf("")
        private set

    var currentHeight by mutableStateOf("")
        private set

    var currentHeightFt by mutableStateOf("")
        private set

    var currentHeightIn by mutableStateOf("")
        private set

    var currentWeightErrorText by  mutableStateOf("")
        private set

    var currentHeightErrorText by mutableStateOf("")
        private set

    var currentVerdict by  mutableStateOf("")
        private set

    fun changeWeight(newWeight: String) {
        currentWeight = newWeight
    }

    fun changeHeight(newHeight: String) {
        currentHeight = newHeight
    }

    fun changeHeightFt(newHeightFt: String) {
        currentHeightFt = newHeightFt
    }

    fun changeHeightIn(newHeightIn: String) {
        currentHeightIn = newHeightIn
    }

    fun changeHeightErrorText(newHeightErrorText: String) {
        currentHeightErrorText = newHeightErrorText
    }

    fun changeWeightErrorText(newWeightErrorText: String) {
        currentWeightErrorText = newWeightErrorText
    }

    fun changeVerdict(newVerdict: String) {
        currentVerdict = newVerdict
    }

    fun changeBMI(newBMI: Double) {
        currentBMI = newBMI
    }

    var currentWeightUnit by mutableStateOf(WeightUnits.KILOGRAMS)
        private set

    var currentHeightUnit by mutableStateOf(HeightUnits.FEET_INCHES)
        private set

    fun changeHeightUnits(newHeightUnit: String) {
        currentHeightUnit = newHeightUnit
    }

    fun changeWeightUnits(newWeightUnit: String) {
        currentWeightUnit = newWeightUnit
    }

    fun calculateBMI() {
        var weightInDouble = currentWeight.toDoubleOrNull() ?: 0.0
        var heightInDouble = currentHeight.toDoubleOrNull() ?: 0.0
        if (currentWeightUnit == WeightUnits.POUNDS) {
            weightInDouble /= 2.205
        }
        when (currentHeightUnit) {
            HeightUnits.FEET_INCHES -> {
                val totalInches = (currentHeightFt.toDoubleOrNull() ?: 0.0) * 12 + (currentHeightIn.toDoubleOrNull()
                    ?: 0.0)
                heightInDouble = totalInches / 39.37
            }
            HeightUnits.CENTIMETERS -> {
                heightInDouble /= 100
            }
        }
        try {
            if (heightInDouble <= 0.0 && weightInDouble <= 0.0) {
                currentHeightErrorText = "Invalid height!"
                currentWeightErrorText = "Invalid weight!"
            } else if (heightInDouble <= 0.0) {
                currentHeightErrorText = "Invalid height!"
            } else if (weightInDouble <= 0.0) {
                currentWeightErrorText = "Invalid weight!"
            } else {
                currentBMI = weightInDouble / (heightInDouble.pow(2))
                currentHeightErrorText = ""
                currentWeightErrorText = ""
                currentVerdict = "Your BMI shows that you are " + when (currentBMI) {
                    in 0.0..18.4 -> "Underweight"
                    in 18.5..24.9 -> "Normal"
                    in 25.0..29.9 -> "Overweight"
                    else -> "Obese"
                } + "!"
            }
        } catch (e: Exception) {
            println(e.toString())
        }
    }
}