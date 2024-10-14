package com.example.bmicalculator.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmicalculator.models.BMIViewModel
import com.example.bmicalculator.utils.HeightUnits
import com.example.bmicalculator.utils.WeightUnits

@Composable
fun SettingsScreenPager(
    bmiViewModel: BMIViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SettingsTile(
            tile = "Weight Units", listOf(
                WeightUnits.KILOGRAMS,
                WeightUnits.POUNDS
            ),
            bmiViewModel
        )
        SettingsTile(
            tile = "Height Units", listOf(
                HeightUnits.FEET_INCHES, HeightUnits.METERS, HeightUnits.CENTIMETERS
            ),
            bmiViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTile(tile: String, items: List<String>, bmiViewModel: BMIViewModel, modifier: Modifier = Modifier) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(tile, fontSize = 20.sp, modifier=Modifier.padding(horizontal = 10.dp))
        // Exposed Dropdown
        if (items.isNotEmpty()) {
            var expanded by remember { mutableStateOf(false) }
            var selectedItem = if (tile == "Weight Units") {
                bmiViewModel.currentWeightUnit
            } else {
                bmiViewModel.currentHeightUnit
            }
            ExposedDropdownMenuBox(
                expanded = expanded,
                modifier = Modifier.size(width = 200.dp, height = 50.dp).padding(end = 10.dp),
                onExpandedChange = { expanded = !expanded }
            ) {
                // TextField to trigger the dropdown
                TextField(
                    value = selectedItem,
                    onValueChange = {},
                    readOnly = true,  // To prevent direct typing
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor() // Attach the menu to the text field
                )

                // The actual dropdown menu
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { selectedOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectedOption) },
                            onClick = {
                                selectedItem = selectedOption
                                if (tile == "Weight Units") {
                                    bmiViewModel.changeWeightUnits(selectedOption)
                                } else {
                                    bmiViewModel.changeHeightUnits(selectedOption)
                                }
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}