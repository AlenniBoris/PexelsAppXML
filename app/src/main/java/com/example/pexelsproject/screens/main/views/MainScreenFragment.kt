package com.example.pexelsproject.screens.main.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pexelsproject.R
import com.example.pexelsproject.screens.main.MainScreenState
import com.example.pexelsproject.screens.main.MainScreenViewModel
import com.example.pexelsproject.utils.FeaturedCollectionsRecyclerAdapter
import com.example.pexelsproject.utils.PhotosRecyclerAdapter
import com.example.pexelsproject.utils.SearchBarHistoryRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainScreenFragment : Fragment() {

    private val mainScreenViewModel: MainScreenViewModel by viewModels()
    private lateinit var applicationContext: Context
    //Photos
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var photosAdapter: PhotosRecyclerAdapter
    //Featured collections
    private lateinit var collectionsRecyclerView: RecyclerView
    private lateinit var featuredCollectionsAdapter: FeaturedCollectionsRecyclerAdapter
    //History
    private lateinit var searchHistoryRecyclerView: RecyclerView
    private lateinit var searchHistoryAdapter: SearchBarHistoryRecyclerAdapter
    private lateinit var searchHistoryContainer: FrameLayout
    private lateinit var searchBar: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applicationContext = requireActivity().applicationContext

        //Collections
        collectionsRecyclerView = view.findViewById(R.id.rvFeaturedCollections)
        featuredCollectionsAdapter = FeaturedCollectionsRecyclerAdapter()
        collectionsRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        collectionsRecyclerView.adapter = featuredCollectionsAdapter

        //Photos
        photosRecyclerView = view.findViewById(R.id.rvPhotosMain)
        photosAdapter = PhotosRecyclerAdapter()
        photosRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        photosRecyclerView.adapter = photosAdapter

        //History
        searchHistoryRecyclerView = view.findViewById(R.id.rvSearchHistory)
        searchHistoryAdapter = SearchBarHistoryRecyclerAdapter()
        searchHistoryRecyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        searchHistoryRecyclerView.adapter = searchHistoryAdapter
        searchHistoryContainer = view.findViewById(R.id.flSearchHistoryContainer)
        searchBar = view.findViewById(R.id.svSearchBar)

        mainScreenViewModel.screenState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state -> renderState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun renderState(state: MainScreenState) {
        featuredCollectionsAdapter.submitList(state.featuredCollections)
        photosAdapter.submitList(state.photos)
        searchHistoryAdapter.submitList(state.history.toList())
        val isActive = state.isActive

        searchBar.setOnQueryTextFocusChangeListener{ _, hasFocus ->
            mainScreenViewModel.changeIsActive(hasFocus)
            if (hasFocus){
                showSearchHistory()
            }
        }
        searchBar.setOnQueryTextListener((object : SearchView.OnQueryTextListener{
            //End of input and submitted query
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainScreenViewModel.forceSearchPhoto(query ?: "")
                Log.d("QUERY", state.queryText)
                searchBar.setQuery("", false)
                searchBar.clearFocus()
                hideSearchHistory()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                //TODO make search after 2.5 seconds

                showSearchHistory()
                return true
            }
        }))

    }

    private fun showSearchHistory() {
        searchHistoryContainer.visibility = View.VISIBLE
        photosRecyclerView.visibility = View.GONE
        collectionsRecyclerView.visibility = View.GONE
    }

    private fun hideSearchHistory() {
        searchHistoryContainer.visibility = View.GONE
        photosRecyclerView.visibility = View.VISIBLE
        collectionsRecyclerView.visibility = View.VISIBLE
    }
}