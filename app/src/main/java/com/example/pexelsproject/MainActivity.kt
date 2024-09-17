package com.example.pexelsproject

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pexelsproject.databinding.ActivityMainBinding
import com.example.pexelsproject.di.PexelsApplication
import com.example.pexelsproject.navigation.Screen
import com.example.pexelsproject.screens.home.HomeScreenViewModel
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var navigator = AppNavigator(this, R.id.fvActivityContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null){
            PexelsApplication.router.newRootScreen(Screen.MainAppScreens())
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