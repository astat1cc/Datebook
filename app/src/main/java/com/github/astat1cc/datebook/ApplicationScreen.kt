package com.github.astat1cc.datebook

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.astat1cc.datebook.core.ui.colors.lightBackground
import com.github.astat1cc.datebook.datelist.presentation.DateListScreen
import com.github.astat1cc.datebook.navigation.NavigationTree
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ApplicationScreen() {

    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        modifier = Modifier
            .background(lightBackground),
        navController = navController,
        startDestination = NavigationTree.DateList.name
    ) {
        composable(
            route = NavigationTree.DateList.name
        ) {
            DateListScreen(navController)
        }
    }
}