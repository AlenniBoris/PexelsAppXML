package com.example.pexelsproject

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen
import com.example.pexelsproject.presentation.home.HomeScreenViewModel
import com.example.pexelsproject.utils.Constants
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeScreenViewModel: HomeScreenViewModel by viewModels()

    private var navigator = AppNavigator(this, R.id.fvActivityContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            PexelsApplication.router.newRootScreen(Screen.mainAppScreens("home_screen"))
        }
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val lastCloseTime = sharedPreferences.getLong("last_close_time", 0L)
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastCloseTime > Constants.ONE_HOUR_TIME) {
            homeScreenViewModel.clearCache()
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

    override fun onDestroy() {
        super.onDestroy()
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putLong("last_close_time", System.currentTimeMillis()).apply()
    }
}