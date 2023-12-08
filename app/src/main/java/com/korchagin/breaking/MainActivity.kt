package com.korchagin.breaking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.korchagin.breaking.presentation.Navigation
import com.korchagin.breaking.presentation.Screen
import com.korchagin.breaking.presentation.model.BottomNavItem
import com.korchagin.breaking.presentation.model.MenuItem
import com.korchagin.breaking.presentation.screens.BottomNavigationBar
import com.korchagin.breaking.presentation.screens.DrawerBody
import com.korchagin.breaking.presentation.screens.DrawerHeader
import com.korchagin.breaking.presentation.screens.common.AppBarState
import com.korchagin.breaking.presentation.view_model.LogInViewModel
import com.korchagin.breaking.presentation.view_model.MainViewModel
import com.korchagin.breaking.ui.theme.MainGreen
import com.korchagin.breaking.ui.theme.NavigationDrawerComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<LogInViewModel>()


    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Composable
        fun currentRoute(navController: NavHostController): String? {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            return navBackStackEntry?.destination?.route
        }


        setContent {
            NavigationDrawerComposeTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val state = viewModel.currentEmail.collectAsState(initial = null)
                val systemUiController = rememberSystemUiController()

                systemUiController.setSystemBarsColor(
                    color = MainGreen
                )
                var appBarState by remember {
                    mutableStateOf(AppBarState())
                }

                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        if (currentRoute(navController) != Screen.LogInScreen.route
                            && currentRoute(navController) != Screen.SignUpScreen.route
                            && currentRoute(navController) != Screen.SplashScreen.route
                            && currentRoute(navController) != Screen.PasswordRecoveryScreen.route
                        ) {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Home",
                                        route = Screen.MainScreen.route,
                                        icon = Icons.Default.Home
                                    ),
                                    BottomNavItem(
                                        name = "Rating",
                                        route = Screen.RatingScreen.route,
                                        icon = Icons.Default.Notifications
                                    ),
                                    BottomNavItem(
                                        name = "Settings",
                                        route = Screen.ElementsScreen.route + state.value,
                                        icon = Icons.Default.Settings

                                    ),
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                }
                            )
                        }

                    },
                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        if (currentRoute(navController) != Screen.LogInScreen.route
                            && currentRoute(navController) != Screen.SignUpScreen.route
                            && currentRoute(navController) != Screen.SplashScreen.route
                            && currentRoute(navController) != Screen.PasswordRecoveryScreen.route
                        ) {
                            DrawerHeader()
                            DrawerBody(
                                items = listOf(
                                    MenuItem(
                                        id = "home",
                                        title = "Home",
                                        contentDescription = "Go to Home screen",
                                        icon = Icons.Default.Home
                                    ),
                                    MenuItem(
                                        id = "Rating",
                                        title = "Rating",
                                        contentDescription = "Go to Rating screen",
                                        icon = Icons.Default.Settings
                                    ),
                                    MenuItem(
                                        id = "help",
                                        title = "Help",
                                        contentDescription = "Get help",
                                        icon = Icons.Default.Info
                                    ),
                                ),
                                onItemClick = {
                                    when (it.title) {
                                        "Home" -> {
                                            navController.navigate(route = Screen.ElementsScreen.route + state.value)
                                        }
                                        "Rating" -> {
                                            navController.navigate(Screen.RatingScreen.route)
                                        }

                                    }
                                }
                            )
                        }
                    }
                ) {
                    Navigation(navController = navController, appBarState)
                }


                AuthState()
            }
        }

    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun AuthState() {
        val scope = rememberCoroutineScope()
        scope.launch { viewModel.checkVerification() }
    }

}
