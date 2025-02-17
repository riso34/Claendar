package com.example.calendar

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calendar.ui.home.HomeScreen
import com.example.calendar.ui.home.NavBottomBarView
import com.example.calendar.ui.home.ScheduleScreen
import com.example.calendar.ui.login.SignInView
import com.example.calendar.ui.login.SignUpView
import com.example.calendar.viewModel.TaskViewModel
import com.example.calendar.viewModel.UserViewModel


sealed class Route(val route: String) {
    data object Login : Route("login") {
        data object SignUp : Route("login/signUp")
        data object SignIn : Route("login/signIn")
    }
    data object Main : Route("main") {
        data object Home : Route("main/home")
        data object Schedule : Route("main/schedule")
        data object Memo : Route("main/memo")
        data object Profile : Route("main/profile")
    }
}


@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()
    val userViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory)
    val taskViewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = Route.Login.SignUp.route
    ) {
        composable(Route.Login.SignUp.route) {
            SignUpView(userViewModel,
                onLoginClick = { navController.navigate(Route.Main.route) },
                onSignInClick = { navController.navigate(Route.Login.SignIn.route) }
            )
        }
        composable(Route.Login.SignIn.route) {
            SignInView(userViewModel,
                onLoginClick = { navController.navigate(Route.Main.route) }
            )
        }

        navigation( route = Route.Main.route, startDestination = Route.Main.Home.route ) {
            composable(
                route = Route.Main.Home.route,
                arguments = listOf(
                    navArgument("user") { type = NavType.StringType }
                )
            ) { navBackStackEntry ->
                val user = navBackStackEntry.arguments?.getString("user")

                taskViewModel.onSetUser(user.toString())
                HomeScreen(taskViewModel, navController,
                    onScheduleClick = { navController.navigate(Route.Main.Schedule.route) },
                    onMemoClick = { navController.navigate(Route.Main.Memo.route) },
                    onProfileClick = { navController.navigate(Route.Main.Profile.route) }
                )
            }
            composable(Route.Main.Schedule.route) {
                ScheduleScreen(taskViewModel, navController,
                    onScheduleClick = { navController.navigate(Route.Main.Schedule.route) },
                    onMemoClick = { navController.navigate(Route.Main.Memo.route) },
                    onProfileClick = { navController.navigate(Route.Main.Profile.route) }
                )
            }
            composable(Route.Main.Memo.route) {
                HomeScreen(taskViewModel, navController,
                    onScheduleClick = { navController.navigate(Route.Main.Schedule.route) },
                    onMemoClick = { navController.navigate(Route.Main.Memo.route) },
                    onProfileClick = { navController.navigate(Route.Main.Profile.route) }
                )
            }
            composable(Route.Main.Profile.route) {
                HomeScreen(taskViewModel, navController,
                    onScheduleClick = { navController.navigate(Route.Main.Schedule.route) },
                    onMemoClick = { navController.navigate(Route.Main.Memo.route) },
                    onProfileClick = { navController.navigate(Route.Main.Profile.route) }
                )
            }
        }
    }
}