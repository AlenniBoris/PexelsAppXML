package com.example.pexelsproject.navigation

import android.os.Bundle
import com.example.pexelsproject.presentation.app.MainAppFragment
import com.example.pexelsproject.presentation.details.views.DetailsScreenFragment
import com.example.pexelsproject.presentation.liked.views.LikedScreenFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screen {
    fun detailsScreen(id: Int, previousDestination: String) = FragmentScreen {
        DetailsScreenFragment().apply {
            arguments = Bundle().apply {
                putInt("key_pic_id", id)
                putString("key_prev_dest", previousDestination)
            }
        }
    }
    fun mainAppScreens(previousDestination: String) = FragmentScreen{
        MainAppFragment().apply {
            arguments = Bundle().apply {
                putString("key_prev_dest", previousDestination)
            }
        }
    }
    fun likedScreen() = FragmentScreen{
        LikedScreenFragment()
    }
}