package com.korchagin.breaking

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.korchagin.breaking.presentation.Navigation
import com.korchagin.breaking.presentation.Screen
import com.korchagin.breaking.presentation.model.BottomNavItem
import com.korchagin.breaking.presentation.model.MenuItem
import com.korchagin.breaking.presentation.screens.BottomNavigationBar
import com.korchagin.breaking.presentation.screens.common.AppBarState
import com.korchagin.breaking.presentation.screens.main_screen.DrawerBody
import com.korchagin.breaking.presentation.screens.main_screen.DrawerHeader
import com.korchagin.breaking.presentation.view_model.LogInViewModel
import com.korchagin.breaking.ui.theme.MainGreen
import com.korchagin.breaking.ui.theme.NavigationDrawerComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel by viewModels<LogInViewModel>()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                val stateEmail = loginViewModel.currentEmail.collectAsState(initial = null)
                val systemUiController = rememberSystemUiController()


                systemUiController.setSystemBarsColor(
                    color = MainGreen
                )
                val appBarState by remember {
                    mutableStateOf(AppBarState())
                }

                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        if (currentRoute(navController) != Screen.LogInScreen.route
                            && currentRoute(navController) != Screen.SignUpScreen.route
                            && currentRoute(navController) != Screen.SplashScreen.route
                            && currentRoute(navController) != Screen.PasswordRecoveryScreen.route
                            && currentRoute(navController) != Screen.DetailScreen.route
                            && currentRoute(navController) != Screen.BboysDetailScreen.route
                        ) {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = applicationContext.getString(R.string.home),
                                        route = Screen.ElementsScreen.route /*+ stateEmail.value*/,
                                        icon = Icons.Default.Home
                                    ),
                                    BottomNavItem(
                                        name = applicationContext.getString(R.string.rating),
                                        route = Screen.RatingScreen.route,
                                        icon = Icons.Default.Star
                                    ),
                                    BottomNavItem(
                                        name = applicationContext.getString(R.string.prize),
                                        route = Screen.BboysScreen.route,
                                        icon = Icons.Default.Favorite

                                    )
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
                                            navController.navigate(route = Screen.ElementsScreen.route /*+ stateEmail.value*/)
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
        scope.launch { loginViewModel.checkVerification() }
    }

}
