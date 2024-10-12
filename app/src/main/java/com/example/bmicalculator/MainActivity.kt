package com.example.bmicalculator

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bmicalculator.Routes.Routes
import com.example.bmicalculator.screens.HomeScreen
import com.example.bmicalculator.screens.HomeScreenPager
import com.example.bmicalculator.screens.SettingsScreen
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
            MainScreen()
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
fun MainScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )

    var showExitDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = (context as? Activity)

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> HomeScreenPager()
            1 -> SettingsScreenPager()
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