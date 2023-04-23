package com.github.astat1cc.datebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import com.github.astat1cc.datebook.core.ui.colors.dateListScreenBackground
import com.github.astat1cc.datebook.core.ui.theme.DatebookTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DatebookTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = dateListScreenBackground
                    )
                    systemUiController.setNavigationBarColor(
                        color = dateListScreenBackground
                    )
                }
                ApplicationScreen()
            }
        }
    }
}