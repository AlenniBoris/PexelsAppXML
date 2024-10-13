package com.example.pexelsproject.navigation

import android.os.Bundle
import com.example.pexelsproject.screens.app.MainAppFragment
import com.example.pexelsproject.screens.bookmarks.views.BookmarksScreenFragment
import com.example.pexelsproject.screens.details.DetailsScreenViewModel
import com.example.pexelsproject.screens.details.views.DetailsScreenFragment
import com.example.pexelsproject.screens.home.HomeScreenViewModel
import com.example.pexelsproject.screens.home.views.HomeScreenFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screen {
//    fun HomeScreen() = FragmentScreen{ HomeScreenFragment() }
//    fun BookmarksScreen() = FragmentScreen { BookmarksScreenFragment() }


    fun DetailsScreen(id: Int, previousDestination: String) = FragmentScreen {
        DetailsScreenFragment().apply {
            arguments = Bundle().apply {
                putInt("key_pic_id", id)
                putString("key_prev_dest", previousDestination)
            }
        }
    }
    fun MainAppScreens(previousDestination: String) = FragmentScreen{
        MainAppFragment().apply {
            arguments = Bundle().apply {
                putString("key_prev_dest", previousDestination)
            }
        }
    }

}