package com.github.astat1cc.datebook

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.github.astat1cc.datebook.datedetails.presentation.DateDetailsScreen
import com.github.astat1cc.datebook.datedetails.presentation.DateDetailsViewModel
import com.github.astat1cc.datebook.datelist.presentation.DateListScreen
import com.github.astat1cc.datebook.navigation.NavigationTree
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ApplicationScreen() {

    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        modifier = Modifier.background(Color.Black),
        navController = navController,
        startDestination = NavigationTree.DateList.name
    ) {
        composable(
            route = NavigationTree.DateList.name,
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = dateListScreenAnimationSpec,
                    targetOffsetX = { -it })
            },
            enterTransition = {
                slideInHorizontally(
                    animationSpec = dateListScreenAnimationSpec,
                    initialOffsetX = { -it })
            },
            popExitTransition = {
                slideOutHorizontally(
                    animationSpec = dateListScreenAnimationSpec,
                    targetOffsetX = { -it })
            },
            popEnterTransition = {
                slideInHorizontally(
                    animationSpec = dateListScreenAnimationSpec,
                    initialOffsetX = { -it })
            }
        ) {
            DateListScreen(onNavigateToDateDetails = { dateId ->
                navController.navigate(buildDateDetailsRoute(dateId))
            })
        }
        composable(
            route = "${NavigationTree.DateDetails.name}/{dateId}",
            arguments = listOf(
                navArgument("dateId") {
                    type = NavType.StringType
                }
            ),
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = detailsScreenAnimationSpec,
                    targetOffsetX = { it })
            },
            enterTransition = {
                slideInHorizontally(
                    animationSpec = detailsScreenAnimationSpec,
                    initialOffsetX = { it })
            },
            popExitTransition = {
                slideOutHorizontally(
                    animationSpec = detailsScreenAnimationSpec,
                    targetOffsetX = { it })
            },
            popEnterTransition = {
                slideInHorizontally(
                    animationSpec = detailsScreenAnimationSpec,
                    initialOffsetX = { it })
            }
        ) { backStackEntry ->
            val dateId = backStackEntry.arguments?.getString("dateId")?.toInt()
            val viewModel =
                getViewModel<DateDetailsViewModel>(parameters = { parametersOf(dateId) })
            DateDetailsScreen(viewModel = viewModel, onNavigateUp = { navController.navigateUp() })
        }
    }
}

private val detailsScreenAnimationSpec = tween<IntOffset>(durationMillis = 600)
private val dateListScreenAnimationSpec = tween<IntOffset>(durationMillis = 630)

private fun buildDateDetailsRoute(dateId: Int) =
    "${NavigationTree.DateDetails.name}/$dateId"
