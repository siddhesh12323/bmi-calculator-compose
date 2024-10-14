package com.example.bmicalculator

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.bmicalculator.models.BMIViewModel
import com.example.bmicalculator.screens.HomeScreenPager
import com.example.bmicalculator.screens.SettingsScreenPager
import com.example.bmicalculator.utils.ConfirmExitDialog

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.HOME, builder = {
                composable(Routes.HOME, content = {
                    HomeScreen(navController)
                })
                composable(Routes.SETTINGS, content = {
                    SettingsScreen(navController)
                })
            })
             */
            val bmiViewModel by viewModels<BMIViewModel>()
            MainScreen(bmiViewModel)
            /*
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
*/
        }
    }
}

@Composable
fun MainScreen(bmiViewModel: BMIViewModel,modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )

    var showExitDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = (context as? Activity)

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> HomeScreenPager(bmiViewModel)
            1 -> SettingsScreenPager(bmiViewModel)
        }
    }

    if (showExitDialog) {
        ConfirmExitDialog(
            onConfirm = { activity?.finish() },
            onDismiss = { showExitDialog = false }
        )
    }

    BackHandler {
        showExitDialog = true
    }
}