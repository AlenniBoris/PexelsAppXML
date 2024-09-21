package com.example.pexelsproject.navigation

import com.example.pexelsproject.screens.app.MainAppFragment
import com.example.pexelsproject.screens.bookmarks.views.BookmarksScreenFragment
import com.example.pexelsproject.screens.details.DetailsScreenViewModel
import com.example.pexelsproject.screens.details.views.DetailsScreenFragment
import com.example.pexelsproject.screens.home.HomeScreenViewModel
import com.example.pexelsproject.screens.home.views.HomeScreenFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screen {
    fun HomeScreen(query: String) = FragmentScreen{ HomeScreenFragment(query) }
    fun BookmarksScreen() = FragmentScreen { BookmarksScreenFragment() }


    fun DetailsScreen(id: Int) = FragmentScreen { DetailsScreenFragment(id) }
    fun MainAppScreens() = FragmentScreen{ MainAppFragment() }

}