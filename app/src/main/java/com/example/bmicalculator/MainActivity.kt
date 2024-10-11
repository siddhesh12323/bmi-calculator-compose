package com.example.bmicalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bmicalculator.Routes.Routes
import com.example.bmicalculator.screens.HomeScreen
import com.example.bmicalculator.screens.SettingsScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.HOME, builder = {
                composable(Routes.HOME, content = {
                    HomeScreen(navController)
                })
                composable(Routes.SETTINGS, content = {
                    SettingsScreen(navController)
                })
            })
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



//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun GreetingPreview() {
//    HomeScreen()
//}