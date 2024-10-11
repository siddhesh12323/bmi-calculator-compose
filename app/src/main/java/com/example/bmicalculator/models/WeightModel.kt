package com.example.bmicalculator.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class WeightViewModel : ViewModel() {
    private val _weightUnit = mutableStateOf("kg")
    val weightUnit: State<String> = _weightUnit

    fun changeWeightUnit(unit: String) {
        _weightUnit.value = unit
    }
}
