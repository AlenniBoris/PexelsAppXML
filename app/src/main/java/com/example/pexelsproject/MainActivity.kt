package com.example.pexelsproject

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen
import com.example.pexelsproject.presentation.home.HomeScreenViewModel
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeScreenViewModel: HomeScreenViewModel by viewModels()

    private var navigator = AppNavigator(this, R.id.fvActivityContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            PexelsApplication.router.newRootScreen(Screen.MainAppScreens("home_screen"))
        }
    }

    override fun onResume() {
        super.onResume()
        PexelsApplication.cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        PexelsApplication.cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }
}