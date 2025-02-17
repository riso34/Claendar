package com.example.calendar.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.calendar.Route


class BarItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

@Composable
fun NavBottomBarView(
    navController: NavController,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val barItems = listOf(
        BarItem("Home", Icons.Outlined.Home, Route.Main.Home.route),
        BarItem("Schedule", Icons.Outlined.Schedule, Route.Main.Schedule.route),
        BarItem("Memo", Icons.Outlined.Menu, Route.Main.Memo.route),
        BarItem("Profile", Icons.Outlined.Settings, Route.Main.Profile.route)
    )

    NavigationBar {
        barItems.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        // 最初の画面までバックスタックを popUp する
                        // (タブによる遷移によってバックスタックが際限なく増えることを抑止する
                        popUpTo(navController.graph.findStartDestination().id) {
                            // バックスタックから取り除いた画面の状態を保存しておく？
                            saveState = true
                        }
                        // バックスタックのトップが同じ destination の場合に遷移しない
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(text = item.label) },
                icon = { Icon(item.icon, contentDescription = null) }
            )
        }
    }
}